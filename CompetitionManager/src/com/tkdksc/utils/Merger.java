package com.tkdksc.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.arnx.jsonic.JSON;

import com.tkdksc.core.AggregationGroup;
import com.tkdksc.core.Category;
import com.tkdksc.core.MergerInfo;
import com.tkdksc.core.Player;

public class Merger {

	private List<MergerInfo> miList = new ArrayList<MergerInfo>();

	public Merger(String mergeConfPath) throws IOException {
		readConf(mergeConfPath);
	}

	private void readConf(String mergeConfPath) throws IOException {
		byte[] fileContentBytes;
		fileContentBytes = Files.readAllBytes(Paths.get("data/merge/merge.json"));
		// 読み込んだバイト列を UTF-8 でデコードして文字列にする
		String fileContentStr = new String(fileContentBytes, StandardCharsets.UTF_8);
		List<Map> list = (List<Map>) JSON.decode(fileContentStr);
		for (Map obj : list) {
			AggregationGroup type = AggregationGroup.getByName((String) obj.get("type"));
			String newName = (String) obj.get("name");
			String[] categories = (String[]) ((List<String>) obj.get("classes")).toArray(new String[0]);
			MergerInfo mi = new MergerInfo(type, newName, categories);
			miList.add(mi);
		}
	}

	public void merge(Map<String, Category> categoryMap) {
		for (MergerInfo mi : miList) {
			List<Player> mergedPlayers = new ArrayList<Player>();
			AggregationGroup group = mi.getGroup();
			String groupName = group.getKana();
			Category category = categoryMap.get(groupName);
			System.out.println(category);
			Map<String, List<Player>> map = category.getMap();

			for (String classification : mi.getList()) {
				List<Player> players = map.get(classification);
				if (players == null) {
					System.err.println(String.format("Error in [%s]", classification));
					System.err.println(mi);
				}
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
}
