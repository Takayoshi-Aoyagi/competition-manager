package com.tkdksc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.arnx.jsonic.JSON;

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
		byte[] fileContentBytes;
		try {
			fileContentBytes = Files.readAllBytes(Paths.get("data/merge/merge.json"));
			// 読み込んだバイト列を UTF-8 でデコードして文字列にする
			String fileContentStr = new String(fileContentBytes, StandardCharsets.UTF_8);
			List<Map> list = (List<Map>)JSON.decode(fileContentStr);
			for (Map obj: list) {
				AggregationGroup type = AggregationGroup.getByName((String)obj.get("type"));
				String newName = (String) obj.get("name");
				String[] categories = (String[]) ((List<MergerInfo>) obj.get("classes")).toArray(new String[0]);
				MergerInfo mi = new MergerInfo(type, newName, categories);
				miList.add(mi);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

//		miList.add(new MergerInfo(AggregationGroup.TUL, "幼年・小学１年白帯の部（9,10級）", "小学 1～3年　白帯の部（9,10級）",
//				"幼年の部（級位・男女混合）"));
//		miList.add(new MergerInfo(AggregationGroup.TUL, "小学生　緑帯の部 （6級～5級）", "小学4～6年　緑帯の部 （6級～5級）　",
//				"小学 1～3年　緑帯の部 （6級～5級）　"));
//		miList.add(new MergerInfo(AggregationGroup.TUL, "成年男女　黄,緑帯の部（8級～5級）　", "成年女子　黄,緑帯の部（8級～5級）　",
//				"成年男子　黄・緑帯の部（8級～5級）　 　　"));
//		miList.add(new MergerInfo(AggregationGroup.MASSOGI, "幼年・小学１・２年　男子の部", "幼年の部（級位・男女混合）", "小学１・２年　男子の部"));
	}

	public static void main(String[] args) throws IOException, NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String inputDir = "input/dojo";
		if (args.length > 0) {
			inputDir = args[0];
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

		merge(categoryMap);

		new File("data/excel").mkdirs();
		new ExcelWriter(categoryMap, "data/excel").write2excel();
		
		new File("data/json/categories").mkdirs();
		for (String categoryName: categoryMap.keySet()) {
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
