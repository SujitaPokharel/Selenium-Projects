package com.npu.selenium.week10;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import library.ExcelDataConfig;

public class RWDataExcelTestNGNpuStdLogin {
	private static final String LinkText = "http://www.google.com";
	private static final String SearchText = "NPU";
	public String Actualvalue = null;
	public String Expectedvalue = null;
	public WebDriver driver;

	@DataProvider
	public Object[][] studentdata() throws IOException, InvalidFormatException {

		ExcelDataConfig config = new ExcelDataConfig(
				"C:\\Users\\Suju\\workspace\\HW8\\userExcelAndLogFile\\Homework8Data.xlsx");
		int rows = config.getRowCount(0);
		System.out.println("rows count" + rows);
		
		Object[][] data = new Object[rows][2];

		for (int i = 0; i < rows; i++) {
			data[i][0] = config.getData(0, i, 0);
			data[i][1] = config.getData(0, i, 1);
			writeDataToExcelSheet(i); // Write data to xls sheet
		}
		return data;
	}

	public void writeDataToExcelSheet(int row) throws IOException, InvalidFormatException {

		try {
			FileInputStream fis = new FileInputStream(
					new File("C:\\Users\\Suju\\workspace\\HW8\\userExcelAndLogFile\\Homework8Data.xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheet("Sheet1");
			sheet.getRow(row).createCell(2).setCellValue("Fail");
			FileOutputStream fos = new FileOutputStream(
					new File("C:\\Users\\Suju\\workspace\\HW8\\userExcelAndLogFile\\Homework8Data.xlsx"));

			workbook.write(fos);
			System.out.println("Excel file has been written");

			fos.close();
			workbook.close();

		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	@Test(dataProvider = "studentdata")
	public void NPUStudentLoginTestNG(String username, String password) {

		// Step 1: open NPU from google search
		openNPUFromGoogleSearch(SearchText, LinkText);

		// Step 2: click Student Login to open Student Login page
		openStudentLogin();

		// Step 3: check Student Login page title
		checkOpenStudentLogin("Log On");

		// Step 4: input Username and Password
		InputUsernamePassword(username, password);

		// Step 5: click Log On button
		clickLogonbutton();

		// Step 5: Logout
		// moved to @AfterMethod
	}

	private void clickLogonbutton() {
		
		// click Log-on
		driver.findElement(By.xpath("/html/body/div/div[2]/fieldset/form/p[2]/input")).click();

		// wait until NPU home page loaded
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait
		// verify
		Expectedvalue = "Login was unsuccessful. Please correct the errors and try again.";
		Actualvalue = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/span")).getText();

		if (Expectedvalue.equals(Actualvalue)) {
			System.out.println("Login Fail. Please correct the errors and try again.");
			// writeDataToExcelSheet(); //function call
		}

		System.out.println("Log-In test Completed.");
	}

	private void InputUsernamePassword(String username, String password) {
		
		// inserting username
		driver.findElement(By.id("UserName")).sendKeys(username);

		// inserting password

		driver.findElement(By.id("Password")).sendKeys(password);
	}

	private void checkOpenStudentLogin(String string) {
		
		// wait and verify Log On page is loaded (check page title)
		System.out.println(driver.getTitle()); // print out the current page
												// title
		try {
			//check page title is "Log On"
			Assert.assertTrue(driver.getTitle().contains("Log On")); 
		} catch (Throwable t) {
			t.getMessage();
		}
		// wait until NPU home page loaded
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private void openStudentLogin() {
		
		// click login button link
		driver.findElement(By.xpath("/html/body/div[1]/div[3]/div/div/div[1]/div/div[4]/form[1]/fieldset/p[1]/a"))
				.click();

		// wait until NPU home page loaded
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private void openNPUFromGoogleSearch(String searchtext2, String linktext2) {
		// go to http://google.com
		driver.get(linktext2);
		// maximize the browser window
		driver.manage().window().maximize();

		// type NPU in the search text Finding the text input element by itsname
		driver.findElement(By.name("q")).sendKeys(searchtext2);
		// wait until the search result appears
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait
		driver.findElement(By.linkText("Northwestern Polytechnic University")).click();

		// wait until NPU home page loaded
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); // wait
	}

	@BeforeMethod
	public void initiate() {
		System.out.println("*** Before Method ***");
		// initiate Firefox browser here
		// launch browser (Firefox)
		driver = new FirefoxDriver();
	}

	@AfterMethod
	public void Teardown() throws IOException, InvalidFormatException {
		System.out.println("*** After Method ***");
		// Close all windows and open sessions and eixt here
		driver.close(); // close current window
		driver.quit(); // close all windows and kill all processes
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("*** Before Test ***");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("*** After Test ***");
	}

}
