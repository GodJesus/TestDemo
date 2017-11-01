package com.TestDemo;

//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.TestDemo.TestCase.DriverManager;

/**
 *页面类，登录前首页类
 *
 */
public class HomePage extends Page{
	
	public static String url="http://10.36.40.213:9292/smartbi/admin/module/index.html";
	//public static WebDriver driver=DriverManager.driver;
	
	/**
	 * LoginModel是可重用的模块，作为成员变量在此页面使用，
	 * 这种用法是组合的写法（composite），不可随意继承
	 */
	public LoginModel lModel=new LoginModel();
	
//	public HomePage(){
//		
//	}
	/**
	 * 这个初始化方法一般在首页使用，通过返回值来判断页面的去向：
	 * @return this, 留在本页面，并且没有刷新类操作；
	 * @return new HomePage, 留在本页面，有刷新的操作；
	 * @return new OtherPage, 跳转都其他页面；
	 * 
	 * 这样写的好处：根据返回值可以明确页面跳转的结果；
	 * 之前我们采用DriverManager.driver赋值给了这个类的成员变量driver的写法，这种在单线程中还可以，一旦到了多线程
	 * 或者用例重跑时就会报错了，因为类加载时会赋予成员变量值，这就导致在新创建driver后，这个类的driver没有更新，所以
	 * 会报session Id is null的错误；但是DriverManager.driver就不同了，每次加载这个静态类时，这个值都会更新，
	 * 所以在非单例的模式下引用一定要注意
	 * 
	 */
	public HomePage init(){
		//driver.get(url);
//		DriverManager.driver.manage().deleteAllCookies();
//		DriverManager.driver.get(url);
		DriverManager.getDriver().manage().deleteAllCookies();
		DriverManager.getDriver().get(url);
		return this;
	}
	public void start(){
		DriverManager.driver.get(url);
	}
	
	public static Actions doAction(){//先不加参数，应为在pageObject设计模式里，代码中一直没有出现driver，都只是当做参数来传递
		Actions action=new Actions(DriverManager.driver); 
		System.out.println(action.toString());
		return action;
	}
}
