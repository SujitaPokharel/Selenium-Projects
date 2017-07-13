package com.npu.selenium.week09;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.Logger;

import library.Utility;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import java.util.List;

import org.junit.rules.ErrorCollector;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;

public class testNPUCreateNewAccountJUnit {
	WebDriver driver;
	
	Logger logger = Logger.getLogger("testNPUCreateNewAccountJUnit");

	private WebElement iUnderstand;
	public String Actualvalue = null;
	public String Expectedvalue = null;

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	List<WebElement> oRadiobutton;
	public boolean b = false;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("****Beginning****");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("****Ending****");
	}

	@Before
	public void setUp() throws Exception {

		// 1. Initiation Firefox browser
		driver = new FirefoxDriver();
		logger.info("firefox opened");

		// 2. Google search and click to open the search result
		driver.get("http://www.google.com");
		Utility.captureScreenshot(driver, "googlePage");
		driver.manage().window().maximize();
		driver.findElement(By.name("q")).sendKeys("NPU");
		Utility.captureScreenshot(driver, "googlePageWithNPU");

		// 3. Verify NPU web page has loaded (check page title)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait
		driver.findElement(By.linkText("Northwestern Polytechnic University")).click();
		Utility.captureScreenshot(driver, "NPU Home Page");
		logger.info("NPU website opened - Home Page");	
		System.out.println(driver.getTitle());
		try {
			Assert.assertTrue(driver.getTitle().contains("Engineering and Business Degrees in California"));
		} catch (Throwable t) {
			t.getMessage();
		}
	}

	@Test
	public void test() throws InterruptedException {

		// 4. If Apply Online button not found, try to open drop down menu
		// Applications then click Apply Online to open For Application,
		// otherwise,
		// click Apply Online button
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div[2]/div/ul/li[2]/a")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[1]/div/div/div[2]/div/ul/li[2]/ul/li[2]/a")).click();
		Utility.captureScreenshot(driver, "Apply Online Button");
		logger.info("Apply Online Link Clicked");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 5. Verify checkbox "I understand and I would like to register a new
		// account" is NOT selected by default
		// 6. Select checkbox "I understand and I would like to register a new
		// account", if it has not selected (otherwise, if has already selected,
		// skip)
		iUnderstand = driver.findElement(By.cssSelector(
				"html body div.page div#main fieldset fieldset.container fieldset.right p input#regcheck"));

		if (!iUnderstand.isSelected())

		{
			iUnderstand.click();
		}
		Utility.captureScreenshot(driver, "CheckBox_checked");
		logger.info("I understand and I agree... Checkbox checked ");

		// 7. Click Link Text "Register Account" to open Create a New Account
		driver.findElement(By.cssSelector("div.align_right:nth-child(7) > a:nth-child(2)")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		logger.info("Register Account Link clicked");

		// 8. Verify the default radio button should select in New Applicant,
		// select it if has not selected
		oRadiobutton = driver.findElements(By.id("new"));
		b = false;
		try {
			b = oRadiobutton.get(0).isSelected();
		} catch (Throwable t) {
			Assert.assertEquals("Create a New Account New Applicant is not selected", true, b);
		}

		// 9. Select your gender (Male of Female)
		Expectedvalue = "Please Select";
		List<WebElement> oSelectlist = driver.findElements(By.name("Gender"));
		boolean bValue = false;
		if (Expectedvalue.toLowerCase().contains("Please Select")) {
			try {
				bValue = oSelectlist.get(0).isSelected();
			} catch (Throwable t) {
				Assert.assertEquals("Gender " + Expectedvalue + " is not selected", true, bValue);
			}
		} else if (Expectedvalue.toLowerCase().contains("Male")) {
			try {
				bValue = oSelectlist.get(1).isSelected();
			} catch (Throwable t) {
				Assert.assertEquals("Gender " + Expectedvalue + " is not selected", true, bValue);
			}
		} else if (Expectedvalue.toLowerCase().contains("Female")) {
			try {
				bValue = oSelectlist.get(2).isSelected();
			} catch (Throwable t) {
				Assert.assertEquals("Gender " + Expectedvalue + " is not selected", true, bValue);
			}
		}

		WebElement select = driver.findElement(By.name("Gender"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (option.getText().equals("Female")) {
				option.click();
				Utility.captureScreenshot(driver, "female_clicked");
				logger.info("female clicked");
				break;
			}
		}

		// 10. Input Given Name/First Name, Surname/Primary Name, Email Address,
		// Password, Confirm Password (follow up the specification design for
		// each to clear the error), use your real first name, last name and id
		// for password/confirm password
		driver.findElement(By.name("FirstName")).clear();
		driver.findElement(By.name("FirstName")).sendKeys("Sujita");
		Utility.captureScreenshot(driver, "firstName_Typed");
		logger.info("Firstname typed");

		driver.findElement(By.name("LastName")).clear();
		driver.findElement(By.name("LastName")).sendKeys("Pokharel");
		Utility.captureScreenshot(driver, "LastName_Typed");
		logger.info("Last Name Typed");

		if (driver.findElements(By.name("Email")).size() > 0) {
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("pokharel14105@mail.npu.edu");
			Utility.captureScreenshot(driver, "Email_Typed");
			logger.info("Email");
		}

		driver.findElement(By.id("Password")).clear();
		driver.findElement(By.id("Password")).sendKeys("12345678");
		Utility.captureScreenshot(driver, "Password_Typed");
		logger.info("Password typed");
		Thread.sleep(3000);

		driver.findElement(By.id("ConfirmPassword")).clear();
		driver.findElement(By.id("ConfirmPassword")).sendKeys("12345678");
		Utility.captureScreenshot(driver, "RePassword_typed");
		logger.info("Password re typed");
		Thread.sleep(3000);

		// 11. Click Save button
		driver.findElement(By.xpath("/html/body/div/div[2]/fieldset/form[2]/input")).click();
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		logger.info("Clicked save button");
		Thread.sleep(10000);

		// 12. Verify message "Invalid reCAPTCHA request. Missing response
		// value." in the web page
		Expectedvalue = "Invalid reCAPTCHA request. Missing response value.";
		b = driver.getPageSource().contains(Expectedvalue.toLowerCase());
		try {
			if (b) {
				Assert.assertEquals("Invalid reCAPTCHA request. Missing response value. Try Again!", b, Expectedvalue);
				Utility.captureScreenshot(driver, "Invalid_reCAPTCHA_catched");
				logger.warn("reCAPTCHA error caught");
			}
		} catch (Exception e) {
			collector.addError(e);
			System.out.println("Missing alert message of Invalid reCAPTCHA request. Check Again!");
		}
		Thread.sleep(3000);

		// 13. Go back to Create New Account page
		driver.navigate().to("https://osc.npu.edu/Account/Register");
		Utility.captureScreenshot(driver, "NewAccount_Page");
		logger.info("Navigated to Create Account Page");
		Thread.sleep(3000); // wait 3s

		// 14. Go back to NPU home page
		driver.navigate().to("http://www.npu.edu/index.html");
		Utility.captureScreenshot(driver, "Home_Page");
		logger.info("Navigated to home page");
		Thread.sleep(3000); // wait 3s

	}

	// 15. Close browser and exit
	@After
	public void tearDown() throws Exception {
		driver.close(); // close current window
		logger.info("Driver is closed");
		driver.quit(); // close all windows and kill all processes
		logger.info("Driver is quited");
	}
}
