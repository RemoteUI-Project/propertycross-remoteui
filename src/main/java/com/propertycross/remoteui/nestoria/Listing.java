
package com.propertycross.remoteui.nestoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "bathroom_number", "bedroom_number", "car_spaces", "commission",
		"construction_year", "datasource_name", "img_height", "img_url", "img_width", "keywords",
		"latitude", "lister_url", "listing_type", "location_accuracy", "longitude", "price",
		"price_currency", "price_formatted", "price_high", "price_low", "price_type",
		"property_type", "room_number", "size", "size_type", "size_unit", "summary", "thumb_height",
		"thumb_url", "thumb_width", "title", "updated_in_days", "updated_in_days_formatted",
		"lister_name", "floor" })
public class Listing {

	@JsonProperty("bathroom_number")
	public Integer bathroomNumber;
	@JsonProperty("bedroom_number")
	public Integer bedroomNumber;
	@JsonProperty("car_spaces")
	public Integer carSpaces;
	@JsonProperty("commission")
	public Integer commission;
	@JsonProperty("construction_year")
	public Integer constructionYear;
	@JsonProperty("datasource_name")
	public String datasourceName;
	@JsonProperty("img_height")
	public Integer imgHeight;
	@JsonProperty("img_url")
	public String imgUrl;
	@JsonProperty("img_width")
	public Integer imgWidth;
	@JsonProperty("keywords")
	public String keywords;
	@JsonProperty("latitude")
	public Double latitude;
	@JsonProperty("lister_url")
	public String listerUrl;
	@JsonProperty("listing_type")
	public String listingType;
	@JsonProperty("location_accuracy")
	public Integer locationAccuracy;
	@JsonProperty("longitude")
	public Double longitude;
	@JsonProperty("price")
	public Integer price;
	@JsonProperty("price_currency")
	public String priceCurrency;
	@JsonProperty("price_formatted")
	public String priceFormatted;
	@JsonProperty("price_high")
	public Integer priceHigh;
	@JsonProperty("price_low")
	public Integer priceLow;
	@JsonProperty("price_type")
	public String priceType;
	@JsonProperty("property_type")
	public String propertyType;
	@JsonProperty("room_number")
	public Integer roomNumber;
	@JsonProperty("size")
	public Integer size;
	@JsonProperty("size_type")
	public String sizeType;
	@JsonProperty("size_unit")
	public String sizeUnit;
	@JsonProperty("summary")
	public String summary;
	@JsonProperty("thumb_height")
	public Integer thumbHeight;
	@JsonProperty("thumb_url")
	public String thumbUrl;
	@JsonProperty("thumb_width")
	public Integer thumbWidth;
	@JsonProperty("title")
	public String title;
	@JsonProperty("updated_in_days")
	public Integer updatedInDays;
	@JsonProperty("updated_in_days_formatted")
	public String updatedInDaysFormatted;
	@JsonProperty("lister_name")
	public String listerName;
	@JsonProperty("floor")
	public Integer floor;

}
