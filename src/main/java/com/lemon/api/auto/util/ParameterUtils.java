package com.lemon.api.auto.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.events.NotationDeclaration;

public class ParameterUtils {
	// 全局数据池
	private static Map<String, String> globalDataMap = new HashMap<>();

	/**
	 * 添加数据到全局数据池中间
	 * 
	 * @param key
	 * @param value
	 */
	public static void addGlobalData(String key, String value) {
		globalDataMap.put(key, value);
	}

	/**
	 * 从全局数据池提取出对应值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getGlobalData(String key) {
		return globalDataMap.get(key);
	}

	/**
	 * 对字符串进行正则匹配，把提取出的符合规则的字符串替换为对应参数的值
	 * 
	 * @param str
	 * @return
	 */
	public static String getCommonStr(String str) {
		String regex = "\\$\\{(.*?)\\}"; // 贪婪模式
		// 编译正则表达式，得到模式
		Pattern pattern = Pattern.compile(regex);
		// 进行匹配
		Matcher matcher = pattern.matcher(str);
		// 迭代所有的符合规则的字符换
		while (matcher.find()) {
			// group(0) 0是检索到完整符合规则的字符串
			String totalStr = matcher.group(0);
			// 将参数名提取出来
			String paramsName = matcher.group(1);
			// 从参数名把对应的全局数据池中拿出来-->
			String paramsValue = globalDataMap.get(paramsName);
			// 把完整的字符串替换为对应参数对应的值
			str = str.replace(totalStr, paramsValue);
		}
		return getFunctionStr(str);
	}

	/**
	 * 通过反射调用FunctionUtils中间的方法，得到处理字符串的值 替换方法参数
	 * 
	 * @param str
	 * @return
	 */
	public static String getFunctionStr(String str) {
		// __\w*?\((\w*,?)*\) -->完整的方法+参数
		// __(\w*?)\(((\w*,?)*)\) -->用()进行分组
		// \w：匹配包括下划线在内的任意字符；*：匹配表达式任意次 ；
		// ？：非贪婪模式（0次或1次）；()相当于一个子表达式，所以在内容要匹配括号时，需要进行转义
		try {
			String regex = "__(\\w*?)\\(((\\w*,?)*)\\)";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(str);

			// 获得FunctionUtils字节码对象
			Class<FunctionUtils> clazz = FunctionUtils.class;
			while (matcher.find()) {
				// 完整的方法参数
				String totalStr = matcher.group(0);
				// 拿到方法名
				String functionName = matcher.group(1);
				// 拿到所有参数
				String parameterStr = matcher.group(2);
				String result = "";
				// 没有参数
				if ("".equals(parameterStr)) {
					Method method = clazz.getMethod(functionName);
					// 反射调用--->传null值表示以静态方式去调用
					result = (String) method.invoke(null);
				} else {// 有参数
						// 获得参数的数组
					String[] parameterArray = parameterStr.split(",");
					// 获得参数值的个数
					int parametersNum = parameterArray.length;
					// 定义一个字节码数组
					Class[] parameterTypes = new Class[parameterArray.length];
					for (int i = 0; i < parametersNum; i++) {
						parameterTypes[i] = String.class;
					}
					// 拿到方法名去反射调用FunctionUtils中对应的方法
					Method method = clazz.getMethod(functionName, parameterTypes);
					// 反射调用
					result = (String) method.invoke(null, parameterArray);

				}
				str = str.replace(totalStr, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;

	}

	public static void main(String[] args) throws Exception, SecurityException {
		// example1();

		// example2();
		globalDataMap.put("mobilephone", "13555555555");
		String str = "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"__md5(123456)\",\"regname\":\"__getRegName(aa,bb,cc,dd)\"}";
		System.out.println(getCommonStr(str));


	}

	private static void example2() throws Exception {
		String str = "{\"mobilephone\":\"__getMobilePhone()\",\"pwd\":\"__md5(123456)\",\"regname\":\"__getRegName(aa,bb,cc,dd)\"}";
		System.out.println(str);
		// __\w*?\((\w*,?)*\) -->完整的方法+参数
		// __(\w*?)\(((\w*,?)*)\) -->用()进行分组
		// \w：匹配包括下划线在内的任意字符；*：匹配表达式任意次 ；
		// ？：非贪婪模式（0次或1次）；()相当于一个子表达式，所以在内容要匹配括号时，需要进行转义

		String regex = "__(\\w*?)\\(((\\w*,?)*)\\)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		// 获得FunctionUtils字节码对象
		Class<FunctionUtils> clazz = FunctionUtils.class;
		while (matcher.find()) {
			// 完整的方法参数
			String totalStr = matcher.group(0);
			// 拿到方法名
			String functionName = matcher.group(1);
			// 拿到所有参数
			String parameterStr = matcher.group(2);
			String result = "";
			// 没有参数
			if ("".equals(parameterStr)) {
				Method method = clazz.getMethod(functionName);
				// 反射调用--->传null值表示以静态方式去调用
				result = (String) method.invoke(null);
			} else {// 有参数
					// 获得参数的数组
				String[] parameterArray = parameterStr.split(",");
				// 获得参数值的个数
				int parametersNum = parameterArray.length;
				// 定义一个字节码数组
				Class[] parameterTypes = new Class[parameterArray.length];
				for (int i = 0; i < parametersNum; i++) {
					parameterTypes[i] = String.class;
				}
				// 拿到方法名去反射调用FunctionUtils中对应的方法
				Method method = clazz.getMethod(functionName, parameterTypes);
				// 反射调用
				result = (String) method.invoke(null, parameterArray);

			}
			str = str.replace(totalStr, result);
			System.out.println("反射調用，替换后:" + str);

		}
	}

	private static void example1() {
		globalDataMap.put("mobilephone", "13555555555");
		globalDataMap.put("pwd", "abcdef");
		globalDataMap.put("regname", "tom");
		// String requestData =
		// "{\"mobilephone\":\"${mobilephone}\",\"pwd\":\"${pwd}\",\"regname\":\"${regname}\"}";
		// System.out.println(getCommonStr(requestData));

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
		String regex = "\\$\\{(.*?)\\}"; // 贪婪模式
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
