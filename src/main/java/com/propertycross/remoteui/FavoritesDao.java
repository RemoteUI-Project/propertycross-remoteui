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
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.propertycross.remoteui.mongo.Favorite;
import com.propertycross.remoteui.mongo.FavoriteRepository;
import com.propertycross.remoteui.nestoria.Listing;

/**
 * 
 *
 * @author Daniel Thommes
 */
@Component
public class FavoritesDao {

	@Inject
	private FavoriteRepository listingRepo;

	public boolean isFavorite(Listing listing) {
		Favorite fav = new Favorite(UserId.getCurrentUserId(), listing);
		return listingRepo.exists(Example.of(fav));
	}

	public List<Listing> getAllFavorites() {
		return listingRepo.findAllByUserId(UserId.getCurrentUserId()).stream()
				.map(fav -> fav.listing).collect(Collectors.toList());
	}

	public void add(Listing listing) {
		listingRepo.save(new Favorite(UserId.getCurrentUserId(), listing));
	}

	public void remove(Listing listing) {
		listingRepo.delete(new Favorite(UserId.getCurrentUserId(), listing));
	}
}
