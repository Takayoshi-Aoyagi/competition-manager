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
import com.tkdksc.utils.Merger;
import com.tkdksc.utils.PlayerUtils;

public class EntryListGeneratorMain {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputDir = "input/dojo";
		String mergeConfPath = "data/merge/merge.json";
		if (args.length > 0) {
			inputDir = args[0];
			mergeConfPath = args[1];
		}
		DojoEntryExcelReader er = new DojoEntryExcelReader(inputDir);
		List<Player> playerList = er.readFiles();
		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		for (AggregationGroup group : AggregationGroup.values()) {
			Category categorized = PlayerUtils.toMap(group, playerList);
			categoryMap.put(categorized.getName(), categorized);
		}
		//
		dbg(categoryMap);

		Merger merger = new Merger(mergeConfPath);
		merger.merge(categoryMap);

		new File("data/excel").mkdirs();
		new ExcelWriter(categoryMap, "data/excel").write2excel();

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

	private static void dbg(Map<String, Category> categoryMap) {
		for (String key : categoryMap.keySet()) {
			System.out.println("============");
			Category category = categoryMap.get(key);
			System.out.println(category);
		}
	}
}
