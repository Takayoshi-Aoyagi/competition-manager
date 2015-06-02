package com.tkdksc.core;

public enum AggregationGroup {
	MASSOGI("Massogi", "マッソギ"), TUL("Tul", "トゥル"), SPECIAL("Special", "スペシャル"), TEAM_TUL("TeamTul", "団体トゥル"), DOJO(
			"Dojo", "道場");

	private String methodName;
	private String kana;

	private AggregationGroup(String methodName, String kana) {
		this.methodName = methodName;
		this.kana = kana;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getKana() {
		return kana;
	}
	
	public static AggregationGroup getByName(String name) {
		for (AggregationGroup value: values()) {
			if (name.equals(value.name())) {
				return value;
			}
		}
		throw new AssertionError();
	}
}
