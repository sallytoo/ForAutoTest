package com.lemon.api.auto.testCase;

import java.util.HashMap;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.api.auto.util.ExcelUtils;
import com.lemon.api.auto.util.HttpUtils;

public class GetTester {
	
	@DataProvider
	public Object[][] registerDatas() {
		Object[][] datas = ExcelUtils.readExcel("/api.xlsx");
		return datas;
	}
	
	@Test(dataProvider = "registerDatas")
	public void f4(String mobilephone, String pwd, String regname) {

		String url = "http://47.106.199.126:8084/futureloan/mvc/api/member/register";
		HashMap<String, String> paramsMap = new HashMap<>();
		paramsMap.put("mobilephone", mobilephone);
		paramsMap.put("pwd", pwd);
		paramsMap.put("regname", regname);
//		String result = HttpUtils.get(url, paramsMap);
//		System.out.println(result);

	}
	
}
