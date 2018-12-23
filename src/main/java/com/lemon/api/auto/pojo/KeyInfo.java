package com.lemon.api.auto.pojo;
/**
 * 关键信息
 * @author hcl
 *
 */
public class KeyInfo {
	private String jsonPath;
	private String value;

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return "KeyInfo [jsonPath=" + jsonPath + ", value=" + value + "]";
	}

}
