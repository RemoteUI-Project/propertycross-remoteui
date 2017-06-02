
package com.propertycross.remoteui.nestoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "country", "language", "location", "num_res", "offset", "output", "page",
		"product_type", "property_type", "size_type", "size_unit", "sort", "listing_type" })
public class Request {

	@JsonProperty("country")
	public String country;
	@JsonProperty("language")
	public String language;
	@JsonProperty("location")
	public String location;
	@JsonProperty("num_res")
	public String numRes;
	@JsonProperty("offset")
	public Integer offset;
	@JsonProperty("output")
	public String output;
	@JsonProperty("page")
	public Integer page;
	@JsonProperty("product_type")
	public String productType;
	@JsonProperty("property_type")
	public String propertyType;
	@JsonProperty("size_type")
	public String sizeType;
	@JsonProperty("size_unit")
	public String sizeUnit;
	@JsonProperty("sort")
	public String sort;
	@JsonProperty("listing_type")
	public String listingType;

}
