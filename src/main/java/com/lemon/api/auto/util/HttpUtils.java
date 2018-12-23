package com.lemon.api.auto.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;

public class HttpUtils {

	/**
	 * 用post方式发送请求
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	private static String post(String url, Map<String, String> paramsMap) {
		String respResult = "";
		try {
			// 生成post请求
			HttpPost post = new HttpPost(url);
			if (paramsMap != null) {
				// 设置post请求参数--请求体中间
				// 创建一个容器，将保存在HashMap中的参数保存到这个容器中
				List<NameValuePair> paramsList = new ArrayList<>();
				Set<String> keySet = paramsMap.keySet();
				for (String key : keySet) {
					String value = paramsMap.get(key);
					paramsList.add(new BasicNameValuePair(key, value));
				}
				// 创建一个原生form表单的请求体
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramsList);
				// 设置post请求体
				post.setEntity(entity);
			}
			
			String jSessionId=ParameterUtils.getGlobalData("JSESSIONID");
			if(jSessionId!=null){
				post.addHeader("Cookie",jSessionId);
			}
			
			// 创建一个HTTP发送客户端(HTTP发包客户端具备这样的功能：浏览器、postman、jmeter、fiddler、app)
			CloseableHttpClient httpClient = HttpClients.createDefault();

			// 发数据包
			CloseableHttpResponse response = httpClient.execute(post);
			//断言,响应状态码为200
			Assert.assertTrue(response.getStatusLine().getStatusCode()==200);
			//将会话id添加到全局数据池
			addSeesionIdToGlobalData(response);
			System.out.println(response);

			// 获取响应体中的内容
			HttpEntity respEntity = response.getEntity();
			respResult = EntityUtils.toString(respEntity);
			return respResult;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respResult;
	}
    
	/**
	 * 将登录成功后返回的token保存到全局数据池中间
	 * @param response
	 */
	private static void addSeesionIdToGlobalData(CloseableHttpResponse response) {
//		Header[] headers=response.getAllHeaders();
		
//		HttpResponseProxy{HTTP/1.1 200  [Set-Cookie: JSESSIONID=625A95CBC4EDC8A8129BDA03932DF914; Path=/futureloan; HttpOnly, Set-Cookie: rememberMe=deleteMe; Path=/futureloan; Max-Age=0; Expires=Mon, 10-Dec-2018 13:40:23 GMT, Content-Type: application/json;charset=UTF-8, Transfer-Encoding: chunked, Date: Tue, 11 Dec 2018 13:40:23 GMT] ResponseEntityProxy{[Content-Type: application/json;charset=UTF-8,Chunked: true]}}
//		{"status":1,"code":"10001","data":null,"msg":"登录成功"}

		//根据响应头的名称获得对应响应头
		Header header=response.getFirstHeader("Set-Cookie");
		if(header!=null){
			//获得Set_Cookie响应头的值
			String hearValue=header.getValue();
			//JSESSIONID=B7BB1357FD2D779EC0E74D37539B185D
			//trim()去除首尾的空格
			if(hearValue!=null && hearValue.trim().length()>0){
				//如果包含JSESSIONID字符串，就截取该值保存到全局数据池
				if(hearValue.contains("JSESSIONID")){
					String jsessionId=hearValue.substring(0, hearValue.indexOf(";"));
				    //保存到全局变量
					ParameterUtils.addGlobalData("JSESSIONID", jsessionId);
				}
			}
		    
		}
	}

	/**
	 * 用get方式发送请求 进一步优化get方法，将设置参数的容器换成HashMap，将所依赖的HttpCLient：List
	 * <NameValuePair>，提取到方法中
	 * 
	 * @param url
	 * @param parameters
	 * @return
	 */
	private static String get(String url, Map<String, String> paramsMap) {
		String respResult = "";
		try {
			if (paramsMap != null) {
				List<NameValuePair> paramsList = new ArrayList<>();

				Set<String> keySet = paramsMap.keySet();
				for (String key : keySet) {
					String value = paramsMap.get(key);
					paramsList.add(new BasicNameValuePair(key, value));
				}

				// 组合拼接参数
				url += ("?" + URLEncodedUtils.format(paramsList, "utf-8"));
			}
			HttpGet get = new HttpGet(url);
//			get.addHeader("Cookie", "JSESSIONID=B7BB1357FD2D779EC0E74D37539B185D");
			String jSessionId=ParameterUtils.getGlobalData("JSESSIONID");
			if(jSessionId!=null){
				get.addHeader("Cookie",ParameterUtils.getGlobalData("JSESSIONID"));
			}

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(get);
			//断言,响应状态码为200
			Assert.assertTrue(response.getStatusLine().getStatusCode()==200);
			
			//将会话id添加到全局数据池
			addSeesionIdToGlobalData(response);
			System.out.println(response);

			HttpEntity respEntity = response.getEntity();
			respResult = EntityUtils.toString(respEntity);
			return respResult;

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}

		return respResult;
	}

	public static String get1(String url, List<NameValuePair> parameters) {

		try {
			// 组合拼接参数
			url += ("?" + URLEncodedUtils.format(parameters, "utf-8"));
			HttpGet get = new HttpGet(url);

			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(get);

			HttpEntity respEntity = response.getEntity();
			// String respResult = EntityUtils.toString(respEntity);
			return EntityUtils.toString(respEntity);

		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 发包（分发各种请求）
	 * 
	 * @param apiId
	 * @param url
	 * @param paramsMap
	 * @return
	 */
	public static String request(String apiId, String url, Map<String, String> paramsMap) {
		String result = "";
		String method = ApiUtils.getRequestMethodByApiId(apiId);
		if ("post".equals(method)) {
			result = post(url, paramsMap);
		} else if ("get".equals(method)) {
			result = HttpUtils.get(url, paramsMap);
		}
		return result;

	}
	public static void main(String[] args) {
		
//		String url="http://120.79.150.210:8080/futureloan/mvc/api/member/login";
//		Map<String,String> paramsMap=new HashMap<String, String>();
//		paramsMap.put("mobilephone","13888888888");
//		paramsMap.put("pwd","123456");
//		String result=post(url, paramsMap);
//	    System.out.println(result);
		
		String url="http://120.79.150.210:8080/futureloan/mvc/api/financelog/getFinanceLogList?memberId=9";
	    String result=get(url, null);
		System.out.println(result);
	    
	    
	}
}
