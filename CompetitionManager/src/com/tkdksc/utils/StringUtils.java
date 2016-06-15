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
	
	public static String trim(String str) {
		String ret = str.replaceAll("[\\s　]+", " ").trim();
		return ret;
	}
	
	public static void main(String[] args) {
		String s;
		String ret;
		s = "成年男子　黄・緑帯の部（8級～5級）　 　　";
		ret = StringUtils.trim(s);
		System.out.println(String.format("[%s]", s));
		System.out.println(String.format("[%s]", ret));
		
		s = "成年男子　青・赤帯の部 （4級～1級）　　";
		ret = StringUtils.trim(s);
		System.out.println(String.format("[%s]", s));
		System.out.println(String.format("[%s]", ret));
	}
}
