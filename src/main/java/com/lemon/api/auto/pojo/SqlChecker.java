package com.lemon.api.auto.pojo;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 描述前置或者后置验证sql的pojo类 
 * LinkedHashMap--有序的
 * HashMap--无序的
 * 
 * @author hcl
 *
 */
public class SqlChecker {
	private String no;
	private String sql;
	private List<LinkedHashMap<String, String>> expectedResultList;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<LinkedHashMap<String, String>> getExpectedResultList() {
		return expectedResultList;
	}

	public void setExpectedResultList(List<LinkedHashMap<String, String>> expectedResultList) {
		this.expectedResultList = expectedResultList;
	}

	@Override
	public String toString() {
		return "SqlChecker [no=" + no + ", sql=" + sql + ", expectedResultList=" + expectedResultList + "]";
	}

	public SqlChecker(String no, String sql, List<LinkedHashMap<String, String>> expectedResultList) {
		super();
		this.no = no;
		this.sql = sql;
		this.expectedResultList = expectedResultList;
	}

}
