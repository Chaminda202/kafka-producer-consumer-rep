package com.spring.kafka.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaRequest {
	@JsonProperty("name")
	String name;
	@JsonProperty("position")
	String position;
	@JsonProperty("age")
	int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
