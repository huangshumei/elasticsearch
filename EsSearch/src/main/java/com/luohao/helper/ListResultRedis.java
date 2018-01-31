package com.luohao.helper;

import java.io.Serializable;
import java.util.List;

public class ListResultRedis<T> implements Serializable {
	private static final long serialVersionUID = 4568179806518625592L;
	private String result = "1";
	private int pageindex;
	private int pages;
	private int total;
	private String msg;
	private String rows;

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPageindex() {
		return this.pageindex;
	}

	public void setPageindex(int pageIndex) {
		this.pageindex = pageIndex;
	}

	public int getPages() {
		return this.pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getRows() {
		return this.rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}
}