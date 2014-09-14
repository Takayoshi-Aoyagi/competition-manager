package com.tkdksc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;
import com.tkdksc.excel.ExcelReader;
import com.tkdksc.excel.ExcelWriter;
import com.tkdksc.utils.PlayerUtils;

public class Main {

	public static void main(String[] args) throws IOException,
			NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		ExcelReader er = new ExcelReader();
		List<Player> playerList = er.readFiles();
		for (Player player : playerList) {
			System.out.println(player);
		}
		Category massogi = PlayerUtils.toMap("Massogi", playerList);
		Category tul = PlayerUtils.toMap("Tul", playerList);
		Category special = PlayerUtils.toMap("Special", playerList);
		Category teamTul = PlayerUtils.toMap("TeamTul", playerList);
		Category dojo = PlayerUtils.toMap("Dojo", playerList);

		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		categoryMap.put("マッソギ", massogi);
		categoryMap.put("トゥル", tul);
		categoryMap.put("スペシャル", special);
		categoryMap.put("団体トゥル", teamTul);
		categoryMap.put("道場", dojo);
		//
		for (String key : categoryMap.keySet()) {
			System.out.println("============");
			Category category = categoryMap.get(key);
			System.out.println(category);
		}
		
		new ExcelWriter().write2excel(categoryMap);
	}
}
