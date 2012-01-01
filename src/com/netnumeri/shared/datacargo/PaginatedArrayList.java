package com.netnumeri.shared.datacargo;


import java.util.List;

public class PaginatedArrayList<T> implements PaginatedList<T> {
	private List<T> list;
	private boolean lastPage;
	private boolean firstPage;

	public PaginatedArrayList(List<T> list, boolean lastPage, boolean firstPage) {
		this.list = list;
		this.lastPage = lastPage;
		this.firstPage = firstPage;
	}

	@Override
	public List<T> getList() {
		return list;
	}

	@Override
	public boolean isLastPage() {

		return lastPage;
	}

	@Override
	public boolean isFirstPage() {
		return firstPage;
	}
}
