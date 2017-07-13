package library;
import java.io.File;
import java.io.IOException;

import library.Utility;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utility {
	
public static String captureScreenshot(WebDriver driver, String screenshotname) {
		
		try {
				TakesScreenshot ts = (TakesScreenshot)driver;
		
				File source = ts.getScreenshotAs(OutputType.FILE);
				
				String dest = "./screenshotFinal/" + screenshotname + ".png";
				
				File destination = new File(dest);
		
				//FileUtils.copyFile(source, new File("./screenshot/" + screenshotname + ".png"));
				
				FileUtils.copyFile(source, destination);
			
				System.out.println("screenshot taken");
				
				return dest;
				
		} catch (Exception e) {
			System.out.println("Exception while taking screenshot." + e.getMessage());
			return e.getMessage();
		}
	}


}
