/** 
* Created on 06.02.2017 
* 
* © 2017 Daniel Thommes
*/
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
