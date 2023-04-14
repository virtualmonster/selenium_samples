package com.test.sel;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.springframework.core.io.ClassPathResource;

import net.bytebuddy.utility.RandomString;

public class EmeraldSeleniumTest {

	private static WebDriver driver;
	private static long sleepTime = Integer.parseInt(System.getenv("wait") != null ? System.getenv("wait") : "1")
			* 1000;
	private static FirefoxOptions firefoxOptions() {
		FirefoxOptions fo = new FirefoxOptions();
		fo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
		fo.setAcceptInsecureCerts(true);
		String isHeadless = TestConfig.getPropertyAsString("ff.headless");
		fo.setHeadless(isHeadless != null ? Boolean.parseBoolean(isHeadless) : false);
		return fo;
	}

	@BeforeAll
	public static void openBrowser() {
		System.setProperty("webdriver.gecko.driver", TestConfig.getPropertyAsString("gecko.driver"));
		driver = new FirefoxDriver(firefoxOptions());
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get(TestConfig.getPropertyAsString("url"));
	}

	private void sleep() {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private WebElement findElementWithWait(String byLocator) {
		return findElementWithWait(By.xpath(byLocator));
	}

	private WebElement findElementWithWait(By byLocator) {
		sleep();
		// Use fluent wait
		FluentWait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofMillis(20))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		// This is the condition to wait on presence of elements.
		WebElement elements = wait.until(ExpectedConditions.presenceOfElementLocated(byLocator));
		return elements;
	}

	@Test
	public void userShouldOrderAItem() {
		shouldSignInUser();
		assertThatNoException();

//		shouldRegisterAUser();
//		assertThatNoException();
		shouldSelectABedByClicks();
		assertThatNoException();

		shouldAddToCart();
		assertThatNoException();

		shouldCheckout();
		assertThatNoException();

		shouldCreateAddress();
		assertThatNoException();

		shouldSelectDeliveryType();
		assertThatNoException();

		shouldFillPaymentDetails();

		shouldReviewAndPlaceOrder();
		assertThatNoException();
	}

	private void shouldFillPaymentDetails() {
		WebElement visaPay = findElementWithWait("//*/p[contains(.,'VISA Credit Card')]");
		Assertions.assertNotNull(visaPay);
		visaPay.click();
		visaPay.click();

		WebElement cc = findElementWithWait("//*/input[@name='account'][@type='tel']");
		Assertions.assertNotNull(cc);
		cc.click();
		cc.sendKeys("4111111111111111");

		WebElement month = findElementWithWait("//*/select[@name='expire_month']");
		Assertions.assertNotNull(month);
		month.click();
		Select mSelect = new Select(month);
		mSelect.selectByVisibleText("08");

		WebElement year = findElementWithWait("//*/select[@name='expire_year']");
		Assertions.assertNotNull(year);
		year.click();
		Select ySelect = new Select(year);
		ySelect.selectByVisibleText("2028");

		WebElement cvv = findElementWithWait("//*/input[@name='cc_cvc']");
		Assertions.assertNotNull(cvv);
		cvv.click();
		cvv.sendKeys("000");

		WebElement useAdd = findElementWithWait("//*/p[contains(.,'Use This Address')]");
		Assertions.assertNotNull(useAdd);
		useAdd.click();

	}

	private void shouldReviewAndPlaceOrder() {
		WebElement reviewAdd = findElementWithWait("//*/button[contains(.,'Review Order')]");
		Assertions.assertNotNull(reviewAdd);
		reviewAdd.click();

		WebElement placeOrder = findElementWithWait("//*/button[contains(.,'Place Order')]");
		Assertions.assertNotNull(placeOrder);
		placeOrder.click();
	}

	private void shouldSignInUser() {
		// Click 'SignIn/Register'
		WebElement signIn = findElementWithWait(("//*/p[contains(.,'Sign In / Register')]"));
		Assertions.assertNotNull(signIn);
		assertEquals("Sign In / Register", signIn.getText());
		signIn.click();

		// Click on email textfield and enter email id
		WebElement email = findElementWithWait(("//*/input[@name='email'][@type='email']"));
		Assertions.assertNotNull(email);
		assertEquals("email", email.getAttribute("name"));
		email.click();
		email.sendKeys("a@b.com");
		// password

		WebElement pwd = findElementWithWait(("//*/input[@name='password'][@type='password']"));
		Assertions.assertNotNull(pwd);
		assertEquals("password", pwd.getAttribute("name"));
		pwd.click();
		pwd.sendKeys("someblah1");

		// Click Sign In
		WebElement login = findElementWithWait(("//*/button[contains(.,'Sign In')][@type='submit']"));
		Assertions.assertNotNull(login);
		assertEquals("Sign In", login.getText());
		login.click();

	}

	private void shouldRegisterAUser() {
		// Click 'SignIn/Register'
		WebElement signIn = findElementWithWait(("//*/p[contains(.,'Sign In / Register')]"));
		Assertions.assertNotNull(signIn);
		assertEquals("Sign In / Register", signIn.getText());
		signIn.click();

		// Click 'SignIn/Register'
		WebElement registerNow = findElementWithWait(("//*/button[contains(.,'Register Now')]"));
		Assertions.assertNotNull(registerNow);
		assertEquals("Register Now", registerNow.getText());
		registerNow.click();

		// Click on email textfield and enter email id
		WebElement email = findElementWithWait(("//*/input[@name='email']"));
		Assertions.assertNotNull(email);
		assertEquals("email", email.getAttribute("name"));
		email.click();
		email.sendKeys("a@b.com");

		// Click 'FirstName'
		WebElement firstName = findElementWithWait(("//*/input[@name='firstName']"));
		Assertions.assertNotNull(firstName);
		assertEquals("firstName", firstName.getAttribute("name"));
		firstName.click();
		firstName.sendKeys("testuserfirstName");

		// Click 'LastName'
		WebElement lastName = findElementWithWait(("//*/input[@name='lastName']"));
		Assertions.assertNotNull(lastName);
		assertEquals("lastName", lastName.getAttribute("name"));
		lastName.click();
		lastName.sendKeys("testuserlastName");

		// Click 'Password'
		WebElement password1 = findElementWithWait(("//*/input[@name='password1']"));
		Assertions.assertNotNull(password1);
		assertEquals("password1", password1.getAttribute("name"));
		password1.click();
		password1.sendKeys("someblah1");

		// Click 'Verify Password'
		WebElement password2 = findElementWithWait(("//*/input[@name='password2']"));
		Assertions.assertNotNull(password2);
		assertEquals("password2", password2.getAttribute("name"));
		password2.click();
		password2.sendKeys("someblah1");

		// Click 'Complete Registration'
		WebElement complete = findElementWithWait(("//*/button[contains(.,'Complete Registration ')]"));
		Assertions.assertNotNull(complete);
		assertEquals("Complete Registration", complete.getText());
		complete.click();
	}

	private void shouldSelectABedByClicks() {
		// Click 'All Categories'
		WebElement element = findElementWithWait(("//*/p[contains(.,'All Categories')]"));
		Assertions.assertNotNull(element);
		assertEquals("All Categories", element.getText());
		element.click();

		// Click 'Beds'
		WebElement elementBeds = findElementWithWait(("//*/p[contains(.,'Beds')]"));
		Assertions.assertNotNull(elementBeds);
		assertEquals("Beds", elementBeds.getText());
		elementBeds.click();

		// Click 'sleepyHeadBed'
		WebElement sleepyHeadBed = findElementWithWait(("//*/p[contains(.,'Sleepy Head Low Key Double Bed')]"));
		Assertions.assertNotNull(sleepyHeadBed);
		assertEquals("Sleepy Head Low Key Double Bed", sleepyHeadBed.getText());
		sleepyHeadBed.click();
	}

	private void shouldAddToCart() {
		// Click 'All Categories'
		WebElement element = findElementWithWait(("//*/button[contains(.,'Add to Cart')]"));
		Assertions.assertNotNull(element);
		assertEquals("Add to Cart", element.getText());
		element.click();
	}

	private void shouldCheckout() {
		// Click 'Checkout'
		WebElement element = findElementWithWait(("//*/button[contains(.,'Checkout')]"));
		Assertions.assertNotNull(element);
		assertEquals("Checkout", element.getText());
		element.click();
	}

	private void shouldCreateAddress() {
		// Click 'Create a New Address'
		WebElement element = findElementWithWait(("//*/button[contains(.,'Create a New Address')]"));
		Assertions.assertNotNull(element);
		assertEquals("Create a New Address", element.getText());
		element.click();

		// Click 'Address name' and enter
		WebElement addressName = findElementWithWait(("//*/input[@id='newAddress-nickName']"));
		Assertions.assertNotNull(addressName);
		assertEquals("newAddress-nickName", addressName.getAttribute("id"));
		addressName.click();
		addressName.sendKeys("HomeAddressName" + RandomString.make());

		// Click 'First name' and enter
		WebElement fName = findElementWithWait(("//*/input[@id='newAddress-firstName']"));
		Assertions.assertNotNull(fName);
		assertEquals("newAddress-firstName", fName.getAttribute("id"));
		fName.click();
		fName.sendKeys("MyFirstName");

		// Click 'Last name' and enter
		WebElement lName = findElementWithWait(("//*/input[@id='newAddress-lastName']"));
		Assertions.assertNotNull(lName);
		assertEquals("newAddress-lastName", lName.getAttribute("id"));
		lName.click();
		lName.sendKeys("MyLastName");

		// Click 'Last name' and enter
		WebElement add1Name = findElementWithWait(("//*/input[@id='newAddress-address1']"));
		Assertions.assertNotNull(add1Name);
		assertEquals("newAddress-address1", add1Name.getAttribute("id"));
		add1Name.click();
		add1Name.sendKeys("addressName1");

		// Click 'Last name' and enter
		WebElement add2Name = findElementWithWait(("//*/input[@id='newAddress-address2']"));
		Assertions.assertNotNull(add2Name);
		assertEquals("newAddress-address2", add2Name.getAttribute("id"));
		add2Name.click();
		add2Name.sendKeys("addressName2");

		// Click 'Address City' and enter
		WebElement addCity = findElementWithWait(("//*/input[@id='newAddress-city']"));
		Assertions.assertNotNull(addCity);
		assertEquals("newAddress-city", addCity.getAttribute("id"));
		addCity.click();
		addCity.sendKeys("address city");

		// Click 'Address country' and enter
		WebElement country = findElementWithWait(("//*/input[@name='country']"));
		Assertions.assertNotNull(country);
		assertEquals("country", country.getAttribute("name"));
		country.click();
		country.sendKeys("India");

		// Click 'Address state' and enter
		WebElement state = findElementWithWait(("//*/input[@name='state']"));
		Assertions.assertNotNull(state);
		assertEquals("state", state.getAttribute("name"));
		state.click();
		state.sendKeys("state");

		// Click 'Address zip' and enter
		WebElement zip = findElementWithWait(("//*/input[@id='newAddress-zipCode']"));
		Assertions.assertNotNull(zip);
		assertEquals("newAddress-zipCode", zip.getAttribute("id"));
		zip.click();
		zip.sendKeys("0000");

		// Click 'Address phone' and enter
		WebElement phone = findElementWithWait(("//*/input[@name='phone1']"));
		Assertions.assertNotNull(phone);
		assertEquals("phone1", phone.getAttribute("name"));
		phone.click();
		phone.sendKeys("010101011");

		// Click 'Address email' and enter
		WebElement email = findElementWithWait(("//*/input[@name='email1'][@type='email'][@id='newAddress-email']"));
		Assertions.assertNotNull(email);
		assertEquals("email1", email.getAttribute("name"));
		email.click();
		email.sendKeys("a@b.com");

		// Click 'Save Address' and enter 'Save and select this address'
		WebElement save = findElementWithWait(("//*/button[contains(.,'Save and select this address')]"));
		Assertions.assertNotNull(save);
		assertEquals("Save and select this address", save.getText());
		save.click();
	}

	private void shouldSelectDeliveryType() {
		WebElement deliver = findElementWithWait(("//*/label[contains(.,'Express')]"));
		Assertions.assertNotNull(deliver);
		deliver.click();

		WebElement continueP = findElementWithWait(("//*/button[contains(.,'Continue to Payment')]"));
		Assertions.assertNotNull(continueP);
		assertEquals("Continue to Payment", continueP.getText());
		continueP.click();
	}

	@Test
	public void verifyPressEnterKeyInSearchBox() {
		// Click 'All Categories'
		WebElement element = findElementWithWait(("//*/input[@name='searchTerm']"));
		Assertions.assertNotNull(element);
		assertEquals("searchTerm", element.getAttribute("name"));
		assertEquals("input", element.getTagName());
		element.click();

		element.sendKeys("Bunk Bed");
		element.sendKeys(Keys.ENTER);
		assertThatNoException();
	}
	
	@Test
	public void verifyKitchenMenuContainsChildItems() {
		// Click 'All Categories'
		WebElement element = findElementWithWait(("//*/p[contains(.,'Kitchen')]"));
		Assertions.assertNotNull(element);
		assertEquals("Kitchen", element.getText());
		element.click();
		
		assertNotNull(element.findElement(By.xpath("//a[contains(.,'Cabinets')]")));
		assertNotNull(element.findElement(By.xpath("//a[contains(.,'Countertops')]")));
	}
	
	@AfterAll
	public static void terminateeDriver() {
		System.out.println("Terminating driver instance");
		driver.close();
	}
}

class TestConfig {

	static Properties props = new Properties();
	static {
		boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
		String propFile = isWindows ? "application-test.properties": "application-test-linux.properties";
		try (InputStream resourceStream = new ClassPathResource(propFile).getInputStream()) {
			props.load(resourceStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getPropertyAsString(String key) {
		return System.getenv(key) != null? System.getenv(key) : props.getProperty(key);
	}
	
	public static int getPropertyAsInt(String key) {
		return Integer.parseInt(props.getProperty(key));
	}
}

