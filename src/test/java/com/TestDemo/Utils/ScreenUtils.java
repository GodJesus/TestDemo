package com.TestDemo.Utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.TestDemo.TestCase.DriverManager;

public class ScreenUtils{//这里到底需不需要继承Page类，有待尝试
	private File dir=null;
	private File localFile=null;
	private static String Dir="E:\\screenshot\\photo2\\";
	private String filePath=null;
	private Log log=LogFactory.getLog(this.getClass());
	
	public void doScreen(ITestResult tr) throws IOException,NullPointerException{
		WebDriver driver=DriverManager.driver;
		dir=new File(Dir);
		if(dir.isDirectory()){
			dir.mkdirs();
		}else{
			log.info("目录已存在");
		}
		
		String format=new SimpleDateFormat("YYYY-MM-dd HH-mm-ss").format(new Date()).toString();
		filePath=Dir+format+".png";
	
		localFile=new File(filePath);
		
		File imageFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(imageFile, localFile);
		if(localFile.exists()){
			log.info("screen finish");
		}else{
			log.info("screen fail");
		}
	}
	
}
