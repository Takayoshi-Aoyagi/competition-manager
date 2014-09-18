package com.tkdksc;

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

public class EntryListGeneratorMain {

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DojoEntryExcelReader er = new DojoEntryExcelReader();
		List<Player> playerList = er.readFiles();
		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		for (AggregationGroup group : AggregationGroup.values()) {
			Category categorized = PlayerUtils.toMap(group, playerList);
			categoryMap.put(categorized.getName(), categorized);
		}
		//
		dbg(categoryMap);

		new ExcelWriter().write2excel(categoryMap);
	}

	private static void dbg(Map<String, Category> categoryMap) {
		for (String key : categoryMap.keySet()) {
			System.out.println("============");
			Category category = categoryMap.get(key);
			System.out.println(category);
		}
	}
}
