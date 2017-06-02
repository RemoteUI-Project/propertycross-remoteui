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

import java.util.Locale;

import org.remoteui.handshake.client.ClientHandshake;
import org.remoteui.webapp.session.RuiSessionContextHolder;

/**
 * 
 *
 * @author Daniel Thommes
 */
public class NestoriaTldHelper {

	public static String getTld() {
		ClientHandshake currentClientHandshake = RuiSessionContextHolder
				.getCurrentClientHandshake();
		Locale locale;
		if (currentClientHandshake != null) {
			locale = currentClientHandshake.getConfiguration().locale;
		} else {
			// Only for unit tests
			locale = Locale.GERMANY;
		}
		String tld;
		// http://www.nestoria.co.uk/help/api-keywords
		String variant = locale.getVariant();
		switch (locale.getLanguage()) {
		case "de":
		case "fr":
		case "it":
			tld = locale.getLanguage();
			break;
		case "pt":
			// Brazil
			tld = "br";
			break;
		case "es":
			if ("MX".equals(variant)) {
				tld = "mx";
			} else {
				tld = "es";
			}
			break;
		case "en":
		default:
			switch (variant) {
			case "IN":
				tld = "in";
				break;
			case "AU":
				tld = "com.au";
				break;
			case "UK":
			default:
				tld = "co.uk";
				break;
			}
		}
		return tld;
	}
}
