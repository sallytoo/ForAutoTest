package com.lemon.api.auto.pojo;
/**
 * 描述与测试用例相关联信息的类
 * @author hcl
 *
 */
public class ApiInfo extends ExcelObject{
	// apiId(接口编号)
	private String apiId;
	// apiName(接口名称)
	private String apiName;
	// type(接口提交方式)
	private String type;
	// url(接口地址)
	private String url;

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "ApiInfo [apiId=" + apiId + ", apiName=" + apiName + ", type=" + type + ", url=" + url + ", getRowNum()="
				+ getRowNum() + "]";
	}

}
