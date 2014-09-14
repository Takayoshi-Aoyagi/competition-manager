package com.tkdksc.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Category {

	private String name;
	private Map<String, List<Player>> map;
	
	public Category(String name) {
		this.name = name;
		map = new TreeMap<String, List<Player>>();
	}
	
	public String getName() {
		return name;
	}
	
	public Map<String, List<Player>> getMap() {
		return map;
	}
	
	public void addPlayer(String key, Player player) {
		List<Player> list = map.get(key);
		if (list == null) {
			list = new ArrayList<Player>();
			map.put(key, list);
		}
		list.add(player);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append("\n");
		for (String key : map.keySet()) {
			sb.append("\t").append(key).append("\n");
			List<Player> list = map.get(key);
			for (Player player : list) {
				sb.append("\t").append("\t").append(player).append("\n");
			}
		}
		return sb.toString();
	}
}
