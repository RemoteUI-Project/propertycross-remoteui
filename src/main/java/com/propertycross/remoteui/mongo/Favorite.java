/** 
* Created on 06.02.2017 
* 
* © 2017 Daniel Thommes
*/
package com.propertycross.remoteui.mongo;

import org.springframework.data.annotation.Id;

import com.propertycross.remoteui.nestoria.Listing;

/**
 * 
 *
 * @author Daniel Thommes
 */
public class Favorite {

	@Id
	public String id;
	public String userId;
	public Listing listing;

	public Favorite(String userId, Listing listing) {
		super();
		this.id = userId + listing.listerUrl;
		this.userId = userId;
		this.listing = listing;
	}

}
