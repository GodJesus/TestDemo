package com.TestDemo;

import java.util.HashMap;




import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
//import java.util.Iterator;
//import java.util.Map.Entry;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.TestDemo.Utils.SourceProvide;
import com.TestDemo.Utils.logListener;

/**
 *用例类 
 *
 *2017-10-19: 终于   终于，jenkins编译成功，第一次完成持续集成  TestNg+maven+selenium+svn+jenkins+参数化,虽然之前的一次部署一直报空指针异常，而且配置
 *文件的参数读取不了，不过新建了一个项目后都顺利完成了。后续可以把报告和邮件的功能完善一下;
 *目前缺陷：单线程，报告没有优化，单一用例，没有完全摆脱手动
 *
 *2017-10-23：本周任务，jenkins编译后通过reportNg进行报告优化
 *2017-10-24:将reportNg进行反编译，但是jenkins主目录变更失败
 *2017-10-25:jenkins的workspace仍然不能顺利更改，明明说windows上 添加环境变量
 *就可以实现，不知为什么不行；另外html report的报告不会更新的问题解决，原因是配置时将
 *报告的路径写为：test-ouput\html,但是这个文件夹下的文件不会随着项目构建而更新内容，所以
 *路径应该为：target\surefire-reports\html 这个目录下的文件会随着更新；
 *额外收获：设置了admin和添加用户的配置；
 *
 *2017-10-26:并发，多线程，并发，多线程。。。。还有失败重跑，利用cookie实现登录
 *
 *2017-10-27：到底是使用ExcutorSeriver，还是common-pools，多线程真的是搞死了。。。下周继续
 *2017-10-30:虽然多线程还没搞，页面设计模式也还没完善，不过还有更重要的东西去学习，
 *接下来就要去研究自动化接口测试了；
 *
 *
 */
@Listeners(logListener.class)

public class TestLogin extends TestCase{
	protected Logger log=Logger.getLogger(this.getClass());
	
	static{
		DOMConfigurator.configure("res/log4j.xml");
	}
	/**
	 * 第一个用例类，实现功能，先登录，进入首页 ，点击普惠端-》点击监管信息-》展示图表
	 * @param param
	 * @throws InterruptedException 
	 */
	@Test(dataProvider="read2007_test2",dataProviderClass=SourceProvide.class,threadPoolSize=2)
	public void testLogin(HashMap<String, String> param) throws InterruptedException{
//		Iterator<Entry<String, String>> it=param.entrySet().iterator();
//		while(it.hasNext()){//遍历
//			it.next();
//		}
		HomePage home=new HomePage();
		log.info("测试方法开始。。。");
		
		String username=param.get("Username");
		String password=param.get("Password");
		log.info("参数1:"+username);
		log.info("参数2:"+password);

		//home.start();
		MainPage main=home.init().lModel.doLogin(username, password).openTree1().openTree2();
		
		log.info("点击元素是否成功："+main.Wtab.isDisplayed());
		
		log.info("1:"+main.Wtitle.isDisplayed());
		//log.info("2:"+main.wtitle_child.isDisplayed());
		assert(main.Wtitle.isDisplayed());
		//assert(main.wtitle_child.isDisplayed());

		log.info("测试方法结束。。。");
	}
}
