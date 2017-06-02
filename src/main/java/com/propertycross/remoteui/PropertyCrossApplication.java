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

import java.io.IOException;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.github.benmanes.caffeine.cache.Caffeine;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.Storage;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;

@SpringBootApplication
@EnableCaching
public class PropertyCrossApplication {

	@Inject
	private MongoProperties properties;

	public static void main(String[] args) {
		SpringApplication.run(PropertyCrossApplication.class, args);
	}

	@Bean
	public IMongodConfig mongodConfig() throws IOException {
		String databaseDir = System.getProperty("user.home") + "/.propertycross/db";
		return new MongodConfigBuilder().version(Version.Main.PRODUCTION)
				.replication(new Storage(databaseDir, null, 0))
				.net(new Net(properties.getPort(), Network.localhostIsIPv6())).build();
	}

	@Bean
	public Caffeine<Object, Object> caffeine() {
		return Caffeine.from("maximumSize=500,expireAfterWrite=10s");
	}
}
