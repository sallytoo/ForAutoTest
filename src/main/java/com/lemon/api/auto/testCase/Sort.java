package com.lemon.api.auto.testCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sort {
	public static void main(String[] args) {
		Object[][] data={
				{1,"张三","书1"},
				{2,"张三","书2"},
				{3,"李四","书3"},
				{4,"李四","书4"},
				{5,"张三","书5"},
				{6,"王五","书6"},
		};
		Map<String, Integer> map=new HashMap<String,Integer>();
		for (Object[] item : data) {
			String key=(String)item[1];
			//判断是否存在该key值，若存在就给value+1，不存在，就设置value的值为1
			if(map.containsKey(key)){
				map.put(key, map.get(key)+1);
			}else{
				map.put(key, 1);
			}
		}
		
		List<Map.Entry<String,Integer>> list=new ArrayList(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String,Integer>>(){		
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getValue()-o2.getValue();
			}	
		});
		System.out.println(list.get(list.size()-1).getKey());
	}
}
