package com.bikes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bikes.util.BikeRestUtil;

public class BikeServiceImpl implements BikeService {

	public void getTop20Bikes(String jsonUrl) {
		try {
			String jsonResponse = BikeRestUtil.getTopTwenttyBikes(jsonUrl);
			parseBikeJson(jsonResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseBikeJson(String jsonString) throws IOException {
		JSONArray bikeList = new JSONArray(jsonString);
		Map<String, Integer> bikeCombinaitions = new HashMap<String, Integer>();
		Integer value = null;
		int count = 0;
		ArrayList<ArrayList<String>> bikes = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < bikeList.length(); i++) {
			JSONArray jsonArray = bikeList.getJSONObject(i).getJSONArray(
					"bikes");

			if (jsonArray.length() > 1) {
				count++;
				ArrayList<String> group1 = new ArrayList<String>();
				for (Object obj : jsonArray) {
					String jObj = (String) obj;
					group1.add(jObj);
				}

				bikes.add(group1);

			}
		}

		int k = 2;
		for (ArrayList<String> group : bikes) {
			Collections.sort(group);
			recurse(group, "", 0, group.size(), k, bikeCombinaitions);
		}
		System.out.println("--------------------------------------------");

		HashMap<String, Integer> desendingOrderMap = sortByValue(bikeCombinaitions);
		int i = 0;
		for (Map.Entry<String, Integer> entry : desendingOrderMap.entrySet()) {

			
			System.out.println(entry.getKey() + " combination " + ++i
					+ " & entries " + entry.getValue());

		}
	}

	public static void recurse(ArrayList<String> group, String out, int i,
			int n, int k, Map<String, Integer> bikeCount) {
		Integer value = null;
		// invalid input
		if (k > n) {
			return;
		}

		// base case: combination size is k
		if (k == 0) {
			System.out.println(out);
			value = bikeCount.get(out);
			if (value != null) {
				value = value + 1;
			} else {
				value = 1;
			}
			bikeCount.put(out, value);
			return;
		}

		// start from next index till last index
		for (int j = i; j < n; j++) {
			// add current element A[j] to solution & recurse for next index
			// (j+1) with one less element (k-1)
			recurse(group, out + "," + (group.get(j)), j + 1, n, k - 1,
					bikeCount);

			// uncomment below code to handle duplicates
			while (j < n - 1 && group.get(j) == group.get(j + 1)) {
				j++;
			}
		}
	}

	public static HashMap<String, Integer> sortByValue(
			Map<String, Integer> bikeCount) {
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				bikeCount.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
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
