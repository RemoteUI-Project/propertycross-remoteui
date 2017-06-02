/** 
* Created on 06.02.2017 
* 
* © 2017 Daniel Thommes
*/
package com.propertycross.remoteui;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 *
 * @author Daniel Thommes
 */
public class UserId {

	/**
	 * Gets the current User id from the request
	 * 
	 * @return
	 */
	public static String getCurrentUserId() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (sra == null) {
			throw new IllegalStateException("You're outside a request.");
		}
		HttpServletRequest request = sra.getRequest();
		return request.getHeader("deviceId");
	}
}
