package com.tools.calc;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class SeleniumUtil {
	
//	public SeleniumUtil() {
//		System.setProperty("webdriver.gecko.driver", "D:\\Work\\Sel4Libs\\Drivers\\geckodriver-v0.31.0-win64\\geckodriver.exe");
//		System.setProperty("webdriver.gecko.driver", "/tmp/linux_x64_geckodriver");
//	}
	
	public WebDriver startDriver() throws MalformedURLException {
		FirefoxOptions fo = new FirefoxOptions();
		fo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
		fo.setAcceptInsecureCerts(true);
		fo.setHeadless(true);
//		fo.setLogLevel(FirefoxDriverLogLevel.TRACE);
		return new FirefoxDriver(fo);
	}
	
	public WebDriver startDriver(String ip, String port) throws MalformedURLException {
		FirefoxOptions fo = new FirefoxOptions();
		fo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
		fo.setAcceptInsecureCerts(true);
		Capabilities c = new DesiredCapabilities(fo.asMap());
		return new RemoteWebDriver(new URL("http://"+ip+":"+port), c);
	}
	
	public boolean launchFirefox(WebDriver wd, String url) {
		wd.get(url);
		return true;
	}
	
	public WebElement findSearchElement(WebDriver wd) {
		return wd.findElement(By.name("q"));
	}
	
	public boolean enterInputAndPressEnter(WebElement we, String input) {
		if(input == null)
			return false;
		we.sendKeys(input);
		we.sendKeys(Keys.ENTER);
		return true;
	}
	
	public static void main(String[] a) throws MalformedURLException {
		SeleniumUtil su = new SeleniumUtil();
		WebDriver wd = su.startDriver("127.0.0.1", "4000");
		su.launchFirefox(wd, "https://www.google.com");
		WebElement we = su.findSearchElement(wd);
		su.enterInputAndPressEnter(we, "India");
		wd.close();
	}
	

}
