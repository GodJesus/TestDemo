package com.TestDemo;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.TestDemo.TestCase.DriverManager;


public class Page {
	protected boolean isWait=false;

	public Page(){
		//每个页面都通过继承这个页面来初始化
		//PageFactory.initElements(DriverManager.driver, this);
		PageFactory.initElements(DriverManager.getDriver(), this);
	}
	//添加一个等待方法，不知道能否成功
	public boolean waitFor(){
		if(isWait=true){
			//DriverManager.driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
			DriverManager.getDriver().manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
			isWait=false;
		}
		return isWait;
	}
	
	public static boolean EleTrue(WebElement we){
		boolean statue=true;
		System.out.println(we.toString());
		if(we.isDisplayed()){
			statue=true;
			System.out.println(statue);
		}else{
			statue=false;
			System.out.println(statue);
		}
		return statue;
	}
}
