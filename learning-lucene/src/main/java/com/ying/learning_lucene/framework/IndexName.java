package com.ying.learning_lucene.framework;

public enum IndexName {
	RAM(""), ENGLISH("d:\\english");

	private String dir;

	private IndexName(String dir) {
		this.dir = dir;
	}

	public String getDir() {
		return dir;
	}
}
