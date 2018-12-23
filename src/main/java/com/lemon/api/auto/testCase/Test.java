package com.lemon.api.auto.testCase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	/*
	 * public static void main(String[] args) { Map<String, String> paramMap =
	 * new HashMap<>(); paramMap.put("书1", "张三"); paramMap.put("书2", "张三");
	 * paramMap.put("书3", "李四"); paramMap.put("书5", "李四"); paramMap.put("书6",
	 * "张三"); paramMap.put("书7", "王五"); Set<String> keySet = paramMap.keySet();
	 * 
	 * FindMapMaxValue(paramMap); for (String key : keySet) {
	 * System.out.println(paramMap.get(key)); }
	 * 
	 * }
	 */

	/**
	 * 1:创建map对象，把数据put进去map，名称作为key，value为出书次数，每次put时出一次书value加1 2：转成list对象
	 * List<Map.Entry<String,Integer>> list = new ArrayList(map.entrySet());
	 * 3:通过Collections.sort方法对list中按规则进行排序，去了解下这个方法
	 * 
	 * 方法2： 1：设计表结构保存用户出书信息 2：jdbc插入语句，将数据插入到到表中 3： select count(id)，name from
	 * table_name group by id limit 1
	 * 
	 * @author hcl
	 * @throws IOException 
	 *
	 */
	public static void main(String[] args) throws IOException {
		File iFile=new File("E:\\1.txt");
		FileReader in=new FileReader(iFile);
		int c = 0;
		while(in.read()!=-1){
			System.out.println(c);
			continue;
		}
		in.close();
	}


}
