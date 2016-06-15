package com.tkdksc.utils;

import java.util.List;

import com.tkdksc.core.Player;

public class Separator {

	public void separate(List<Player> playerList) {
		for (Player player: playerList) {
			if (!"幼年の部（級位・男女混合）".equals(player.getTul())) {
				continue;
			}
			String grade = player.getGrade();
			if (grade.equals("10級") || grade.equals("9級")) {
				player.setTul("幼年 白帯の部");
			} else {
				player.setTul("幼年 黄緑帯の部");
			}
		}
	}
}
