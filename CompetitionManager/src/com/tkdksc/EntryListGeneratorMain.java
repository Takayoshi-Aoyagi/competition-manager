package com.tkdksc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tkdksc.core.AggregationGroup;
import com.tkdksc.core.Category;
import com.tkdksc.core.MergerInfo;
import com.tkdksc.core.Player;
import com.tkdksc.io.excel.reader.entry.DojoEntryExcelReader;
import com.tkdksc.io.excel.writer.aggregate.ExcelWriter;
import com.tkdksc.utils.PlayerUtils;

public class EntryListGeneratorMain {

	private static List<MergerInfo> miList = new ArrayList<MergerInfo>();
	static {
		miList.add(new MergerInfo(AggregationGroup.TUL, "幼年・小学１年白帯の部（9,10級）", "小学 1～3年　白帯の部（9,10級）",
				"幼年の部（級位・男女混合）"));
		miList.add(new MergerInfo(AggregationGroup.TUL, "小学生　緑帯の部 （6級～5級）", "小学4～6年　緑帯の部 （6級～5級）　",
				"小学 1～3年　緑帯の部 （6級～5級）　"));
		miList.add(new MergerInfo(AggregationGroup.TUL, "成年男女　黄,緑帯の部（8級～5級）　", "成年女子　黄,緑帯の部（8級～5級）　",
				"成年男子　黄・緑帯の部（8級～5級）　 　　"));
		miList.add(new MergerInfo(AggregationGroup.MASSOGI, "幼年・小学１・２年　男子の部", "幼年の部（級位・男女混合）", "小学１・２年　男子の部"));
	}

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputDir = "input/dojo";
		DojoEntryExcelReader er = new DojoEntryExcelReader(inputDir);
		List<Player> playerList = er.readFiles();
		Map<String, Category> categoryMap = new TreeMap<String, Category>();
		for (AggregationGroup group : AggregationGroup.values()) {
			Category categorized = PlayerUtils.toMap(group, playerList);
			categoryMap.put(categorized.getName(), categorized);
		}
		//
		dbg(categoryMap);

		merge(categoryMap);

		new ExcelWriter(categoryMap, "output").write2excel();
	}

	private static void merge(Map<String, Category> categoryMap) {
		//
		for (MergerInfo mi : miList) {
			List<Player> mergedPlayers = new ArrayList<Player>();
			AggregationGroup group = mi.getGroup();
			String groupName = group.getKana();
			Category category = categoryMap.get(groupName);
			System.out.println(category);
			Map<String, List<Player>> map = category.getMap();

			for (String classification : mi.getList()) {
				List<Player> players = map.get(classification);
				mergedPlayers.addAll(players);
				map.remove(classification);
				for (Player player : players) {
					switch (group) {
					case TUL:
						player.setTul(mi.getNewName());
						break;
					case MASSOGI:
						player.setMassogi(mi.getNewName());
						break;
					default:
						throw new AssertionError();
					}
				}
			}
			map.put(mi.getNewName(), mergedPlayers);
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
