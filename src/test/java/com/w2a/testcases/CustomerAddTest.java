package com.w2a.testcases;

import java.util.Hashtable;
import java.util.Iterator;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtils;

public class CustomerAddTest extends TestBase {

	@Test(dataProviderClass = TestUtils.class,dataProvider = "dp")
	public void addCustomerTest(Hashtable<String, String> data) throws InterruptedException {

		
		if(!data.get("runmode").equals("Y"))
		{
			throw new SkipException("skipping the test case as the runmode for data set is NO");
		}
		
		click("btnaddcustomer");
		type("firstname", data.get("firstname"));
		type("lastname", data.get("lastname"));
		type("postcode", data.get("postcode"));
		click("addcustbtn");		
		
		/*
		 * driver.findElement(By.cssSelector(OR.getProperty("btnaddcustomer"))).click();
		 * driver.findElement(By.xpath(OR.getProperty("firstname"))).sendKeys(firstName)
		 * ;
		 * driver.findElement(By.xpath(OR.getProperty("lastname"))).sendKeys(lastName);
		 * driver.findElement(By.xpath(OR.getProperty("postcode"))).sendKeys(postcode);
		 * driver.findElement(By.xpath(OR.getProperty("addcustbtn"))).click();
		 */
		
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		
		alert.accept();
	}
	
	/*
	 * @DataProvider public Object[][] getData() { String sheetName =
	 * "AddCustomerTest"; int rows = excel.getRowCount(sheetName); int cols =
	 * excel.getColumnCount(sheetName);
	 * 
	 * Object[][] data = new Object[rows - 1][cols];
	 * 
	 * for(int rowNum = 2;rowNum <=rows; rowNum++) { for (int colNum = 0; colNum <
	 * cols; colNum++) { data[rowNum-2][colNum] = excel.getCellData(sheetName,
	 * colNum, rowNum); } }
	 * 
	 * return data; }
	 */	
}
