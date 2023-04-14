package com.tools.calc.junit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.tools.calc.SeleniumUtil;

public class TestSeleniumJunit {
	SeleniumUtil sj = new SeleniumUtil();
	WebDriver wd = null;
	static boolean isWindows = false;
	static Process process = null;
	
	@BeforeAll
	public static void initializeDriverServer() throws IOException {
		System.out.println("Starting server...");
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

	@Test
	public void findSearchElement() throws IOException {
		System.out.println("Test findSearchElement...");
		assertTrue(sj.findSearchElement(wd).getAttribute("name").equals("q"));
	}
	
	@Test
	public void inputElement() {
		System.out.println("Test inputElement...");
		WebElement we = sj.findSearchElement(wd);
		assertTrue(sj.enterInputAndPressEnter(we, "india"));
	}

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
	}
}
