package com.lemon.api.auto.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.lemon.api.auto.pojo.ApiInfo;
import com.lemon.api.auto.pojo.CellData;
import com.lemon.api.auto.pojo.ExcelObject;

public class ExcelUtils {
	/**
	 * 要写的cell数据池
	 */
	private static List<CellData> ceilDataToWriteList=new ArrayList<>();
	
	/**
	 * 添加要回写的数据
	 * @param cellData
	 */
	public static void addCellData(CellData cellData){
		ceilDataToWriteList.add(cellData);
	}
	
	
	/**
	 * 获得所有要写的cellData数据
	 * @return
	 */
	public static List<CellData> getCeilDataToWriteList() {
		return ceilDataToWriteList;
	}




	/**
	 * 将所有的测试用例写在一个excel中,读取该测试用例
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @param sheetNum
	 *            sheet编号，从1开始，传1表示第一个sheet，即索引为0的sheet
	 * @return
	 */
     
	public static List<? extends ExcelObject> readAllCaseExcel(String excelPath, int sheetNum, Class<? extends ExcelObject> clazz) {
		List<ExcelObject> objoList = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();
			// 创建一个数组，保存表头信息
			String[] columnArray = new String[lastCellNum];
			// 循环遍历第一行，获得表头
			for (int k = 0; k < lastCellNum; k++) {
				// 若存在值为空，则设置该值为空
				Cell cell = fistrow.getCell(k, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// 设置列的类型
				cell.setCellType(CellType.STRING);
				// 获得该列的值
				String columnName = cell.getStringCellValue();
				columnArray[k] = columnName;
			}

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			objoList = new ArrayList<>();
			for (int i = 1; i <= lastRowNum; i++) {
				// 通过字节码对象实例化一个对象
				ExcelObject obj = clazz.newInstance();
				//设置行号（行号是从1开始）
				obj.setRowNum(i+1);
				// 获得索引对应的行
				Row row = sheet.getRow(i);
				
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					
					//原始字符串的参数的替换
					String commonStr=ParameterUtils.getCommonStr(cellValue);
					
					// 得到setter方法的名称
					String setMethodName = "set" + columnArray[j].substring(0, columnArray[j].indexOf("("));
					// 得到setter方法
					Method setMethod = clazz.getMethod(setMethodName, String.class);
					// 反射调用该方法
//					setMethod.invoke(obj, cellValue);
					setMethod.invoke(obj, commonStr);

				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				objoList.add(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objoList;
	}
	/**
	 * 批量回写数据
	 * @param sourceExcelPath 读取的excel源文件路径
	 * @param targetExcelPath 写入后的excel保存路径
	 * @param sheetNum 对应sheet中那个sheet
	 */
	public static void batchWrite(String sourceExcelPath,String targetExcelPath, int sheetNum) {
		InputStream inp = null;
		Workbook workbook = null;
		OutputStream outputStream = null;

		try {
			inp = ExcelUtils.class.getResourceAsStream(sourceExcelPath);
//			inp =new FileInputStream(new File(sourceExcelPath));
			workbook = WorkbookFactory.create(inp);
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
			List<CellData> cellDataToWriteList=ExcelUtils.getCeilDataToWriteList();
			//拿出所有要回写的数据
			for (CellData cellData : cellDataToWriteList) {
				//获得行号
				int rowNum=ApiUtils.getRowNum(cellData.getCaseId());
				//获得对应的行
				Row row=sheet.getRow(rowNum-1);
				Cell cellToWrite = row.getCell(cellData.getCellNum() - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				cellToWrite.setCellType(CellType.STRING);
				cellToWrite.setCellValue(cellData.getResult());	
			}
			outputStream = new FileOutputStream(new File(targetExcelPath));
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(workbook!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inp!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	}

	/**
	 * 写回数据到excel
	 * 性能问题：
	 * 1：如果写1000次数据，就要io操作1000次
	 *   -->只读写一次可以ok了（把所有的要写的相关信息先收集起来，所有测试用例执行完毕后在写）
	 * 2:如果caseId对应的行比较靠后，前面遍历次数就会非常多
	 *   -->告诉我一个caseId，就能拿到对应的行（rowNum）
	 * @param excelPath
	 * @param sheetNum
	 * @param caseId
	 * @param cellNum
	 * @param result
	 */
	@Deprecated
	public static void writeExcel(String excelPath, int sheetNum, String caseId, int cellNum, String result) {
		InputStream inp = null;
		Workbook workbook = null;
		OutputStream outputStream = null;

		try {
//			inp = ExcelUtils.class.getResourceAsStream(excelPath);
			inp =new FileInputStream(new File(excelPath));
			workbook = WorkbookFactory.create(inp);
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
		    //获得行号
			int rowNum=ApiUtils.getRowNum(caseId);
			//获得对应的行
			Row row=sheet.getRow(rowNum-1);
			//拿到要写数据的列
			Cell cellToWrite = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cellToWrite.setCellType(CellType.STRING);
			cellToWrite.setCellValue(result);			
			/*// 获得最大的行（行的索引，从0开始）
			int lastRowNum = sheet.getLastRowNum();
			
			for (int i = 1; i <= lastRowNum; i++) {
				Row row = sheet.getRow(i);
				Cell cell = row.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// 设置列的类型
				cell.setCellType(CellType.STRING);
				// 获得第i行，第一列的值
				String firstCellvalue = cell.getStringCellValue();
				if (caseId.equals(firstCellvalue)) {
					Cell cellToWrite = row.getCell(cellNum - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cellToWrite.setCellType(CellType.STRING);
					cellToWrite.setCellValue(result);
					break;
				}

			}*/
			outputStream = new FileOutputStream(new File("target/classes/api_test_case_01.xlsx"));
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(workbook!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inp!=null){
				try {
					inp.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	

	}

	@Deprecated // 过期了
	public static Object[][] readAllCaseExcel(String excelPath, int sheetNum) {
		Object[][] datas = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(sheetNum - 1);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			datas = new Object[lastRowNum][];
			for (int i = 1; i <= lastRowNum; i++) {
				// 获得索引对应的行
				Row row = sheet.getRow(i);
				// 创建一个一维数组，保存该行的所有列信息
				Object[] cellArry = new Object[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					cellArry[j] = cellValue;

				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				datas[i - 1] = cellArry;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 1个excel中保存一个类型的测试用例
	 * 
	 * @param excelPath
	 *            excel的路径
	 * @return
	 */
	public static Object[][] readExcel(String excelPath) {
		Object[][] datas = null;
		try {
			InputStream inp = ExcelUtils.class.getResourceAsStream(excelPath);
			// 获得工作薄对象
			Workbook workbook = WorkbookFactory.create(inp);
			// 获得第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			// 通过遍历拿到所有的行->通过遍历拿到每一行的列
			// 获得最后的行号
			// 获得最后的行号(行的索引，从0开始)
			int lastRowNum = sheet.getLastRowNum();

			// 获得第一行
			Row fistrow = sheet.getRow(0);
			// 获得最大的列数(从1开始)
			int lastCellNum = fistrow.getLastCellNum();

			// 创建一个Object类型的二维数组，保存从excel解析出来的行列数据
			datas = new Object[lastRowNum][];
			for (int i = 1; i <= lastRowNum; i++) {
				// 获得索引对应的行
				Row row = sheet.getRow(i);

				// 创建一个一维数组，保存改行的所有列信息
				Object[] cellDatas = new Object[lastCellNum];
				for (int j = 0; j < lastCellNum; j++) {
					// 若存在值为空，则设置该值为空
					Cell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					// 设置列的类型
					cell.setCellType(CellType.STRING);
					// 获得该列的值
					String cellValue = cell.getStringCellValue();
					cellDatas[j] = cellValue;
				}
				// 每遍历一行，要把改行对应的以为数组添加进去
				datas[i - 1] = cellDatas;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	public static void main(String[] args) {

		/*
		 * Object[][] datas = readExcel("/api.xlsx"); for (Object[] objects :
		 * datas) { for (Object object : objects) { System.out.print(object +
		 * "   "); } System.out.println(); }
		 */
		List<ApiInfo> ApiInfoMap = (List<ApiInfo>) readAllCaseExcel("/api_test_case_01.xlsx", 1, ApiInfo.class);// url
		for (ApiInfo apiInfo : ApiInfoMap) {
			System.out.println(apiInfo);
		}

		/*
		 * Object[][] caseDatas = readAllCaseExcel("/api_test_case_01.xlsx",
		 * 2);// 测试用例 System.out.println(caseDatas.length); // 11
		 * System.out.println(urlDatas.length); // 13 for (int i = 0; i <
		 * caseDatas.length; i++) {
		 * 
		 * for (int j = 0; j < urlDatas.length; j++) { if
		 * (caseDatas[i][1].equals(urlDatas[j][0])) { System.out.println(j);
		 * String url = (String) urlDatas[j][3]; System.out.println(url); }
		 * 
		 * }
		 * 
		 * }
		 */

		/*
		 * String jsonStr =
		 * "{\"mobilephone\":\"13517315669\",\"pwd\":\"\",\"regname\":\"柠檬班\"}";
		 * Map<String, String> dataMap=(Map<String,
		 * String>)JSONObject.parse(jsonStr); Set<String>
		 * keySet=dataMap.keySet(); for (String key : keySet) {
		 * System.out.println(key+":"+dataMap.get(key));
		 * 
		 * }
		 */

	}

	

	

}
