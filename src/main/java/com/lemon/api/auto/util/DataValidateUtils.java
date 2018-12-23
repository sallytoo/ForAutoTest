package com.lemon.api.auto.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.pojo.CellData;
import com.lemon.api.auto.pojo.SqlChecker;
import com.lemon.api.auto.pojo.ValidateResult;

public class DataValidateUtils {
	 /**
     * 前置验证
     * @param preCheckSQL
     */
	public static void preValidate(String caseId,int cellNum,String preCheckSQL) {
		if(StringUtils.isEmpty(preCheckSQL)){
			return;//返回调用处
		}
		// 解析json
		List<SqlChecker> sqlCheckList = JSONObject.parseArray(preCheckSQL, SqlChecker.class);
		

		// 创建一个容器，保存校验结果
		List<ValidateResult> validateResultList = new ArrayList<>();

		for (SqlChecker sqlChecker : sqlCheckList) {
			String no = sqlChecker.getNo();
			String sql = sqlChecker.getSql();
			List<LinkedHashMap<String, String>> expectedResultList = sqlChecker.getExpectedResultList();
			String expectResult = JSONObject.toJSONString(expectedResultList);
			
//			 System.out.println("前置期待结果："+expectResult);
			// 拿到sql去访问数据库
			List<LinkedHashMap<String, String>> actualResultList = DBUTils.select(sql);
			String actualResult = JSONObject.toJSONString(actualResultList);
//			 System.out.println("前置实际结果："+actualResult);

			// 回写结果到excel对应的测试用例
			// 断言
			if (actualResult.equals(expectResult)) {
				validateResultList.add(new ValidateResult(no, actualResultList, "成功"));

			} else {
				validateResultList.add(new ValidateResult(no, actualResultList, "失败"));
			}

		}
		String validateResultToWrite = JSONObject.toJSONString(validateResultList);
		// System.out.println(validateResultToWrite);
		
		//收集到回写数据池中间
		ExcelUtils.addCellData(new CellData(caseId, cellNum, validateResultToWrite));
	}
    /**
     * 后置验证
     * @param afterCheckSQL
     */
	public static void afterValidate(String caseId,int cellNum,String afterCheckSQL) {	
		if(StringUtils.isEmpty(afterCheckSQL)){
			return;//返回调用处
		}
		// 解析json
		List<SqlChecker> sqlCheckList = JSONObject.parseArray(afterCheckSQL, SqlChecker.class);

		// 创建一个容器，保存校验结果
		List<ValidateResult> validateResultList = new ArrayList<>();

		for (SqlChecker sqlChecker : sqlCheckList) {
			String no = sqlChecker.getNo();
			String sql = sqlChecker.getSql();
			List<LinkedHashMap<String, String>> expectedResultList = sqlChecker.getExpectedResultList();
			String expectResult = JSONObject.toJSONString(expectedResultList);
//			 System.out.println("后置期待结果："+expectResult);
			// 拿到sql去访问数据库
			List<LinkedHashMap<String, String>> actualResultList = DBUTils.select(sql);
			String actualResult = JSONObject.toJSONString(actualResultList);
//			 System.out.println("后置实际结果："+actualResult);

			// 回写结果到excel对应的测试用例
			// 断言
			if (actualResult.equals(expectResult)) {
				validateResultList.add(new ValidateResult(no, actualResultList, "成功"));

			} else {
				validateResultList.add(new ValidateResult(no, actualResultList, "失败"));
			}

		}
		String validateResultToWrite = JSONObject.toJSONString(validateResultList);
		System.out.println(validateResultToWrite);
		
		//收集到回写数据池中间
		ExcelUtils.addCellData(new CellData(caseId, cellNum, validateResultToWrite));
		
	}
	
	
	public static void main(String[] args) {
		String preSql=" [  \r\n" + 
				"        {\r\n" + 
				"            \"no\":\"1\",\r\n" + 
				"            \"sql\":\"select count(1) from member where MobilePhone='13888888888';\",\r\n" + 
				"            \"expectedResultList\":\r\n" + 
				"            [\r\n" + 
				"               {\"count(1)\":\"0\"}                                  \r\n" + 
				"             ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"no\":\"2\",\r\n" + 
				"            \"sql\":\"select type,leaveamount from member where mobilePhone='13923446469';\",\r\n" + 
				"            \"expectedResultList\":\r\n" + 
				"            [\r\n" + 
				"               {\"type\":\"1\",\"leaveamount\":\"0.00\"}                               \r\n" + 
				"             ]\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"no\":\"3\",\r\n" + 
				"            \"sql\":\"select regname,mobilePhone,type,leaveamount from member LIMIT 0,3;\",\r\n" + 
				"            \"expectedResultList\":\r\n" + 
				"            [\r\n" + 
				"               {\"regname\":\"haha\",\"mobilePhone\":\"13923446469\",\"type\":\"1\",\"leaveamount\":\"0.00\"},\r\n" + 
				"               {\"regname\":\"jack\",\"mobilePhone\":\"1372222222\",\"type\":\"1\",\"leaveamount\":\"100.00\"},\r\n" + 
				"               {\"regname\":\"jack\",\"mobilePhone\":\"1372222222\",\"type\":\"1\",\"leaveamount\":\"100.00\"}                                  \r\n" + 
				"             ]\r\n" + 
				"        }\r\n" + 
				"       ]";
		
//		System.out.println(preSql);
			
		//解析json
		List<SqlChecker> sqlCheckList = JSONObject.parseArray(preSql, SqlChecker.class);
		 
		//创建一个容器，保存校验结果
		List<ValidateResult> validateResultList=new ArrayList<>();

		for (SqlChecker sqlChecker : sqlCheckList) {
			String no = sqlChecker.getNo();
			String sql = sqlChecker.getSql();
			List<LinkedHashMap<String, String>> expectedResultList = sqlChecker.getExpectedResultList();
			String expectResult = JSONObject.toJSONString(expectedResultList);
			 System.out.println(expectResult);
			// 拿到sql去访问数据库
			List<LinkedHashMap<String, String>> actualResultList = DBUTils.select(sql);
			String actualResult = JSONObject.toJSONString(actualResultList);
			 System.out.println(actualResult);
			
			//回写结果到excel对应的测试用例
						
			/**多条sql
			[
			 {"no":"1","actualResultList":[{},{}],"result":"success"},
			 {"no":"2","actualResultList":[{},{}],"result":"fail"},
			 {"no":"3","actualResultList":[{},{}],"result":"success"},
			 ]
			**/
			//断言
			if(actualResult.equals(expectResult)){
//				System.out.println("本条sql验证通过");
				validateResultList.add(new ValidateResult(no,actualResultList,"success"));
				
			}else{
//				System.out.println("本条sql验证不通过");
				validateResultList.add(new ValidateResult(no,actualResultList,"fail"));
			}

		}
		
		String validateResultToWrite = JSONObject.toJSONString(validateResultList);
		System.out.println(validateResultToWrite);
	}
   
}
