package org.kangaroo.db.mybatis.common;

import java.util.List;

public class PageResult<E> {
	private List<E> result;//结果集
	private int rowCount;//总记录数
	private int startPage;//记录开始的位置
	private int pageSize;//每页显示多少条记录
	public List<E> getResult() {
		return result;
	}
	public void setResult(List<E> result) {
		this.result = result;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
