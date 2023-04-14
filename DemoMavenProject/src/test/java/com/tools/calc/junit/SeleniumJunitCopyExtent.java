package com.tools.calc.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.ExtentSparkReporterConfig;
import com.tools.calc.SeleniumUtil;

public class SeleniumJunitCopyExtent {
	SeleniumUtil sj = new SeleniumUtil();
	WebDriver wd = null;
	static boolean isWindows = false;
	static Process process = null;
	
	static ExtentTest test;
	static ExtentReports report;
	
	
	@BeforeAll
	public static void initializeDriverServer() throws IOException {
		System.out.println("Starting server...");
		report = new ExtentReports();
		new File("./target/site/images").mkdirs();
		ExtentSparkReporter spark = new ExtentSparkReporter("./target/site/Spark.html");
		report.attachReporter(spark);
		ExtentSparkReporterConfig rc = spark.config();
		rc.enableOfflineMode(true);
		test = report.createTest("ExtentDemo");
		
		isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		if(isWindows) {
//			process  = Runtime.getRuntime().exec("cmd.exe /c " + DriverConstant.WIN_DRIVER +" --port 4000");
			System.setProperty("webdriver.gecko.driver", "D:\\onetest\\geckodriver.exe");
		} else {
//			process  = Runtime.getRuntime().exec("/bin/sh -c " + DriverConstant.LINUX_DRIVER +" --port 4000");
			System.setProperty("webdriver.gecko.driver", "/tmp/linux_x64_geckodriver");
		}
	}

	@BeforeEach
	public void initializeDriver() throws MalformedURLException {
		System.out.println("Initializing session...");
		if(isWindows) {
			wd = sj.startDriver();
		} else {
			System.out.println("Starting selenium driver: ankur..");
			wd = sj.startDriver();
		}
		sj.launchFirefox(wd, "https://www.google.com");
	}

//	@Test
	public void findSearchElement() throws IOException {
		assertTrue(sj.findSearchElement(wd).getAttribute("name").equals("q"));
		test.pass("Pass");
		test.log(Status.PASS, capture(wd));
	}
	
	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String time = System.currentTimeMillis()+".png";
		File Dest = new File("./target/site/images/" + time);
//		String errflpath = Dest.getAbsolutePath();
		Files.copy(new FileInputStream(scrFile), Dest.toPath());
		return "./images/"+time;
	}

//	@Test
	public void inputElement() {
		WebElement we = sj.findSearchElement(wd);
		assertTrue(sj.enterInputAndPressEnter(we, "india"));
	}
//
	@AfterEach
	public void destroy() {
		System.out.println("Destroying session...");
		if (wd != null) {
			wd.close();
		}
	}
	
	@AfterAll
	public static void stopDriverServer() {
		System.out.println("Stopping server...");
		if(process != null)
			process.destroy();
		report.flush();
	}
}
