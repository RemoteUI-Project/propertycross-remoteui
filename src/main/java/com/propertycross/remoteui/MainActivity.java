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
import org.remoteui.webapp.plugin.geolocation.GeoLocation;
import org.remoteui.webapp.plugin.geolocation.GeolocationPlugin;
import org.springframework.http.ResponseEntity;

import com.propertycross.remoteui.mongo.RecentSearch;
import com.propertycross.remoteui.mongo.RecentSearchRepository;
import com.propertycross.remoteui.nestoria.NestoriaResponse;
import com.propertycross.remoteui.nestoria.Request;
import com.propertycross.remoteui.nestoria.Response;

/**
 * 
 * 
 * @author Daniel Thommes
 */
@Activity(main = true)
public class MainActivity extends WebActivity {

	@Inject
	private SearchResultsActivity searchResultsActivity;

	@Inject
	private NestoriaApi nestoriaApi;

	@Inject
	private FavoritesDao favoritesDao;

	@Inject
	private RecentSearchRepository recentSearchRepo;

	public GeoLocation location;
	public String searchTerm = "";
	public String errorText = null;
	public boolean searching = false;
	public boolean error = false;
	private String userId;

	private volatile boolean fireRecentSearches;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.remoteui.webapp.WebActivity#onRestart()
	 */
	@Override
	protected void onRestart() {
		if (fireRecentSearches) {
			firePropertyChanged("recentSearches");
			fireRecentSearches = false;
		}
	}

	@Override
	protected void onStart() {
		if (location == null || location.getTimestamp() + 1200000 < System.currentTimeMillis()) {
			getPlugin(GeolocationPlugin.class).getGeolocation(this::onGeolocation);
		}
		userId = UserId.getCurrentUserId();
	}

	public void onSearchByTermClicked() {
		search(searchTerm);
	}

	public void onSearchByLocationClicked() {
		setSearchingState();
		nestoriaApi.searchProperties(location.getLatitude(), location.getLongitude(), 1,
				this::onSearchResult, this::onSearchError);
	}

	public void onOpenFavouritesClicked() {
		searchResultsActivity.start(favoritesDao.getAllFavorites());
	}

	public void search(String term) {
		setSearchingState();
		nestoriaApi.searchProperties(term, 1, this::onSearchResult, this::onSearchError);
	}

	public void onGeolocation(GeoLocation location) {
		this.location = location;
		firePropertyChanged("location");
	}

	public List<RecentSearch> getRecentSearches() {
		return recentSearchRepo.findAllByUserIdOrderByTimestampDesc(userId);
	}

	public void onSearchResult(ResponseEntity<NestoriaResponse> responseEntity) {
		try {
			searching = false;
			firePropertyChanged("searching");
			Request request = responseEntity.getBody().request;
			Response response = responseEntity.getBody().response;
			switch (response.applicationResponseCode) {
			case "100":
			case "101":
			case "110":
				Integer total = response.totalResults;
				recentSearchRepo.save(new RecentSearch(request.location, userId, total));
				fireRecentSearches = true;
				searchResultsActivity.start(request.location, responseEntity.getBody());
				break;
			case "200":
			case "202":
			default:
				setErrorState(response.applicationResponseText);
				break;
			}
		} catch (Exception e) {
			setErrorState(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

	public void onSearchError(Throwable e) {
		searching = false;
		firePropertyChanged("searching");
		setErrorState(e.getMessage());
	}

	private void setSearchingState() {
		searching = true;
		firePropertyChanged("searching");
		errorText = null;
		error = false;
		firePropertyChanged("error");
	}

	private void setErrorState(String message) {
		error = true;
		errorText = message;
		firePropertyChanged("error");
		firePropertyChanged("errorText");
	}
}
