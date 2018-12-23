package com.lemon.api.auto.testCase;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONObject;
import com.lemon.api.auto.base.BaseTester;
import com.lemon.api.auto.pojo.Apidetail;
import com.lemon.api.auto.pojo.CellData;
import com.lemon.api.auto.pojo.ExcelObject;
import com.lemon.api.auto.util.ApiUtils;
import com.lemon.api.auto.util.DataValidateUtils;
import com.lemon.api.auto.util.ExcelUtils;
import com.lemon.api.auto.util.HttpUtils;
import com.lemon.api.auto.util.StringUtils;
/**
 * 数据-->数据库
 * 数据验证-->查询数据库
 * 什么时候验证?
 *   执行测试用例之前：前置数据验证
 *   执行测试用例之后：后置数据验证
 * 怎么验证？-->jdbc操作
 *注册的功能：
 *    正向的测试用例：13888888888 123456 tom
 *    前置：查询数据库确定13888888888不存在
 *       第一条验证sql：一条记录，单个字段
 *       sql：select count(1) from member where MobilePhone='13888888888';
 *       预期结果：0
 *    后置：完成注册测试用例执行后，13888888888对应的用户信息已经保存到数据库中
 *       no:1  第一条验证sql：验证member表中已经存在该用户信息
 *       sql：select count(1) from member where MobilePhone='13888888888';
 *       预期结果：1
 *       
 *       no:2   第二条验证sql：一条sql，多个字段
 *       sql：select leaveamount,type from member where mobilePhone='13888888888';
 *       预期结果： leaveamount=0.00,type=1
 *      
 *       no:3  第三条验证sql：一条sql，多个字段
 *       分页查询用户列表：多条记录，多个字段
 *       sql：select regname,mobilePhone,type,leaveamount from member LIMIT 0,3;
 *       期望结果：[]
       	   	haha	13923446469	1	0.00
			小蜜蜂	13452620793	1	0.00
			小蜜蜂	13517315569	1	0.00
 *       
 *       1:上面的这些sql，每条sql的期望结果都应该保存到excel中-->excel表格保存的数据类型-->String-->json
 *      
      [  
        {
            "no":"1",
            "sql":"select count(1) from member where MobilePhone='13888888888';",
            "expectedResultList":
            [
               {"count(1)":"0"}                                  
             ]
        },
        {
            "no":"2",
            "sql":"select type,leaveamount from member where mobilePhone='13923446469';",
            "expectedResultList":
            [
               {"type":"1","leaveamount":"0.00"}                               
             ]
        },
        {
            "no":"3",
            "sql":"select regname,mobilePhone,type,leaveamount from member LIMIT 0,3;",
            "expectedResultList":
            [
               {"regname":"haha","mobilePhone":"13923446469","type":"1","leaveamount":"0.00"},
               {"regname":"jack","mobilePhone":"1372222222","type":"1","leaveamount":"0.00"},
               {"regname":"jack","mobilePhone":"1372222222","type":"1","leaveamount":"0.00"}                                  
             ]
        }
       ]
        
 
 * @author hcl
 *
 */
public class AllTestCase extends BaseTester{

	@DataProvider
	public Object[][] datas() {
		List<ExcelObject> objList = (List<ExcelObject>) ExcelUtils.readAllCaseExcel("/api_test_case_01.xlsx", 2, Apidetail.class);
		int size = objList.size();
		// 创建一个容器-->数据提供者需要的二维数组-->只要获得需要的信息即可
		Object[][] datas = new Object[size][6];
		for (int i = 0; i < size; i++) {
			Apidetail apidetail = (Apidetail) objList.get(i);
			datas[i][0] = apidetail.getCaseId();
			datas[i][1] = apidetail.getApiId();
			datas[i][2] = apidetail.getRequestData();
			datas[i][3] = apidetail.getExpectedReponseData();
			datas[i][4] = apidetail.getPreCheckSQL();			
			datas[i][5] = apidetail.getAfterCheckSQL();
		}
		return datas;
	}

	@Test(dataProvider = "datas")
	public void f(String caseId, String apiId, String requestData, String excepectedResponseData,String preCheckSQL,String afterCheckSQL) {
		//前置验证
		DataValidateUtils.preValidate(caseId,7,preCheckSQL);
		
		// 获取URL地址
		String url = ApiUtils.getUrlByApiId(apiId);
		// 将json数据转为Map<String,String>
		Map<String, String> paramsMap = (Map<String, String>) JSONObject.parse(requestData);
		// 发包,得到响应结果
		String actualResult = HttpUtils.request(apiId, url, paramsMap);
//		System.out.println(result);
			
		//要写的数据的收集
		ExcelUtils.addCellData(new CellData(caseId, 5, actualResult));
		
		//后置验证
		DataValidateUtils.afterValidate(caseId,9,afterCheckSQL);
		
		//断言:实际的响应结果等于期望的响应结果
		Assert.assertEquals(actualResult, excepectedResponseData);
		
		System.out.println(actualResult);
		
		// 将测试的实际结果写回到excel中去
//		ExcelUtils.writeExcel("target/classes/api_test_case_01.xlsx", 2, caseId, 5, result);

	}

}
