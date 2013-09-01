package com.mcafee.scor.safety.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.JsonArray;

public class Commonutilities {
	public static <T> boolean isNullOrEmpty(Collection<T> collection){
		return (collection == null || collection.isEmpty());
	}

	public static JsonArray limitJsonArraySize(JsonArray jsonArray, int limit) {
		int inputArraySize = jsonArray.size();
		if(limit == 0){
			return new JsonArray();
		}
		if(inputArraySize <= limit){
			return jsonArray;
		}
		int d = inputArraySize/limit;
		int index = 0;
		JsonArray resultJsonArray = new JsonArray();
		for(int i=0;i<limit - 1;i++){
			index = i * d;
			resultJsonArray.add(jsonArray.get(index));
		}
		resultJsonArray.add(jsonArray.get(inputArraySize - 1));
		return resultJsonArray;
	}
	
	public static <T> List<T> limitListSize(List<T> inputList, int limit) {
		int inputListSize = inputList.size();
		if(limit == 0){
			return new ArrayList<T>();
		}
		if(inputListSize <= limit){
			return inputList;
		}
		int d = inputListSize/limit;
		int index = 0;
		List<T> resultList = new ArrayList<T>();
		for(int i=0;i<limit - 1;i++){
			index = i * d;
			resultList.add(inputList.get(index));
		}
		resultList.add(inputList.get(inputListSize - 1));
		return resultList;
	}
}
