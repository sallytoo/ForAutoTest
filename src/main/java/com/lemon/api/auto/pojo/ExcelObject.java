package com.lemon.api.auto.pojo;

/**
 * 描述所有sheet的基类
 * 行号
 * @author hcl
 *
 */
public abstract class ExcelObject {
	private int rowNum;

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

}
