package com.spring.kafka.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	
	private JacksonUtil(){
		
	}

	public static String getToString(Object src ) {
		try {
			return mapper.writeValueAsString(src);
		} catch (JsonProcessingException e) {
			
		}
		return null;
	}
}
