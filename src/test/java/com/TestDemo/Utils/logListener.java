package com.TestDemo.Utils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class logListener extends TestListenerAdapter implements WebDriverEventListener,IAnnotationTransformer{
	//更换log对象，使用log4j
	private Logger log=Logger.getLogger(this.getClass());
	
	private By Findby;
	
	static{
		DOMConfigurator.configure("res/log4j.xml");
	}
	
	@Override
	/**
	 * 在页面模式中，ITestResult这个参数没什么用，因为DriverManager.driver是静态成员变量，贯穿整个线程，所以不同的method都拥有自己的driver，所以
	 * 传入ScreenUtils类时，这个参数并且有用；
	 */
	
	public void onTestSuccess(ITestResult tr) {
		ScreenUtils su=new ScreenUtils();
		try {
			log.info("Pass.");
			su.doScreen(tr);
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}
    @Override
    public void onTestFailure(ITestResult tr) {
    	ScreenUtils su=new ScreenUtils();
		try {
			log.info("Fail.");
			su.doScreen(tr);
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
    }
    @Override
    public void onTestSkipped(ITestResult tr) {
    	ScreenUtils su=new ScreenUtils();
		try {
			log.info("Skip.");
			su.doScreen(tr);
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
    }
	
    @Override
    @SuppressWarnings("rawtypes")
    /**
     * 用例失败重跑的监听
     */
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
    	IRetryAnalyzer reAnalyzer=annotation.getRetryAnalyzer();
    	if(reAnalyzer==null){
    		annotation.setRetryAnalyzer(TestRetry.class);
    	}
	}
    
    /**
     * 在onFinish监听方法中添加删除重复报告的功能
     * 代码的逻辑是这样的：
     * 第一，将所有的测试结果在finish的时候传到一个et容器里，然后获取到每个测试结果所
     * 对应的方法名；如果测试的方法>1,就把测试结果Set的内容remove一下；如果通过的方法>0,就把测试结果Set的内容remove一下；
     */
    @Override
    
    public void onFinish(ITestContext testContext) {
    	log.info("test finish");
//    	Iterator<ITestResult> it=testContext.getFailedTests().getAllResults().iterator();
//    	while(it.hasNext()){
//    		ITestResult result=it.next();
//    		ITestNGMethod testMethod=result.getMethod();
//    		if(testContext.getFailedTests().getResults(testMethod).size()>1){
//    			log.info("失败的方法："+testContext.getFailedTests().getResults(testMethod).toString());
//    			it.remove();
//    		}else{
//    			if(testContext.getPassedTests().getResults(testMethod).size()>0){
//    				log.info("通过的方法："+testContext.getPassedTests().getResults(testMethod).toString());
//    				it.remove();
//    			}
//    		}
//    	}
    }
    
	public void beforeAlertAccept(WebDriver driver) {
		
	}

	
	public void afterAlertAccept(WebDriver driver) {
		
	}

	
	public void afterAlertDismiss(WebDriver driver) {
		
	}

	
	public void beforeAlertDismiss(WebDriver driver) {
		
	}

	
	public void beforeNavigateTo(String url, WebDriver driver) {
		log.info("navigate to"+url);
	}

	
	public void afterNavigateTo(String url, WebDriver driver) {
		
	}

	
	public void beforeNavigateBack(WebDriver driver) {
		
	}

	
	public void afterNavigateBack(WebDriver driver) {
		
	}

	
	public void beforeNavigateForward(WebDriver driver) {
		
	}

	
	public void afterNavigateForward(WebDriver driver) {
		
	}

	
	public void beforeNavigateRefresh(WebDriver driver) {
		
	}

	
	public void afterNavigateRefresh(WebDriver driver) {
		
	}

	/**
	 * selenium版本调整到3.6.0后，until方法报错，不懂为啥子
	 */
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		Findby=by;
		(new WebDriverWait(driver, 10, 3000)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d){
				return d.findElement(Findby).isDisplayed();
			}
		});	
	}

	
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		log.info("此元素"+by.toString());
	}

	
	public void beforeClickOn(WebElement element, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		log.info("要点击的元素是否存在:"+element.isDisplayed());
	}

	
	public void afterClickOn(WebElement element, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		String locate=element.toString().split("-> ")[1];
		log.info("web locating at:"+locate.substring(0, locate.length()-1));
	}

	
	public void beforeChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		
	}

	
	public void afterChangeValueOf(WebElement element, WebDriver driver,
			CharSequence[] keysToSend) {
		
	}

	
	public void beforeScript(String script, WebDriver driver) {
		
	}

	
	public void afterScript(String script, WebDriver driver) {
		
	}

	
	public void onException(Throwable throwable, WebDriver driver) {
		if(throwable.getClass().equals(NoSuchElementException.class)){
			log.error("Element Error:not found "+Findby+throwable.getMessage());
		}if(throwable.getClass().equals(IOException.class)){
			log.error("unknown exception"+throwable.getMessage());
		}else{
			log.error("Element Error:"+throwable.getMessage());
		}
	}


	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		
		
	}


	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {
		
		
	}
	

}
