package com.TestDemo.Utils;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 重新写一个通过POI读取Excel,并作为数据源的工具类。功能如下
 * 1.提供Object[][]类型数据；
 * 2.读取测试数据，或者测试case；
 * 3.解析得到后的数据；
 */
public class ExcelUnit {
	private Workbook wb;
	private Sheet sheet;
	private Row row;
	private Cell cell;
	private File file;
	private FileInputStream filein;
//	private FileOutputStream fileout;
	private int rowNum;
//	private CellAddress cAddress;  这个变量用不到了
	private String cellValue=null;
	
	/**
	 * 读取Excel主类
	 * @param path
	 * @param sheetName
	 * @return
	 */
	public Object[][] readExcel_07(String path,String sheetName){
		file=new File(path);
		
		//创建一个list用来存放excel中第一列的值
		List<String> list=new ArrayList<String>();
		
		try {
			if(file.exists()){
				filein=new FileInputStream(file);
				wb=new XSSFWorkbook(filein);
				
				
			}else{
				System.out.println("文档不存在，请检查");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		//为了避免sheetIndex混淆，还是用sheetName更准确	
		sheet=wb.getSheet(sheetName.trim());
		
	    rowNum=sheet.getPhysicalNumberOfRows();
	    
		/**
		 * 
		 * 	关于这个rowNum要多说几句：网上的demo在创建HashMap[rowNum-1][1],但是他的  rowNum=sheet.getPhysicalNumberOfRows(), 这个方法
		 * 返回的值是sheet页中所有行的行数，结合咱们的文档，这个值等于4; 
		 * 	而我们之前用的  rowNum=sheet.getLastRowNum(), 这个方法返回的是sheet页中最后一行的index值，是3(index是从0开始的);
		 * 	所以就导致我们在后面的循环中频繁的出现数组越界或者是只能获取到两行的情况;
		 * 
		 * 	总结一下：目前一共有两种值：sheet页总行数，sheet页最后一行index值;
		 * 创建HashMap[][]时，数组长度==最后一行的index值（这是正常情况，第一行为title，如果遇到特殊情况再说）;
		 * 循环行数时（初始化），循环条件的值<数组长度 或  <=数组长度-1;
		 * 循环行数时（取值），循环条件的值<总行数 或  <=总行数-1;
		 * 	
		 * 	还有row.getLastCellNum()，他和getLastRowNum()是不一样的，他获取的就是该行的总cell数，而不是其index值;
		 * 	所以我们在循环的时候，两个条件分别为：<=getLastRowNum; <getLastCellNum;
		 * 
		 * 10-9补充：在对Excel文档进行更新时，如果想减少行，就要使用删除，而不是backspace；使用后者只是内容不见了，但是对应的Row，Cell对象
		 * 仍然存在表格中，遍历的时候就会造成空指针！！！
		 */
			
		@SuppressWarnings("unchecked")
		HashMap<String, String>[][] map=new HashMap[rowNum-1][1];
			
//		Row rowOne=sheet.getRow(0);
//		Cell cellOne=r
		//初始化
		for(int i=0;i<rowNum-1;i++){
			map[i][0]=new HashMap<String,String>();
		}
		
		//建表
		for(int i=0;i<=rowNum-1;i++){//这边应该要<=
			row=sheet.getRow(i);
			//System.out.println(row.getLastCellNum()); 每行单元格数，而不是index
			for(int j=0;j<row.getLastCellNum();j++){
				//System.out.print("第"+j+"行");
				cell=row.getCell(j);
				cellValue=getCellValue(cell);
				if(i==0){
					System.out.println("列名:"+cellValue);
					list.add(cellValue);
				}else{
					//System.out.println(list.get(j).toString());
					map[i-1][0].put(list.get(j),cellValue);
					//System.out.println("当前集合长度"+map.length);
					
					//System.out.println(map[i-1][0].toString());
				}
			}
		}
			try {
				wb.close();
				filein.close();
				System.out.println("数据读取完毕");
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return map;
	}
	
	/**
	 * 获取Cell的值，如果在循环中不创建cell对象，就需要在外部方法中通过sheet，rowNum（行），cellNum（列）这三个值来定位cell;
	 * @param cell
	 * @return
	 * 
	 * 这边补充一个知识点，List存储是有序的，可以重复的；而HashMap存储是无序的，我们在从HashMap
	 * 中取值都是依靠key值，而不是index，所以我们在遍历最后得到的集合时，数据显示的顺序是无序的；
	 */
	public static String getCellValue(Cell cell){
		String cellValue=CellValueUnit.cellValue(cell);
		return cellValue;
	}
	
	public static void main(String[] args){
		ExcelUnit eu=new ExcelUnit();
		//获取的是二维数组，遍历起来可能有点麻烦，最好能封装一个类
		Object[][] ob=eu.readExcel_07("D:\\test2_bk.xlsx", "Sheet3");
		for(int i=0;i<=ob.length-1;i++){
			System.out.println(ob[i][0]);
		}
	}
}
