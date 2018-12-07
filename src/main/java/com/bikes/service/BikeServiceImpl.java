package com.bikes.service;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.bikes.util.BikeRestUtil;

public class BikeServiceImpl implements BikeService {
	
	//Method to get the top bikes after parsing the json and output to string for sorting

	public void getTop20Bikes(String jsonUrl) {
		try {
			String jsonResponse = BikeRestUtil.getTopTwenttyBikes(jsonUrl);
			parseBikeJson(jsonResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// To parse jsonObject and manage the bike entries
	
	private void parseBikeJson(String jsonString) throws IOException {
		JSONArray bikeList = new JSONArray(jsonString);
		Map<String, Integer> bikeCount = new HashMap<String, Integer>();
		Integer value = null;
		for (int i = 0; i < bikeList.length(); i++) {
			value = bikeCount.get(bikeList.getJSONObject(i).getJSONArray("bikes").getString(0));
			if (value != null) {
				value = value + 1;
			} else {
				value = 1;
			}
			bikeCount.put(bikeList.getJSONObject(i).getJSONArray("bikes").getString(0), value);
		}

		HashMap<String, Integer> desendingOrderMap = sortByValue(bikeCount);
		int i = 0;
		for (Map.Entry<String, Integer> entry : desendingOrderMap.entrySet()) {
			if (i < 20) {
				System.out.println(entry.getKey() + " - " + entry.getValue() + " entries ");
			}
			i++;
		}
	}
	
	//Method to sort the values.
	
	public static HashMap<String, Integer> sortByValue(Map<String, Integer> bikeCount) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(bikeCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		return temp;
	}
}
