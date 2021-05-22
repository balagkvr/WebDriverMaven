package com.w2a.testcases;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase {

	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {

		/*
		 * Date d = new Date(); System.setProperty("current.date",
		 * d.toString().replace(" ", "_").replace(":", "_"));
		 * 
		 * PropertyConfigurator.configure(System.getProperty("user.dir")+
		 * "\\src\\test\\resources\\properties\\log4j.properties");
		 */
			
		/*
		 * verifyEquals("abc", "xyz"); Thread.sleep(3000);
		 */
		log.debug("inside login test");
		click("btnbml");
		Thread.sleep(3000);

		Assert.assertTrue(isElementPresent(By.xpath(OR.getProperty("btnaddcustomer"))),"Login not successful");

		log.debug("login successful!!!");
		
		//Assert.fail("Login not successful");
		}

}
