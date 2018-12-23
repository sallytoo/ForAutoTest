package com.lemon.api.auto.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBTest {
	public static void main(String[] args) {
		//1.插入
//		String sql = "insert into member(regname,pwd,mobilephone,leaveAmount,type) values(?,?,?,?,?);";
//		DBUTils.execute(sql, "jack", "1234545", "1372222222", 100.00,1);
		
	   //2.修改
//		String sql="update member set leaveAmount=10000.00;";
//		DBUTils.execute(sql);
//		String sql="update member set leaveAmount=? where id=?;";
//		DBUTils.execute(sql,10.0,1);
		
		//3.删除
//		String sql="delete from member where id=?";
//		DBUTils.execute(sql,1 );
		
		//4.查询
		/*String sql="select * from member where id<?";
		List<Map<String, String>> dataList=DBUTils.select(sql,4);
		for (Map<String, String> map : dataList) {
			System.out.println(map);
		}*/
		String sql="select leaveamount,type from member where mobilePhone='13888888888';";
		List<LinkedHashMap<String, String>> dataList=DBUTils.select(sql);
		for (LinkedHashMap<String, String> map : dataList) {
			System.out.println(map);
		}
	}
}
