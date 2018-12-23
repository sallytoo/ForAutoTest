package com.lemon.api.auto.util;

import java.util.List;

import com.jayway.jsonpath.JsonPath;

public class KeyInfoValidateUtils {
	public static void main(String[] args) {
		String str="{\r\n" + 
				"    \"store\": {\r\n" + 
				"        \"book\": [\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"reference\",\r\n" + 
				"                \"author\": \"Nigel Rees\",\r\n" + 
				"                \"title\": \"Sayings of the Century\",\r\n" + 
				"                \"price\": 8.95\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"Evelyn Waugh\",\r\n" + 
				"                \"title\": \"Sword of Honour\",\r\n" + 
				"                \"price\": 12.99\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"Herman Melville\",\r\n" + 
				"                \"title\": \"Moby Dick\",\r\n" + 
				"                \"isbn\": \"0-553-21311-3\",\r\n" + 
				"                \"price\": 8.99\r\n" + 
				"            },\r\n" + 
				"            {\r\n" + 
				"                \"category\": \"fiction\",\r\n" + 
				"                \"author\": \"J. R. R. Tolkien\",\r\n" + 
				"                \"title\": \"The Lord of the Rings\",\r\n" + 
				"                \"isbn\": \"0-395-19395-8\",\r\n" + 
				"                \"price\": 22.99\r\n" + 
				"            }\r\n" + 
				"        ],\r\n" + 
				"        \"bicycle\": {\r\n" + 
				"            \"color\": \"red\",\r\n" + 
				"            \"price\": 19.95\r\n" + 
				"        }\r\n" + 
				"    },\r\n" + 
				"    \"expensive\": 10\r\n" + 
				"}";
//		String jsonPath="$.store.book[*].author";
//		List<String>  authors=JsonPath.read(str,jsonPath);
//		System.out.println(authors);
		
		
		
		
	//{"status":1,"code":"10001","data":[{"id":null,"investId":"2","createTime":"2018-12-11 23:00:08","terms":"1","unfinishedInterest":"1.0","unfinishedPrincipal":"0","repaymentDate":"2019-01-11 23:00:08","actualRepaymentDate":null,"status":"0"},{"id":null,"investId":"2","createTime":"2018-12-11 23:00:08","terms":"2","unfinishedInterest":"1.0","unfinishedPrincipal":"0","repaymentDate":"2019-02-11 23:00:08","actualRepaymentDate":null,"status":"0"},{"id":null,"investId":"2","createTime":"2018-12-11 23:00:08","terms":"3","unfinishedInterest":"1.0","unfinishedPrincipal":"100.00","repaymentDate":"2019-03-11 23:00:08","actualRepaymentDate":null,"status":"0"},{"id":null,"investId":"1","createTime":"2018-12-11 23:00:08","terms":"1","unfinishedInterest":"1.0","unfinishedPrincipal":"0","repaymentDate":"2019-01-11 23:00:08","actualRepaymentDate":null,"status":"0"},{"id":null,"investId":"1","createTime":"2018-12-11 23:00:08","terms":"2","unfinishedInterest":"1.0","unfinishedPrincipal":"0","repaymentDate":"2019-02-11 23:00:08","actualRepaymentDate":null,"status":"0"},{"id":null,"investId":"1","createTime":"2018-12-11 23:00:08","terms":"3","unfinishedInterest":"1.0","unfinishedPrincipal":"100.00","repaymentDate":"2019-03-11 23:00:08","actualRepaymentDate":null,"status":"0"}],"msg":"生成回款计划成功"}
	String jsonStr = "{\"status\":1,\"code\":\"10001\",\"data\":[{\"id\":null,\"investId\":\"2\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"1\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"0\",\"repaymentDate\":\"2019-01-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"},{\"id\":null,\"investId\":\"2\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"2\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"0\",\"repaymentDate\":\"2019-02-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"},{\"id\":null,\"investId\":\"2\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"3\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"100.00\",\"repaymentDate\":\"2019-03-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"},{\"id\":null,\"investId\":\"1\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"1\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"0\",\"repaymentDate\":\"2019-01-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"},{\"id\":null,\"investId\":\"1\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"2\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"0\",\"repaymentDate\":\"2019-02-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"},{\"id\":null,\"investId\":\"1\",\"createTime\":\"2018-12-11 23:00:08\",\"terms\":\"3\",\"unfinishedInterest\":\"1.0\",\"unfinishedPrincipal\":\"100.00\",\"repaymentDate\":\"2019-03-11 23:00:08\",\"actualRepaymentDate\":null,\"status\":\"0\"}],\"msg\":\"生成回款计划成功\"}";
//	String jsonPath="$..data[0].repaymentDate"; ///..为递归调用
//	List<String>  repaymentDate=JsonPath.read(jsonStr,jsonPath);//递归调用后，搜索结果为数组
	String jsonPath="$.data[0].repaymentDate";
    String  repaymentDate=JsonPath.read(jsonStr,jsonPath);
    System.out.println(repaymentDate);
    
   /**
    1.关键信息的验证
    [
      {"jsonPath":"$..xx.xxx[1]","value":"1"},
      {"jsonPath":"$..xx.xxx[1]","value":"aaa"},
      {"jsonPath":"$..xx.xxx[1]","value":"bbb"}
    ] 
     2.参数化-->数据池中间
    [
      {"jsonPath":"$..xx.xxx[1].loadId","key":"1"},-->从这个接口提取
      {"jsonPath":"$..xx.xxx[1]","key":"aaa"},
      {"jsonPath":"$..xx.xxx[1]","key":"bbb"}
    ] 
    */
    
	}
}
