package com.TestDemo.Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public class CellValueUnit {
	
	public static String cellValue(Cell cell){
		String value="";
		
		switch(cell.getCellTypeEnum()){
			case STRING:
				value=String.valueOf(cell.getRichStringCellValue());
				break;
			case NUMERIC:
				if(DateUtil.isCellDateFormatted(cell)){
					value=cell.getDateCellValue().toString();
				}else{
					//cell.getNumericCellValue返回的是double型，有时候我们需要整型，所以类型转换一下
					value=String.valueOf((int)cell.getNumericCellValue());
				}
				break;
			case FORMULA:
				value=String.valueOf(cell.getCellFormula());
				break;
			case BOOLEAN:
				value=String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
		}
		
		return value;
	}
}
