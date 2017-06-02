
package com.propertycross.remoteui.nestoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "center_lat", "center_long", "long_title", "place_name", "title" })
public class Location {

	@JsonProperty("center_lat")
	public Double centerLat;
	@JsonProperty("center_long")
	public Double centerLong;
	@JsonProperty("long_title")
	public String longTitle;
	@JsonProperty("place_name")
	public String placeName;
	@JsonProperty("title")
	public String title;

}
