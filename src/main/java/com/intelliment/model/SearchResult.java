package com.intelliment.model;

import java.util.Map;

public class SearchResult {
	private Map<String, Integer> counts;

	public Map<String, Integer> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, Integer> counts) {
		this.counts = counts;
	}
}
