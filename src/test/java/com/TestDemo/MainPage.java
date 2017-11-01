package com.TestDemo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * 页面类，登录后首页类
 * 
 * 之前一直找不到元素，原来是忘了写继承Page类，目前元素可以定位到，但是还需要优化
 *
 */
public class MainPage extends Page {

	public MainPage(){
		
	}
	
	//@FindBy(xpath=".//*a[text()='普惠端']")
	@FindBy(id="puhuiModule")
	WebElement Wtab;
	
	public MainPage openTree1(){
		//System.out.println(Wtab.getAttribute("onclick"));
		System.out.println(Wtab.isDisplayed());
		if(Wtab.isDisplayed()){
			Wtab.click();
		}
		return this;
	}
	
	@FindBy(xpath="//span[contains(text(),'管理报表')]")
	WebElement Wtree;
	
	@FindBy(id="puhuiModule_management_outerLoanOverdue_menu")
	WebElement Wtree_JG;
	
//	public String getTableDate(){
//		
//	}
	
	public MainPage openTree2(){
		System.out.println(Wtree.isDisplayed());
		System.out.println(Wtree.toString());
		if(EleTrue(Wtree)){
			Wtree.click();
		}
		if(EleTrue(Wtree_JG)){
			Wtree_JG.click();
		}
		return this;
	}
	
	@FindBy(xpath="//h5[contains(text(),'监管信息披露')]")
	WebElement Wtitle;
	
//	//@FindBy(xpath=".//*h4[contains(text(),'逾期金额(银监会)')]")   之前按照这种写法，元素找不到
//	@FindBy(xpath="//h4[contains(text(),'逾期金额(银监会)')]")
//	WebElement wtitle_child;
	
}
