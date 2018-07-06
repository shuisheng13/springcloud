package com.pactera.utlis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtils {

	private static ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	public static Map<?, ?> JsonToMap(String jsonString) {
		Map<?, ?> map = null;
		try {
			map = mapper.readValue(jsonString, Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static String ObjectToJson(Object object) {
		String writeValueAsString = null;
		try {
			writeValueAsString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return writeValueAsString;
	}

	public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
		JavaType javaType = getCollectionType(ArrayList.class, clazz);
		List<T> readValue = null;
		try {
			readValue = mapper.readValue(jsonString, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}

	public static <T> T jsonToClass(String jsonString, Class<T> clazz) {
		T readValue = null;
		try {
			readValue = mapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return readValue;
	}

	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}

}