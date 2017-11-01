package com.TestDemo.Utils;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetry implements IRetryAnalyzer {
	public Logger log=Logger.getLogger(this.getClass());
	private static int maxCount; //最大运行次数，失败后重跑maxCount+1次
	private int retryCount;
	static{
		DOMConfigurator.configure("res/log4j.xml");
		maxCount=2;//设为2表示失败后重跑3次
	}
	
	@Override
	public boolean retry(ITestResult result) {
		if(retryCount<maxCount){
			
			String msg="running again for: "+result.getName()+
					", on class:"+this.getClass().getName()+
					", retry:"+retryCount+" times.";
			log.info(msg);
			retryCount++;
			return true;
		}
		return false;
	}

}
