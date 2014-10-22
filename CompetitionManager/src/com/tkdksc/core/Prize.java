package com.tkdksc.core;

public class Prize {

	private String category;
	private String classification;
	private String rank;
	private String name;
	private Integer magic;

	public Prize(String category, String classification, String rank, String name) {
		super();
		this.category = category;
		this.classification = classification;
		this.rank = rank;
		this.name = name;
	}

	public Prize(String category, String classification, String rank, String name, int magic) {
		super();
		this.category = category;
		this.classification = classification;
		this.rank = rank;
		this.name = name;
		this.magic = magic;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMagic() {
		return magic;
	}

	public void setMagic(Integer magic) {
		this.magic = magic;
	}

	@Override
	public String toString() {
		return "Prize [category=" + category + ", classification=" + classification + ", rank=" + rank
				+ ", name=" + name + ", magic=" + magic + "]";
	}
}
