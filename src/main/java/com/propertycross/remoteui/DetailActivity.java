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

import javax.inject.Inject;

import org.remoteui.annotation.Activity;
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
import org.remoteui.webapp.WebActivity;

import com.propertycross.remoteui.nestoria.Listing;

/**
 * 
 *
 * @author Daniel Thommes
 */
@Activity
public class DetailActivity extends WebActivity {

	@Inject
	private FavoritesDao favoritesDao;

	public Listing prop;

	public void start(Listing property) {
		this.prop = property;
		start();
	}

	public boolean isFavorite() {
		return favoritesDao.isFavorite(prop);
	}

	public void setFavorite(boolean value) {
		if (value) {
			favoritesDao.add(prop);
		} else {
			favoritesDao.remove(prop);
		}
	}

}
