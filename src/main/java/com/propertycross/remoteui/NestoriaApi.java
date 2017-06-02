/*******************************************************************************
 * Copyright 2017 Daniel Thommes
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.propertycross.remoteui;

import java.util.Arrays;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.SimpleKey;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.client.AsyncRestTemplate;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.propertycross.remoteui.nestoria.NestoriaResponse;

/**
 *
 * @author Daniel Thommes
 */
@Service
public class NestoriaApi {

	/**
	 * SLF4J Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(NestoriaApi.class);

	private static final String BASE_URI = "https://api.nestoria.%TLD%/api?country=de&action=search_listings&encoding=json&listing_type=buy";

	private AsyncRestTemplate template = new AsyncRestTemplate();

	private HttpEntity<String> entity;

	Cache<SimpleKey, ResponseEntity<NestoriaResponse>> cache = Caffeine
			.from("maximumSize=10000,expireAfterWrite=600s").build();

	public NestoriaApi() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json")));
		// Nestoria returns this media type - ouch.
		converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "javascript")));
		template.getMessageConverters().add(converter);
		SimpleClientHttpRequestFactory rf = (SimpleClientHttpRequestFactory) template
				.getAsyncRequestFactory();
		// 5000 were to fast for nestoria
		rf.setReadTimeout(10000);
		rf.setConnectTimeout(5000);
		HttpHeaders headers = new HttpHeaders();
		headers.set("User-Agent", "MyAgent");
		entity = new HttpEntity<String>("parameters", headers);
	}

	public void searchProperties(double latitude, double longitude, Integer page,
			SuccessCallback<? super ResponseEntity<NestoriaResponse>> successCallback,
			FailureCallback failureCallback) {
		search("&page={page}&centre_point={latitude},{longitude}", successCallback, failureCallback,
				page, latitude, longitude);
	}

	public void searchProperties(String searchTerm, Integer page,
			SuccessCallback<? super ResponseEntity<NestoriaResponse>> successCallback,
			FailureCallback failureCallback) {
		search("&page={page}&place_name={searchTerm}", successCallback, failureCallback, page,
				searchTerm);
	}

	private void search(String pattern,
			SuccessCallback<? super ResponseEntity<NestoriaResponse>> successCallback,
			FailureCallback failureCallback, Object... params) {
		String tld = NestoriaTldHelper.getTld();
		String uri = BASE_URI.replace("%TLD%", tld) + pattern;
		final SimpleKey key = new SimpleKey(ArrayUtils.add(params, tld));
		ResponseEntity<NestoriaResponse> response = null;
		if (cache != null) {
			// retrieve the cached instance, if cache is configured
			response = cache.getIfPresent(key);
		}
		if (response != null) {
			logger.debug("Found cached result for {}", key);
			successCallback.onSuccess(response);
			return;
		}
		logger.debug("Fetching result for {}", Arrays.toString(params));
		ListenableFuture<ResponseEntity<NestoriaResponse>> exchange = template.exchange(uri,
				HttpMethod.GET, entity, NestoriaResponse.class, params);
		exchange.addCallback(successCallback, failureCallback);
		exchange.addCallback(result -> logger.debug("Result {}", result),
				e -> logger.error("Error: ", e));
		exchange.addCallback(result -> cache.put(key, result), null);
	}
}
