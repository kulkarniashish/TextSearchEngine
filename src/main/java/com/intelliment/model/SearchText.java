package com.intelliment.model;

import java.util.List;

public class SearchText {
	List<String> searchText;

	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}

	@Override
	public String toString() {
		return "SearchText [searchText=" + searchText + "]";
	}
}
