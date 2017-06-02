
package com.propertycross.remoteui.nestoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "img_height", "img_url", "img_width", "link_to_img" })
public class Attribution {

	@JsonProperty("img_height")
	public Integer imgHeight;
	@JsonProperty("img_url")
	public String imgUrl;
	@JsonProperty("img_width")
	public Integer imgWidth;
	@JsonProperty("link_to_img")
	public String linkToImg;

}
