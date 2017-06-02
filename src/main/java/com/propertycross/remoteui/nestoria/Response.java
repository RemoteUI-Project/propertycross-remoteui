
package com.propertycross.remoteui.nestoria;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "application_response_code", "application_response_text", "attribution",
		"created_http", "created_unix", "link_to_url", "listings", "locations", "page", "sort",
		"status_code", "status_text", "thanks", "total_pages", "total_results", "listing_type" })
public class Response {

	@JsonProperty("application_response_code")
	public String applicationResponseCode;
	@JsonProperty("application_response_text")
	public String applicationResponseText;
	@JsonProperty("attribution")
	public Attribution attribution;
	@JsonProperty("created_http")
	public String createdHttp;
	@JsonProperty("created_unix")
	public Integer createdUnix;
	@JsonProperty("link_to_url")
	public String linkToUrl;
	@JsonProperty("listings")
	public List<Listing> listings = null;
	@JsonProperty("locations")
	public List<Location> locations = null;
	@JsonProperty("page")
	public Integer page;
	@JsonProperty("sort")
	public String sort;
	@JsonProperty("status_code")
	public String statusCode;
	@JsonProperty("status_text")
	public String statusText;
	@JsonProperty("thanks")
	public String thanks;
	@JsonProperty("total_pages")
	public Integer totalPages;
	@JsonProperty("total_results")
	public Integer totalResults;
	@JsonProperty("listing_type")
	public String listingType;

}
