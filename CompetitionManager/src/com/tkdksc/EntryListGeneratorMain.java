package com.tkdksc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tkdksc.core.AggregationGroup;
import com.tkdksc.core.Category;
import com.tkdksc.core.Player;
import com.tkdksc.io.excel.reader.entry.DojoEntryExcelReader;
import com.tkdksc.io.excel.writer.aggregate.ExcelWriter;
import com.tkdksc.utils.PlayerUtils;
import com.tkdksc.utils.Separator;

public class EntryListGeneratorMain {

	private static int seq = 1;
	
	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputDir = "input/dojo";
		String mergeConfPath = "data/merge/merge.json";
		if (args.length > 0) {
			inputDir = args[0];
			mergeConfPath = args[1];
			// separateConfPath = args[2];
		}
		DojoEntryExcelReader er = new DojoEntryExcelReader(inputDir);
		List<Player> playerList = er.readFiles();

		// separator
		Separator separator = new Separator();
		separator.separate(playerList);

		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		for (AggregationGroup group : AggregationGroup.values()) {
			Category categorized = PlayerUtils.toMap(group, playerList);
			categoryMap.put(categorized.getName(), categorized);
		}
		//
		appendSeqNo(categoryMap);
		//
		dbg(categoryMap);

		// Merger merger = new Merger(mergeConfPath);
		// merger.merge(categoryMap);

		new File("data/excel").mkdirs();
		new ExcelWriter(playerList, categoryMap, "data/excel").write2excel();

		new File("data/json/categories").mkdirs();
		for (String categoryName : categoryMap.keySet()) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter("data/json/categories/" + categoryName + ".json"));
				Category category = categoryMap.get(categoryName);
				bw.write(category.toJSON());
			} finally {
				bw.close();
			}
		}
	}

	private static void appendSeqNo(Map<String, Category> categoryMap) {
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

	private static void dbg(Map<String, Category> categoryMap) {
		for (String key : categoryMap.keySet()) {
			System.out.println("============");
			Category category = categoryMap.get(key);
			System.out.println(category);
		}
	}
}
