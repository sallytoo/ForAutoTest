package com.lemon.api.auto.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * JDBC工具类
 * 
 * @author hcl
 * @date 2018年11月21
 * @desc 提供通用性增删改查方法
 */
public class DBUTils {
	// hard code--硬编码，写死的代码---耦合度较高，不利于维护--数据分离、数据解耦、数据抽取
	private static String user;
	private static String password;
	private static String url;
	private static String driver;

	/**
	 * 通过静态块： 保证注册驱动，以及读取数据库连接信息的操作只运行一遍
	 */
	static {
		Properties properties = new Properties();
		try {
			properties.load(DBUTils.class.getResourceAsStream("/jdbc.properties"));
			driver = properties.getProperty("jdbc.driver");
			url = properties.getProperty("jdbc.url");
			user = properties.getProperty("jdbc.user");
			password = properties.getProperty("jdbc.password");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
   /**
    * 数据库的连接
    * @return
    */
	private static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 痛点： 
	 * 1.当进行增、删、改操作时，sql各不相同，参数不确定---通用性 执行增删改的操作：
	 *  1）SQL语句
	 *  2）参数：占位符对应的参数值--不确定类型、不确定个数--调和（用object类型）
	 * 
	 */

	public static void execute(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int length = params.length;
			for (int i = 0; i < length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt);

		}

	}

	/**
	 *
	 * 1.查询的sql不一样 
	 * 2.查询的参数不一样 
	 * 3.结果集的记录数、字段数是不确定的---容器（数据载体） 
	 * 0条或多条记录--ArrayList
	 * 每条记录有多个字段，并且字段的名称不确定 --->List<Map<String,String>> 
	 * excel一行 = properties文件 = 数据库的一行= xml = json = Java对象 = HashMap（key-value)
	 * 
	 * @param mobilephone
	 * @param pwd
	 */
	public static List<LinkedHashMap<String, String>> select(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<LinkedHashMap<String, String>> dateList = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			int length = params.length;
			for (int i = 0; i < length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			resultSet = pstmt.executeQuery();
			// 获得结果集的元数据（描述数据的数据）
			ResultSetMetaData meteDate = resultSet.getMetaData();
			// 获得列数
			int columnCount = meteDate.getColumnCount();
			// 创建一个List<Map<String, String>>容器，存放所有的记录
			dateList = new ArrayList<LinkedHashMap<String, String>>();
			// 遍历每一条记录
			while (resultSet.next()) {
				// 创建一个Map对象，保存每一行记录
				LinkedHashMap<String, String> oneDataMap = new LinkedHashMap<String, String>();
				// 解析每条记录的值
				for (int i = 1; i <= columnCount; i++) {
					// 获得每一列的值
					String value = resultSet.getString(i);
					// 获取字段名，作为key
//					String columnName = meteDate.getColumnName(i);
					//采用别名作为key
					String lableName=meteDate.getColumnLabel(i);
					// 把每一个字段对应的值存放到map中间
					oneDataMap.put(lableName, value);
				}
				// 把每一行记录添加到对应的list
				dateList.add(oneDataMap);
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn, pstmt, resultSet);
		}
		return dateList;
	}

	/**
	 * 关闭资源
	 * @param conn  连接
	 * @param pstmt 预处理陈述对象
	 */
	private static void close(Connection conn, PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭资源
	 * @param conn 连接          
	 * @param pstmt 预处理陈述对象           
	 * @param resultSet 结果集
	 *            
	 */
	private static void close(Connection conn, PreparedStatement pstmt, ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		close(conn, pstmt);
	}

	/**
	 * 1.新增、插入
	 */
	private static void insert(String regName, String pwd, String mobilephone) {
		String sql = "insert into member(regname,pwd,mobilephone) values(?,?,?);";
		DBUTils.execute(sql, regName, pwd, mobilephone);
	}

}
