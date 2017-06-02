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

import java.util.List;

import javax.inject.Inject;

import org.remoteui.annotation.Activity;
import org.remoteui.webapp.WebActivity;
import org.springframework.http.ResponseEntity;

import com.propertycross.remoteui.nestoria.Listing;
import com.propertycross.remoteui.nestoria.NestoriaResponse;

/**
 * 
 *
 * @author Daniel Thommes
 */
@Activity
public class SearchResultsActivity extends WebActivity {

	@Inject
	private DetailActivity detailActivity;

	@Inject
	private NestoriaApi nestoriaApi;

	private String query;

	private int currentPage;

	public String title;
	public boolean warnVisible;
	public boolean loadMoreVisible;
	public String resultInfoText;
	public String loadingButtonText;
	public List<Listing> items;

	/**
	 * Start with response object
	 * 
	 * @param nestoriaResponse
	 */
	public void start(String query, NestoriaResponse nestoriaResponse) {
		this.query = query;
		currentPage = 1;
		showResult(nestoriaResponse, false);
		start();
	}

	/**
	 * Start with list of favourites
	 * 
	 * @param allFavorites
	 */
	public void start(List<Listing> allFavorites) {
		title = "Favourites";
		loadMoreVisible = false;
		items = allFavorites;
		warnVisible = allFavorites == null || allFavorites.isEmpty();
		start();
	}

	public void onItemClicked(Listing property) {
		detailActivity.start(property);
	}

	public void onLoadMoreClicked() {
		nestoriaApi.searchProperties(query, currentPage + 1, this::onSearchResult,
				this::onSearchError);
		loadingButtonText = "Loading...";
		firePropertyChanged("loadingButtonText");
	}

	public void onSearchResult(ResponseEntity<NestoriaResponse> response) {
		showResult(response.getBody(), true);
		firePropertyChanged("items", "title", "loadMoreVisible", "loadingButtonText",
				"resultInfoText", "loadingButtonText");
	}

	public void onSearchError(Throwable e) {
		showToast(e.getMessage(), false);
		loadingButtonText = "Load More";
		firePropertyChanged("loadingButtonText");
	}

	private void showResult(NestoriaResponse nestoriaResponse, boolean addToExistingList) {
		currentPage = nestoriaResponse.response.page;
		warnVisible = false;
		loadMoreVisible = true;
		loadingButtonText = "Load more";
		List<Listing> newList = nestoriaResponse.response.listings;
		if (addToExistingList) {
			items.addAll(newList);
		} else {
			items = newList;
		}
		Integer total = nestoriaResponse.response.totalResults;
		title = items.size() + " of " + total + " matches ";
		resultInfoText = "Results for " + nestoriaResponse.request.location + ", showing "
				+ items.size() + " of " + total + " properties";
	}

}
