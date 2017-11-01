package com.TestDemo;


import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.TestDemo.Utils.DriverUnit;

/**
 *用例总类
 *
 *所有用例类的父类，其中包含了用例中常用的前置和后续操作，通通放在这一个类中，子类只需要继承就好了；
 *内部还包含了一个DriverManager类，用来初始化WebDriver；
 *不过不理解如果单独声明一个类用来初始化不也可以么，在该类中调用不就好了，子类只负责继承； 
 *
 *问题总结：之前程序一直启动不了，以为是代码结构问题，后来通过debug发现是传入参数可能带有""的问题，所以在判断时还是用contains()比较保险
 *
 *
 *2017-10-16：解决了自动截图问题，了解了关于ITestResult和driver之间的关系（在页面设计模式下，且单线程中）; 解决了第二个页面的元素定位问题，原来是
 *@Findby(xpath=".//*span[...]")这种写法有问题，还是按照之前的写法  xpath="//span[...]";
 *但是目前actions操作还是有误，执行不了;
 *
 */
public class TestCase {

	protected Logger log=Logger.getLogger(this.getClass());
	
	static{
		DOMConfigurator.configure("res/log4j.xml");
	}
	
	@BeforeMethod(alwaysRun=true)
	@Parameters("browser")
	protected void MethodStart(String browser){
		DriverManager.setDriver(browser);
		
	}
	
	@AfterMethod(alwaysRun=true)
	protected void MethodEnd(){
		//DriverManager.driver.close();
		DriverManager.quitDriver();
	}
	
	@BeforeClass(alwaysRun=true)
	protected void TestStart(){
		
		//打印类名
		log.info("\\/\\/\\/\\/\\/\\/---TestCase = "+ this.getClass().getSimpleName()+"---\\/\\/\\/\\/\\/\\/");
	}
	
	@AfterClass(alwaysRun=true)
	protected void TestEnd(){
		
		log.info("\\/\\/\\/\\/\\/\\/---TestCase = "+ this.getClass().getSimpleName()+"---\\/\\/\\/\\/\\/\\/");
		//打印分割符
		log.info("#####################################################");
	}
	
	/**
	 * 
	 *静态内部类
	 */
	public static class DriverManager implements Runnable{
		
		public static WebDriver driver;
	
		private static ThreadLocal<WebDriver> threadlocal=new ThreadLocal<WebDriver>();
		
		public static void setDriver(String dName){
			DriverUnit du=new DriverUnit();
			driver=du.ChooseExplore(dName);
			//试一下线程安全的driver
			
			threadlocal.set(driver);
			System.out.println("driver 名称："+driver.toString());
			
		}
		
		public static WebDriver getDriver(){
			
			return threadlocal.get();
		}
		
		public static void quitDriver(){
			//driver.quit();
			getDriver().quit();
		}
	

		@Override
		public void run() {
			
		}
	
	}
}
