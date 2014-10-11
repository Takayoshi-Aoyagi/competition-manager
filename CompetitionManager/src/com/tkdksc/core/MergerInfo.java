package com.tkdksc.core;

import java.util.ArrayList;
import java.util.List;

public class MergerInfo {

	private AggregationGroup group;
	private String newName;
	List<String> list = new ArrayList<String>();

	public MergerInfo(AggregationGroup group, String newName, String... categories) {
		this.group = group;
		this.newName = newName;
		for (String category : categories) {
			list.add(category);
		}
	}

	public AggregationGroup getGroup() {
		return group;
	}

	public String getNewName() {
		return newName;
	}

	public List<String> getList() {
		return list;
	}
}
