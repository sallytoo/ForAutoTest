package com.lemon.api.auto.pojo;

/**
 * 参数化-->接口关联
 * 
 * @author hcl
 *
 */
public class Var {
	private String jsonPath;
	private String key;// 保存到全局数据池中的key

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Var [jsonPath=" + jsonPath + ", key=" + key + "]";
	}

}
