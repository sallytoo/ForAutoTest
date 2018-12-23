package com.lemon.api.auto.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.Apidetail;

public class ApiUtils {
	// 数据载体的变化：以apiId作为key，然后url作为值-->HashMap
		// 以apiId作为key，然后type作为值-->HashMap
		// 总而言之，给我一个apiId，就可以获得对应api值的基本信息-->用什么类型来保存一个api的基本信息(APiInfo)
		// 存放ApiInfo信息的容器
		static Map<String, ApiInfo> apiInfoMap = new HashMap();
		static Map<String, Apidetail> apidetailMap = new HashMap();

		static {
			/*Object[][] apiInfoDatas = ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 1);
			// 遍历出每一行数据
			for (Object[] apiInfoArray : apiInfoDatas) {
				ApiInfo apiInfo = new ApiInfo();
				String apiId = apiInfoArray[0].toString();
				apiInfo.setApiId(apiId);
				apiInfo.setApiName(apiInfoArray[1].toString());
				apiInfo.setType(apiInfoArray[2].toString());
				apiInfo.setUrl(apiInfoArray[3].toString());
				// System.out.println(apiInfo);
				// 放到apiInfoMap容器中
				apiInfoMap.put(apiId, apiInfo);
			}*/
			
			List<ApiInfo> apiInfoList=(List<ApiInfo>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 1,ApiInfo.class);
			for (ApiInfo apiInfo : apiInfoList) {
				apiInfoMap.put(apiInfo.getApiId(), apiInfo);
			}
			
			List<Apidetail> apidetailList=(List<Apidetail>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 2,Apidetail.class);
			for (Apidetail apidetail : apidetailList) {
				apidetailMap.put(apidetail.getCaseId(), apidetail);
			}
			

		}
		
		/**
		 * 通过apiId获得对应的url
		 * 
		 * @param apiId
		 * @return
		 */
		public static String getUrlByApiId(String apiId) {

			return apiInfoMap.get(apiId).getUrl();
		}

		/**
		 * 通过apiId获得请求方法：get or post
		 * 
		 * @param apiId
		 * @return
		 */
		public static String getRequestMethodByApiId(String apiId) {
			return apiInfoMap.get(apiId).getType();
		}
		/**
		 * 通过caseId获得行号
		 * @param caseId
		 * @return
		 */
		public static int getRowNum(String caseId){
			return apidetailMap.get(caseId).getRowNum();
		}
		
		
		public static void main(String[] args) {
//			Set<String> keySet = apiInfoMap.keySet();
//			for (String key : keySet) {
//				System.out.println(apiInfoMap.get(key).getUrl());
//
//			}
			System.out.println(apidetailMap.get("7").getRowNum());
			
			// apiInfoMap.get("1");
		}
}
