package com.tkdksc.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.tkdksc.core.Category;
import com.tkdksc.core.Player;

public class PlayerUtils {

	public static Category toMap(String methodName,
			List<Player> players) throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Method method = Player.class.getMethod("get" + methodName, null);
		Category category = new Category(methodName);
		for (Player player : players) {
			String key = (String) method.invoke(player, null);
			category.addPlayer(key, player);
		}
		return category;
	}
}
