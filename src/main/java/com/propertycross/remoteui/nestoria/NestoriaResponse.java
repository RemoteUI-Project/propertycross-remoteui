
package com.propertycross.remoteui.nestoria;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "request", "response" })
public class NestoriaResponse {

	@JsonProperty("request")
	public Request request;
	@JsonProperty("response")
	public Response response;

}
