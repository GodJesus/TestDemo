package com.TestDemo.Utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * 这个类是驱动专用类，关于创建不同浏览器的驱动对象有一点很关键，就是selenium版本要与浏览器
 * 版本匹配，chrome，ie比较特殊，需要额外下载匹配版本的驱动程序，而firefox已经在selenium中
 * 集成了，所以不用考虑，具体的对照表详见笔记；
 *
 * 还有一点要清楚，webDriver是非线程安全的，当启动多个线程时，之前的线程会挂掉，造成死锁；
 * 想利用线程池来解决这个问题；
 *
 */
public class DriverUnit {
	private WebDriver driver=null;
    //WebDriverBackedSelenium   此类将webDriver又封装了一次
    
    private String driverName=null;
    
	public String getD() {
		return driverName;
	}

	public void setD(String d) {
		this.driverName = d;
	}

	
	public WebDriver ChooseExplore(String driverName){
			setD(driverName);
			//System.out.println(driverName);
			
			if(driverName.toLowerCase().contains("firefox")){
				//注册一个自己的事件监听器
				driver=new EventFiringWebDriver(new FirefoxDriver()).register(new logListener());
				//driver=new FirefoxDriver();
				System.out.println("success "+driverName);
			}
			//IE和Chrome的driver是自己管理，所以想启动这两种browser的时候需要下载驱动;
			if(driverName.toLowerCase().contains("ie")){
				DesiredCapabilities ieCapabilities=DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(
						InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				//作用是ie的保护模式关闭
				System.setProperty("webdriver.ie.driver", "E:\\KFHJ\\IEDriverServer.exe");
				driver=new InternetExplorerDriver();
				System.out.println("success "+driverName);
			}
			if(driverName.toLowerCase().contains("chrome")){
				//DesiredCapabilities chCapabilities=DesiredCapabilities.chrome();
				System.setProperty("webdriver.chrome.driver", "E:\\KFHJ\\chromedriver.exe");
				
				ChromeOptions options=new ChromeOptions();
				//配置参数，禁止data;出现
				options.addArguments("--user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data/Default");
				
				//通过配置参数删除“您使用的是不受支持的命令行标记：--ignore-certificate-errors。稳定性和安全性会有所下降。”提示
				options.addArguments("--start-maximized",
						   "allow-running-insecure-content", "--test-type");
				
				driver=new ChromeDriver(options);
				//driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
				System.out.println("success "+driverName);
			}
//			if(driverName.toLowerCase()=="html"){
//				driver=new HtmlUnitDriver();
//				System.out.println("success "+driverName);
//			}
		return driver;

	}
	
}
