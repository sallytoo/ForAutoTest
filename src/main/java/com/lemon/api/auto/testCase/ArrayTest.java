package com.lemon.api.auto.testCase;

import java.util.Arrays;

public class ArrayTest {

	public static void main(String[] args) {
		int[][] a = new int[1][];
		int[] ac = new int[5];
		ac[0] = 1;
		ac[1] = 2;
		ac[2] = 3;
		ac[3] = 4;
		ac[4] = 5;
		
		a[0] = ac;
		//Arrays.toString获得一维数组
	    System.out.println(Arrays.toString(ac));
	   //Arrays.deepToString获得多维数组
	    System.out.println(Arrays.deepToString(a));

		
		String[][] deepArray = new String[][] {{"John", "Mary"}, {"Alice", "Bob"}};
		System.out.println(Arrays.toString(deepArray));
		//output: [[Ljava.lang.String;@106d69c, [Ljava.lang.String;@52e922]
		System.out.println(Arrays.deepToString(deepArray));
	}

}
