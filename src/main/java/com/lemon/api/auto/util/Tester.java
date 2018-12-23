package com.lemon.api.auto.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tester {
	// 全局数据池
	static Map<String, String> globalDataMap = new HashMap<>();

	public static void main(String[] args) {
		globalDataMap.put("mobilephone", "13555555555");
		globalDataMap.put("pwd", "abcdef");
		globalDataMap.put("regname", "tom");

		// 删除数据库
		// 操作excel，全局替换
		// 操作查询出最大的手机号+1-->新手机号-->把excel中间数据进行替换（参数化-->接口关联）
		// 从数据查询心的手机号-->内存-->变量
		// ${mobilephone}
		// 从excel解析出来的数据，把其中的参数替换为内存中间对应变量的值
		String requestData = "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"${pwd}\",\"regname\":\"${regname}\"}";
		System.out.println("替换前：" + requestData);
		// String
		// requestData="{\"mobilephone\":\"13555555555\",\"pwd\":\"abcdef\",\"regname\":\"柠檬班\"}";

		// 检索、提取符合规则的字符内容-->正则表达式
		// ${变量名}-->${mobilephone} ${pwd} ${XXX}
		// \$\{.*\}
		// String regex = "\\$\\{.*\\}"; //贪婪模式
		String regex = "\\$\\{(.*?)\\}"; // 非贪婪模式
		// 编译正则表达式，得到模式
		Pattern pattern = Pattern.compile(regex);
		// 进行匹配
		Matcher matcher = pattern.matcher(requestData);
		// 迭代所有的符合规则的字符换
		while (matcher.find()) {
			// group(0) 0是检索到完整符合规则的字符串
			String totalStr = matcher.group(0);
			System.out.println(totalStr);
			// 将参数名提取出来
			String paramsName = matcher.group(1);
			System.out.println(paramsName);
			// 从参数名把对应的全局数据池中拿出来-->
			String paramsValue = globalDataMap.get(paramsName);
			System.out.println(paramsValue);
			// 把完整的字符串替换为对应参数对应的值
			requestData = requestData.replace(totalStr, paramsValue);
		}
		System.out.println("替换后：" + requestData);

	}
}
