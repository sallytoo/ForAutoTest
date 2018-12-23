package com.lemon.api.auto.pojo;
/**
 * 描述的测试用例的类
 * @author hcl
 *
 */
public class Apidetail extends ExcelObject {
	// CaseId(用例编号)
	private String caseId;
	// ApiId(接口编号)
	private String apiId;
	// RequestData(接口请求参数)
	private String requestData;
	// ExpectedReponseData(期望响应数据)
	private String expectedReponseData;
	// ActualReponseData实际响应数据
	private String actualReponseData;

	// PreCheckSQL(前置验证SQL)
	private String preCheckSQL;

	// PreCheckResult(前置验证结果)
	private String preCheckResult;

	// AfterCheckSQL(后置验证SQL)
	private String afterCheckSQL;

	// AfterCheckResult(后置验证结果)
	private String afterCheckResult;

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getExpectedReponseData() {
		return expectedReponseData;
	}

	public void setExpectedReponseData(String expectedReponseData) {
		this.expectedReponseData = expectedReponseData;
	}

	public String getActualReponseData() {
		return actualReponseData;
	}

	public void setActualReponseData(String actualReponseData) {
		this.actualReponseData = actualReponseData;
	}

	public String getPreCheckSQL() {
		return preCheckSQL;
	}

	public void setPreCheckSQL(String preCheckSQL) {
		this.preCheckSQL = preCheckSQL;
	}

	public String getPreCheckResult() {
		return preCheckResult;
	}

	public void setPreCheckResult(String preCheckResult) {
		this.preCheckResult = preCheckResult;
	}

	public String getAfterCheckSQL() {
		return afterCheckSQL;
	}

	public void setAfterCheckSQL(String afterCheckSQL) {
		this.afterCheckSQL = afterCheckSQL;
	}

	public String getAfterCheckResult() {
		return afterCheckResult;
	}

	public void setAfterCheckResult(String afterCheckResult) {
		this.afterCheckResult = afterCheckResult;
	}

	@Override
	public String toString() {
		return "Apidetail [caseId=" + caseId + ", apiId=" + apiId + ", requestData=" + requestData
				+ ", expectedReponseData=" + expectedReponseData + ", actualReponseData=" + actualReponseData
				+ ", preCheckSQL=" + preCheckSQL + ", preCheckResult=" + preCheckResult + ", afterCheckSQL="
				+ afterCheckSQL + ", afterCheckResult=" + afterCheckResult + ", getRowNum()=" + getRowNum() + "]";
	}

}
