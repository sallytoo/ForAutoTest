package com.lemon.api.auto.testCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.lemon.api.auto.util.HttpUtils;

public class GetTester_bak {
	@DataProvider
	public Object[][] registerDatas(){
		Object[][] datas={
				{"13923446469","1234587", "haha"},
				{"1392344","1234587", "haha"},
				{"139234464","1234587", "haha"},
				{"13923748458","123677", "23你好"},
				{"","", ""},
		};
		return datas;
		
	}
	@Test(dataProvider="registerDatas")
	public void f4(String mobilephone,String pwd,String regname) {

		String url = "http://47.106.199.126:8084/futureloan/mvc/api/member/register";
		HashMap<String, String> paramsMap = new HashMap<>();
		paramsMap.put("mobilephone", mobilephone);
		paramsMap.put("pwd", pwd);
		paramsMap.put("regname", regname);
//		String result=HttpUtils.get(url, paramsMap);
//		System.out.println(result);
	}
	
//	@Test
	public void f3() {
		
		String url = "http://47.106.199.126:8084/futureloan/mvc/api/member/register";
		HashMap<String, String> parameters = new HashMap<>();
		parameters.put("mobilephone", "13923446668");
		parameters.put("pwd", "1234567");
		parameters.put("regname", "wopao");
//		HttpUtils.get(url, parameters);
	}

	// @Test
	public void f2() {

		String url = "http://47.106.199.126:8084/futureloan/mvc/api/member/register";

		List<NameValuePair> parameters = new ArrayList<>();
		parameters.add(new BasicNameValuePair("mobilephone", "13923446667"));
		parameters.add(new BasicNameValuePair("pwd", "123456"));
		parameters.add(new BasicNameValuePair("regname", "pao&name=pao"));
		HttpUtils.get1(url, parameters);
	}

	// @Test
	public void f1() {
		try {
			String url = "http://47.106.199.126:8084/futureloan/mvc/api/member/register";

			List<NameValuePair> parameters = new ArrayList<>();
			parameters.add(new BasicNameValuePair("mobilephone", "13923456666"));
			parameters.add(new BasicNameValuePair("pwd", "123456"));
			parameters.add(new BasicNameValuePair("regname", "pao&name=pao"));
			// 组合拼接参数
			String paString = URLEncodedUtils.format(parameters, "utf-8");
			url += ("?" + paString);
			HttpGet get = new HttpGet(url);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(get);

			StatusLine line = response.getStatusLine();
			System.out.println(line.getProtocolVersion());
			System.out.println(line.getStatusCode());
			System.out.println(line.getReasonPhrase());
			AssertJUnit.assertEquals(line.getStatusCode(), 200);

			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}

			HttpEntity respEntity = response.getEntity();
			String respResult = EntityUtils.toString(respEntity);
			System.out.println(respResult);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
