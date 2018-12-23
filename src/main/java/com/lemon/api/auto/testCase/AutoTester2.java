package com.lemon.api.auto.testCase;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test
public class AutoTester2 {

	@DataProvider
	public Object[][] login_data() {
		Object[][] datas = { { "13825161923", "e10adc3949ba59abbe56e057f20f883e", "1", "登录成功" },
				{ "13825161921", "e10adc3949ba59abbe56e057f20f883e", "1", "信息错误" },
				{ "1382516", "e10adc3949ba59abbe56e057f20f883e", "1", "信息错误" },
				{"","e10adc3949ba59abbe56e057f20f883e","1","信息错误"},
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
		try {
			// 1.请求地址
			String uri = "http://39.108.136.60:8085/lmcanon_web_auto/mvc/member/api/member/login";
			// 2.生成post请求
			HttpPost post = new HttpPost(uri);
			
//			添加请求头
//			post.addHeader("Accept","application/json");
//			post.addHeader("Accept","text/xml");
			
			// 3.设置post请求参数--请求体中间
			// 创建一个容器，保存每个参数
			List<NameValuePair> parameters = new ArrayList<>();
			parameters.add(new BasicNameValuePair("mobilephone", mobilephone));
			parameters.add(new BasicNameValuePair("password", pwd));
			parameters.add(new BasicNameValuePair("type", type));

			// 4.创建一个原生form表单的请求体
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
			// 5.设置post请求体
			post.setEntity(entity);

			// 6.创建一个HTTP发送客户端(HTTP发包客户端具备这样的功能：浏览器、postman、jmeter、fiddler、app)
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// 7.发数据包-->获得响应
			CloseableHttpResponse response = httpClient.execute(post);
			// 获得相应，关注什么信息
			// 1)响应行:HTTP/1.1 200 OK
			StatusLine line = response.getStatusLine();
			System.out.println(line.getProtocolVersion());
			System.out.println(line.getStatusCode());
			System.out.println(line.getReasonPhrase());
			AssertJUnit.assertEquals(line.getStatusCode(), 200);
			// 2）响应头
			/*
			 * Server: Apache-Coyote/1.1 Set-Cookie:
			 * JSESSIONID=565CC34E15A6BF4FA2F29D30C8D19DE4;
			 * Path=/lmcanon_web_auto Set-Cookie: rememberMe=deleteMe;
			 * Path=/lmcanon_web_auto; Max-Age=0; Expires=Wed, 21-Nov-2018
			 * 13:17:17 GMT Content-Type: application/json;charset=UTF-8 Date:
			 * Thu, 22 Nov 2018 13:17:17 GMT Content-Length: 57
			 */
			Header[] headers = response.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + ":" + header.getValue());
			}

			// 3）响应体{"status":1,"code":"1001","data":"","msg":"登录成功"}
			HttpEntity respEntity = response.getEntity();
			String respResult = EntityUtils.toString(respEntity);
			System.out.println(respResult);
			Assert.assertTrue(respResult.contains(partialResult));

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}