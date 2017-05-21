package com.tkdksc.utils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class SequenceNumberAppender {
	
	private static int seq;

	public static void execute(Map<String, Category> categoryMap) {
		seq = 1;
		Category category = categoryMap.get("道場");
		TreeMap<String, List<Player>> map = category.getMap();
		System.out.println(map.keySet());
		map.forEach((dojo, players) -> {
			System.out.println(dojo);
			players.forEach(player -> {
				player.setSeq(seq);
				seq++;
			});
		});
	}
}
