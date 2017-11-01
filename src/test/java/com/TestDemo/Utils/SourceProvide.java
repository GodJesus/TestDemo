package com.TestDemo.Utils;

import org.testng.annotations.DataProvider;

public class SourceProvide {
	
	@DataProvider(name="read2007_test2",parallel=true)//parallel参数表示可以并发执行
	public static Object[][] dp2(){
		String path="D:\\test2_bk.xlsx";
		String indexName="Sheet3";
		ExcelUnit exUnit=new ExcelUnit();
		Object[][] data=exUnit.readExcel_07(path, indexName);
		return data;
	}
}
