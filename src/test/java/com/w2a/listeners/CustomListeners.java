package com.w2a.listeners;

import java.io.IOException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.LogStatus;
import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtils;

public class CustomListeners implements ITestListener,ISuiteListener {

	public void onTestStart(ITestResult result) {
		
		TestBase.test = TestBase.rep.startTest(result.getName().toUpperCase());
		
	}

	public void onTestSuccess(ITestResult result) {
		
		TestBase.test.log(LogStatus.PASS, result.getName().toUpperCase() + "PASS");
		TestBase.rep.endTest(TestBase.test);
		TestBase.rep.flush();
	}

	public void onTestFailure(ITestResult result) {
	try {
		TestUtils.captureScreenshot();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	TestBase.test.log(LogStatus.FAIL, result.getName().toUpperCase() + "Failed with exception : " + result.getThrowable());
	TestBase.test.log(LogStatus.FAIL, TestBase.test.addScreenCapture(TestUtils.screenshotname));
	TestBase.rep.endTest(TestBase.test);
	TestBase.rep.flush();
	}

	public void onTestSkipped(ITestResult result) {
	
		TestBase.test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped the test as the Run mode is NO");
		TestBase.rep.endTest(TestBase.test);
		TestBase.rep.flush();
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		
	}

	public void onStart(ITestContext context) {
	
	}

	public void onFinish(ITestContext context) {
		
	}

public void onStart(ISuite suite) {
		
	}

	public void onFinish(ISuite suite) {
		
	}
}