package com.lemon.api.auto.testCase;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class PostTester {

	@DataProvider
	public Object[][] login_data() {
		Object[][] datas = { { "13825161923", "e10adc3949ba59abbe56e057f20f883e", "1", "登录成功" },
				{ "13825161921", "e10adc3949ba59abbe56e057f20f883e", "1", "信息错误" },
				{ "1382516", "e10adc3949ba59abbe56e057f20f883e", "1", "信息错误" },
				// {"","e10adc3949ba59abbe56e057f20f883e","1","信息错误"},
				{ "1382516", "e10adc3949ba59abbe56e", "1", "信息错误" } };
		return datas;
	}

	/**
	 * 1.指定请求方法：post
	 * 2.接口url：http://39.108.136.60:8085/lmcanon_web_auto/mvc/member/api/member/
	 * login
	 * 3.参数：mobilephone=13825161923&password=e10adc3949ba59abbe56e057f20f883e&
	 * type=1 4.发送数据包 5.查看响应
	 */
	@Test(dataProvider = "login_data")
	public void post(String mobilephone, String pwd, String type, String partialResult) {

		// 1.请求地址
		String url = "http://39.108.136.60:8085/lmcanon_web_auto/mvc/member/api/member/login";
		// 3.设置post请求参数--请求体中间
		// 创建一个容器，保存每个参数
		HashMap<String,String> paramsMap=new HashMap<>();
		paramsMap.put("mobilephone", mobilephone);
		paramsMap.put("password", pwd);
		paramsMap.put("password", pwd);
		paramsMap.put("partialResult", partialResult);
//		String respResult=HttpUtils.post(url, paramsMap);
//		System.out.println(respResult);
//		AssertJUnit.assertTrue(respResult.contains(partialResult));

	}

}