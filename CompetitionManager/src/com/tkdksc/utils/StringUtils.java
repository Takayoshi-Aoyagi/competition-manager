package com.tkdksc.utils;

public class StringUtils {

	public static boolean isNullOrEmpty(String s) {
		if (s == null) {
			return true;
		}
		if ("".equals(s)) {
			return true;
		}
		return false;
	}

	public static String noBlank(String s) {
		return s.replaceAll("　", "");
	}
}
