package com.lemon.api.auto.util;

public class FunctionUtils {
	public static String md5(String str) {
		return "yyy12213124eadswqeq213";
	}

	public static String getRegName(String str1, String str2, String str3) {
		return str1 + str2 + str3;
	}
	
	public static String getRegName(String str1, String str2, String str3, String str4) {
		return "小蜜蜂-"+str1 + str2 + str3+str4;
	}
	
	public static String getMobilePhone() {
		//去数据库查询出最大的手机号
		//手机号保证数据库不存在即可
		return "13888888888";
	}

}
