package com.npu.cs522_selenium.finalProject;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import library.ExcelDataConfig;
import library.Utility;

public class AmazonPurchaseTestNG {
	
	Logger logger = Logger.getLogger("AmazonPurchaseTestNG");
	
	private static final String LinkText = "http://www.amazon.com";
	public String Actualvalue = null;
	public String Expectedvalue = null;
	public WebDriver driver;
	public WebDriverWait wait;

	@BeforeTest
	public void beforeTest() {
		System.out.println("*** Before Test ***");
	}

	@BeforeMethod
	public void initiate() {
		System.out.println("*** Before Method ***");
		// initiate Firefox browser here
		// launch browser (Firefox)
		driver = new FirefoxDriver();
		Utility.captureScreenshot(driver, "firefoxLaunched");
		logger.info("firefox opened");
		wait = new WebDriverWait(driver, 70);
	}

	@DataProvider
	public Object[][] studentdata() throws IOException, InvalidFormatException {

		ExcelDataConfig config = new ExcelDataConfig("C:\\Users\\Suju\\workspace\\finalProject\\userExcelAndLogFile\\AmazonData.xlsx");
		logger.info("Reading data from excel");
		int rows = config.getRowCount(0);
		System.out.println("rows no = " + rows);

		Object[][] data = new Object[rows][9];

		for (int i = 0; i < rows; i++) {
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);
			data[i][2] = config.getData(0, i, 2);
			data[i][3] = config.getData(0, i, 3);
			data[i][4] = config.getData(0, i, 4);
			data[i][5] = config.getData(0, i, 5);
			data[i][6] = config.getData(0, i, 6);
			data[i][7] = config.getData(0, i, 7);
			data[i][8] = config.getData(0, i, 8);

		}
		logger.info("returning the data that is read from the excel");
		return data;
	}

	@Test(dataProvider = "studentdata")
	public void AmazonLoginTestNG(String username, String password, String fullname, String address1, String address2, String city, String state,
			String zip, String phoneno) throws InterruptedException {

		// Step 1: open amazon.com
		openAmazon(LinkText);
		
		// Step 2: input Username and Password
		InputUsernamePassword(username, password);

		// Step 3: click Log On button
		clickLogonbutton();

		// Step 4: click Student Login to open Student Login page
		enterAddress(fullname, address1, address2, city, state, zip, phoneno);
		
		
		// Step 5: check Student Login page title
		enterPaymentMethod();

		

	}

	private void enterPaymentMethod() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='ccName']")));
		driver.findElement(By.xpath(".//*[@id='ccName']")).sendKeys("c.Mori"); // input .//*[@id='ccName']
		Utility.captureScreenshot(driver, "cardHolderNameEntered");
		logger.info("name in credit card entered");
		
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='addCreditCardNumber']")));
		driver.findElement(By.xpath(".//*[@id='addCreditCardNumber']")).sendKeys("1234567890"); // input
		Utility.captureScreenshot(driver, "AccountNumEntered");
		logger.info("credit card number entered");
		
		// click Add your Card
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='ccAddCard']")));
		driver.findElement(By.xpath(".//*[@id='ccAddCard']")).click(); //id="ccAddCard" 
		logger.info("Add card entered");
		
		// click Continue
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("continue-bottom-disabled")));
		driver.findElement(By.xpath(".//*[@id='continue-bottom-disabled']")).click(); //.//*[@id='continue-bottom-disabled'] 
		Utility.captureScreenshot(driver, "continueCLicked");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS); // wait
		logger.info("continue button clicked");
	}

	private void enterAddress(String fullname, String address1, String address2, String city, String state,
			String zip, String phoneno) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressFullName")));
		// inserting fullname
		driver.findElement(By.id("enterAddressFullName")).sendKeys(fullname);
		Utility.captureScreenshot(driver, "fullNameEntered");
		logger.info("full name of the receiver inserted reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressAddressLine1")));
		// inserting Address line1
		driver.findElement(By.id("enterAddressAddressLine1")).sendKeys(address1);
		Utility.captureScreenshot(driver, "Address1Entered");
		logger.info("address1 of the receiver entered reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressAddressLine2")));
		// inserting Address line1
		driver.findElement(By.id("enterAddressAddressLine2")).sendKeys(address2);
		Utility.captureScreenshot(driver, "Address2Entered");
		logger.info("address2 entered reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressCity")));
		// inserting city
		driver.findElement(By.id("enterAddressCity")).sendKeys(city);
		Utility.captureScreenshot(driver, "cityEntered");
		logger.info("City entered reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressStateOrRegion")));
		// inserting state
		driver.findElement(By.id("enterAddressStateOrRegion")).sendKeys(state);
		Utility.captureScreenshot(driver, "stateEntered");
		logger.info("State entered reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressPostalCode")));
		// inserting zip
		driver.findElement(By.id("enterAddressPostalCode")).sendKeys(zip);
		Utility.captureScreenshot(driver, "zipEntered");
		logger.info("zip code entered reading from the excel file");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("enterAddressPhoneNumber")));
		// inserting phone number
		driver.findElement(By.id("enterAddressPhoneNumber")).sendKeys(phoneno);
		Utility.captureScreenshot(driver, "phoneNumberEntered");
		logger.info("phone number entered reading from the excel file");

		// click Continue
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='newShippingAddressFormFromIdentity']/div[1]/div/form/div[6]/span/span/input")));
		driver.findElement(
				By.xpath(".//*[@id='newShippingAddressFormFromIdentity']/div[1]/div/form/div[6]/span/span/input"))
				.click();
		Utility.captureScreenshot(driver, "clickedContinue");
		logger.info("clicked continue");
		
		// click Continue
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='shippingOptionFormId']/div[3]/div/div/span[1]/span/input")));
		driver.findElement(
				By.xpath(".//*[@id='shippingOptionFormId']/div[3]/div/div/span[1]/span/input"))
				.click();
		logger.info("selected standard delivery and continue button clicked");
		
	}

	private void clickLogonbutton() {

		// click Log-on
		driver.findElement(By.xpath(".//*[@id='signInSubmit']")).click();
		Utility.captureScreenshot(driver, "loginButtonClicked");
		logger.info("login button clicked to continue delivery");

		System.out.println("Log-In test Completed.");
	}

	private void InputUsernamePassword(String username, String password) {

		// inserting username
		driver.findElement(By.id("ap_email")).sendKeys(username);
		Utility.captureScreenshot(driver, "emailEntered");
		logger.info("Email entered to login reading from the excel file");

		// inserting password
		driver.findElement(By.id("ap_password")).sendKeys(password);
		Utility.captureScreenshot(driver, "passwordEntered");
		logger.info("password entered to login reading from the excel file");
	}

	private void openAmazon(String linktext2) throws InterruptedException {
		// go to http://www.amazon.com
		driver.get(linktext2);
		logger.info("www.amazon.com opened");
		Utility.captureScreenshot(driver, "amazonHomePageOpened");
		// maximize the browser window
		driver.manage().window().maximize();

		// wait until amazon home page loaded
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait

		// Step 2.Input iWatch and select iWatch Apple in Electronics in the
		// drop down menu, open it
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iWatch"); // input
		Utility.captureScreenshot(driver, "iWatchKeywordTyped");
		logger.info("iWatch keyword inputed to search");
		Actions action = new Actions(driver);
		for (int i = 1; i <= 2; i++) {
			action.sendKeys(Keys.DOWN).build().perform(); // move Down key
			Utility.captureScreenshot(driver, "searchInElectronics");
			logger.info("iWatch is asked to select from Electronics");
			Thread.sleep(3); // sleep for 3 seconds
		}
		action.sendKeys(Keys.ENTER).build().perform(); // hit Enter key to open
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait

		// Step 3. Click on the first iwatch text link appreas on the top of the
		// page and wait until page appears
		driver.findElement(By.xpath(".//*[@id='result_0']/div/div[3]/div[1]/a/h2")).click();
		Utility.captureScreenshot(driver, "firstIWatchLinkClicked");
		logger.info("Found the first iWatch link and clicked");
		// driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); //
		// wait

		//wait = new WebDriverWait(driver, 60);// 1 minute
		

		// Step 4. Add the iwatch to cart and wait until added
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
		driver.findElement(By.id("add-to-cart-button")).click(); // .//*[@id='add-to-cart-button']
		Utility.captureScreenshot(driver, "addedToCart");
		logger.info("iWatch added to the cart");
		System.out.println(driver.getTitle());
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS); // wait

		// Step 5. click on proceed to check out button
		driver.findElement(By.id("hlb-ptc-btn-native-bottom")).click();// .//*[@id='hlb-ptc-btn-native']
		Utility.captureScreenshot(driver, "proceededToCheckout");
		logger.info("Proceeded to check out");
		System.out.println(driver.getTitle());
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait

	}

	@AfterMethod
	public void Teardown() throws IOException, InvalidFormatException {
		System.out.println("*** After Method ***");
		// Close all windows and open sessions and eixt here
		driver.close(); // close current window
		driver.quit(); // close all windows and kill all processes
	}

	@AfterTest
	public void afterTest() {
		System.out.println("*** After Test ***");
	}

}
