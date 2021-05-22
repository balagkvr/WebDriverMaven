package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtils;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	/*
	 * webdriver - done properties - done logs extent reports db part excel reading
	 * part mail jenkins
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\TestData.xlsx");
	public static WebDriverWait wait;
	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	@BeforeSuite
	public void setUp() {

		log.debug("test setup method started!!!");

		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") +"\\src\\test\\resources\\properties\\Config.properties" );
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("config file loaded!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR file loaded!!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				log.debug("chrome browser launched!!!");
			} else if (config.getProperty("browser").equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			} else {
				System.out.println("invalid browser asked to be invoked");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("navigated to the " + config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicitwait")),
					TimeUnit.SECONDS);

			wait = new WebDriverWait(driver, Integer.parseInt(config.getProperty("explicitwait")));

		}

	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public void click(String locator) {
		driver.findElement(By.xpath(OR.getProperty(locator))).click();
		test.log(LogStatus.INFO, "clicing on : " + locator);
	}

	public void type(String locator, String value) {
		driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		test.log(LogStatus.INFO, "typing in : " + locator + " and entered value as " + value);
	}

	public static void verifyEquals(String Expected, String Actual) throws IOException {
		try {
			Assert.assertEquals(Actual, Expected);
		} catch (Throwable t) {
			TestUtils.captureScreenshot();

			// Extent Reports
			TestBase.test.log(LogStatus.FAIL, "Failed with exception : " + t.getMessage());
			TestBase.test.log(LogStatus.FAIL, TestBase.test.addScreenCapture(TestUtils.screenshotname));

		}

	}

	public void select(String locator, String value) {
		Select s = new Select(driver.findElement(By.xpath(OR.getProperty(locator))));
		s.selectByVisibleText(value);
		test.log(LogStatus.INFO, "selecting from dropdpwn : " + locator + " and selected value as " + value);
	}

	@AfterSuite
	public void tearDown() {

		log.debug("test teardown method executed!!!");
		if (driver != null) {
			driver.quit();
		}

		log.debug("execution completed and browsers closed!!!");
	}

}
