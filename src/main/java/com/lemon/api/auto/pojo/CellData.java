package com.lemon.api.auto.pojo;
/**
 * 描述回写数据cell的pojo类
 * @author hcl
 *
 */
public class CellData {
	private String caseId;
	private int cellNum;
	private String result;

	public CellData(String caseId, int cellNum, String result) {
		super();
		this.caseId = caseId;
		this.cellNum = cellNum;
		this.result = result;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public int getCellNum() {
		return cellNum;
	}

	public void setCellNum(int cellNum) {
		this.cellNum = cellNum;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
