package com.mcafee.scor.safety.common;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class TestCommonUtilities extends TestCase{

	@Test
	public void testLimitJsonArraySize() throws IOException{
		int limit = 8;
		int[] arr = getSampleArray(24);
		String jsonString = new Gson().toJson(arr);
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray actual = Commonutilities.limitJsonArraySize(jsonArray, limit);
		assertEquals(limit, actual.size());
	}
	
	@Test
	public void testLimitJsonArraySize_undersized() throws IOException{
		int limit = 8;
		int[] arr = getSampleArray(6);
		String jsonString = new Gson().toJson(arr);
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray actual = Commonutilities.limitJsonArraySize(jsonArray, limit);
		assertEquals(6, actual.size());
	}
	
	@Test
	public void testLimitJsonArraySize_commonDiffOne() throws IOException{
		int limit = 8;
		int[] arr = getSampleArray(12);
		String jsonString = new Gson().toJson(arr);
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray actual = Commonutilities.limitJsonArraySize(jsonArray, limit);
		assertEquals(limit, actual.size());
	}
	
	@Test
	public void testLimitJsonArraySize_emptyInputArray() throws IOException{
		int limit = 0;
		int[] arr = getSampleArray(0);
		String jsonString = new Gson().toJson(arr);
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray actual = Commonutilities.limitJsonArraySize(jsonArray, limit);
		assertEquals(limit, actual.size());
	}
	
	@Test
	public void testLimitJsonArraySize_zeroArrayLimit() throws IOException{
		int limit = 0;
		int[] arr = getSampleArray(1);
		String jsonString = new Gson().toJson(arr);
		JsonArray jsonArray = new JsonParser().parse(jsonString).getAsJsonArray();
		JsonArray actual = Commonutilities.limitJsonArraySize(jsonArray, limit);
		assertEquals(limit, actual.size());
	}

	private int[] getSampleArray(int size) {
		int[] arr = new int[size];
		for(int i=0;i<size;i++){
			arr[i] = i;
		}
		return arr;
	}

}
