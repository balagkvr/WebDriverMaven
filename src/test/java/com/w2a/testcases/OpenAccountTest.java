package com.w2a.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtils;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtils.class, dataProvider = "dp")
	public void openAccountTest(Hashtable<String, String> data) throws InterruptedException {
//		customer=//select[@id='userSelect']
//				currency=//select[@id='currency']
//				btnprocess=//button[@type='submit']	

		System.out.println("istestRunnable for : " + data.get("customer") + TestUtils.isTestRunnable("OpenAccountTest", excel));

		if (!(TestUtils.isTestRunnable("OpenAccountTest", excel))) {

			throw new SkipException("Skipping the openAccountTest as the Run mode is NO");
		}

		click("btnOpenAccount");
		select("customer", data.get("customer"));
		select("currency", data.get("currency"));
		click("btnprocess");

		Thread.sleep(3000);

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Assert.assertTrue(alert.getText().contains(data.get("alertText")));

		alert.accept();
	}
}
