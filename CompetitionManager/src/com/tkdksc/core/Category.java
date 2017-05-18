package com.tkdksc.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.arnx.jsonic.JSON;

public class Category {

	private AggregationGroup group;
	private TreeMap<String, List<Player>> map;

	public Category(AggregationGroup group) {
		this.group = group;
		if (group == AggregationGroup.DOJO) {
			this.map = new TreeMap<String, List<Player>>(new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					String kw = "川口";
					if (o1.equals(kw) && o2.equals(kw)) {
						return 0;
					}
					if (o1.indexOf(kw) >= 0) {
						return -1;
					}
					return o1.compareTo(o2);
				}
			});
		} else {
			this.map = new TreeMap<String, List<Player>>();
		}
	}

	public String getName() {
		return group.getKana();
	}

	public TreeMap<String, List<Player>> getMap() {
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
		sb.append(group).append("\n");
		for (String key : map.keySet()) {
			sb.append("\t").append(String.format("[%s]", key)).append("\n");
			List<Player> list = map.get(key);
			for (Player player : list) {
				sb.append("\t").append("\t").append(player).append("\n");
			}
		}
		return sb.toString();
	}

	public String toJSON() {
		Map<String, Object> groupMap = new HashMap<String, Object>();
		groupMap.put("category", group.name());
		groupMap.put("data", map);
		return JSON.encode(groupMap, true);
	}
}
