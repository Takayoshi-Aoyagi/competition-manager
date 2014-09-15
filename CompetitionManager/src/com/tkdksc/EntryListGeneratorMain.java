package com.tkdksc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;
import com.tkdksc.excel.reader.entry.DojoEntryExcelReader;
import com.tkdksc.excel.writer.aggregate.ExcelWriter;
import com.tkdksc.utils.PlayerUtils;

public class EntryListGeneratorMain {

	public static void main(String[] args) throws IOException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		DojoEntryExcelReader er = new DojoEntryExcelReader();
		List<Player> playerList = er.readFiles();
		for (Player player : playerList) {
			System.out.println(player);
		}
		Category massogi = PlayerUtils.toMap(AggregationGroup.MASSOGI,
				playerList);
		Category tul = PlayerUtils.toMap(AggregationGroup.TUL, playerList);
		Category special = PlayerUtils.toMap(AggregationGroup.SPECIAL,
				playerList);
		Category teamTul = PlayerUtils.toMap(AggregationGroup.TEAM_TUL,
				playerList);
		Category dojo = PlayerUtils.toMap(AggregationGroup.DOJO, playerList);

		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		categoryMap.put(massogi.getName(), massogi);
		categoryMap.put(tul.getName(), tul);
		categoryMap.put(special.getName(), special);
		categoryMap.put(teamTul.getName(), teamTul);
		categoryMap.put(dojo.getName(), dojo);
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
