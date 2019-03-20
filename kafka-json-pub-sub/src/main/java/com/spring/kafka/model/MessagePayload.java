package com.spring.kafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessagePayload {
	@JsonProperty("description")
	private String description;
	@JsonProperty("identifier")
	private int identifier;
	
	public MessagePayload() {
		super();
	}
	public MessagePayload(String description, int identifier) {
		super();
		this.description = description;
		this.identifier = identifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
}
