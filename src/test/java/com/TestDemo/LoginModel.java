package com.TestDemo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.interactions.CompositeAction;
import org.openqa.selenium.support.FindBy;

/**
 * 模块类，其实本身也是页面类，只不过作为特殊作用，将一些页面元素拎出来，
 * 作为模块进行复用；
 *
 * by.xpath("//div[...]") 这是一种写法
 * @FindBy(xpath=".//*div[...]") 这是另一种写法，有待验证正确性
 *
 */
public class LoginModel extends Page {

	/**
	 * 这种写法是PageFactory的写法，通过标签来定位页面元素,
	 * 和driver.findeElement(By by)作用相同，常用的定法方法都可以用
	 */
	@FindBy(id="username")
	WebElement Wuser;
	
	@FindBy(id="password")
	WebElement Wpass;
	
	@FindBy(xpath="div[text()='验证码不能为空']")
	WebElement Wcode;
	
	@FindBy(id="captcha")
	WebElement Wcap;
	
	@FindBy(id="logbutton")
	WebElement Wsub;
	
	public MainPage doLogin(String str1,String str2) throws InterruptedException{
		//Actions actions=HomePage.doAction();
		//System.out.println(actions.toString());
		Wuser.clear();
		Wpass.clear();
		
		Wuser.sendKeys(str1);
		//actions.sendKeys(Wuser, str1);
		Wpass.sendKeys(str2);
		
		if(Wcap.getAttribute("value").isEmpty()){
			super.isWait=true;
			waitFor();
		}
		Wsub.click();
		//actions.click(Wsub).perform();
		return new MainPage();
	}
}
