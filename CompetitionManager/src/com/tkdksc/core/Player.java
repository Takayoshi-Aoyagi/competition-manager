package com.tkdksc.core;

import com.tkdksc.utils.StringUtils;

public class Player {
	private String name;
	private String grade;
	private String dojo;
	private String tul;
	private String massogi;
	private String special;
	private String teamTul;
	private String kana;
	private String entryFee;

	public Player(String name, String grade, String dojo, String tul, String massogi, String special,
			String teamTul, String kana, String entryFee) {
		super();
		this.name = name;
		this.grade = grade;
		this.dojo = dojo;
		this.tul = StringUtils.trim(tul);
		this.massogi = StringUtils.trim(massogi);
		this.special = special;
		this.teamTul = teamTul;
		this.kana = kana;
		this.entryFee = entryFee;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", grade=" + grade + ", dojo=" + dojo + ", tul=" + tul + ", massogi="
				+ massogi + ", special=" + special + ", teamTul=" + teamTul + ", kana=" + kana
				+ ", entryFee=" + entryFee + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDojo() {
		return dojo;
	}

	public void setDojo(String dojo) {
		this.dojo = dojo;
	}

	public String getTul() {
		return tul;
	}

	public void setTul(String tul) {
		this.tul = tul;
	}

	public String getMassogi() {
		return massogi;
	}

	public void setMassogi(String massogi) {
		this.massogi = massogi;
	}

	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public String getTeamTul() {
		return teamTul;
	}

	public void setTeamTul(String teamTul) {
		this.teamTul = teamTul;
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public String getEntryFee() {
		return entryFee;
	}

	public void setEntryFee(String entryFee) {
		this.entryFee = entryFee;
	}
}
