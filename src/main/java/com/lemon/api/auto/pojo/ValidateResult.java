package com.lemon.api.auto.pojo;

import java.util.LinkedHashMap;
import java.util.List;
/**
 * 保存前置验证或者后置验证后的实际结果
 * @author hcl
 *
 */
public class ValidateResult {
	// "no":"1","actualResultList":[{},{}],"result":"success"
	private String no;
	private List<LinkedHashMap<String, String>> actualResultList;
	private String result;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public List<LinkedHashMap<String, String>> getActualResultList() {
		return actualResultList;
	}

	public void setActualResultList(List<LinkedHashMap<String, String>> actualResultList) {
		this.actualResultList = actualResultList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String toString() {
		return "ValidateResult [no=" + no + ", actualResultList=" + actualResultList + ", result=" + result + "]";
	}

	public ValidateResult(String no, List<LinkedHashMap<String, String>> actualResultList, String result) {
		super();
		this.no = no;
		this.actualResultList = actualResultList;
		this.result = result;
	}

}
