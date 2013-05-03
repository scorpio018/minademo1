package com.scorpio.minademo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapDemp {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("one", "1");
		map.put("two", "2");
		map.put("three", "3");
		Set<String> set = map.keySet();
		for (String string : set) {
			System.out.println(string);
		}
	}
}
