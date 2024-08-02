package demo1;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.annotations.Test;

public class SatechAssiganments {
	static WebDriverWait wait;

	@Test(priority = 1)
	public void verifyAllMenuItemsAccessible() {
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get("https://www.tendable.com");

		// List of expected menu items
		String[] expectedMenuItems = { "Home", "Our Story", "Our Solution", "Why Tendable" };

		// Find the navigation menu
		WebElement navMenu = driver.findElement(By.cssSelector("nav.menu"));

		// Find all menu items
		List<WebElement> menuItems = navMenu.findElements(By.cssSelector("ul.nav > li > a"));

		// Verify each menu item
		for (String expectedItem : expectedMenuItems) {
			boolean found = false;
			for (WebElement menuItem : menuItems) {
				if (menuItem.getText().trim().equals(expectedItem)) {
					Assert.assertTrue(menuItem.isDisplayed(), "Menu item '" + expectedItem + "' is not visible");
					Assert.assertTrue(menuItem.isEnabled(), "Menu item '" + expectedItem + "' is not enabled");
					found = true;
					break;
				}
			}
			Assert.assertTrue(found, "Menu item '" + expectedItem + "' not found");
		}

		System.out.println("All menu items are accessible and verified successfully.");

	}

	@Test(priority = 2)
	private static void verifyRequestDemoButton() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tendable.com");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get("https://www.tendable.com");
		// Look for the "Request a Demo" button
		List<WebElement> demoButtons = driver
				.findElements(By.xpath("//a[contains(text(), 'Request a Demo') or contains(text(), 'Request Demo')]"));

		Assert.assertFalse(demoButtons.isEmpty(), "'Request a Demo' button not found on the page");

		WebElement demoButton = demoButtons.get(0);
		Assert.assertTrue(demoButton.isDisplayed(), "'Request a Demo' button is not visible");
		Assert.assertTrue(demoButton.isEnabled(), "'Request a Demo' button is not enabled");

		// Optionally, you can check if the button is clickable
		wait.until(ExpectedConditions.elementToBeClickable(demoButton));
		String[] menuItems = { "Home", "Our Story", "Our Solution", "Why Tendable" };

		for (String menuItem : menuItems) {

			// Click on the menu item
			WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(menuItem)));
			menu.click();

			// Verify the page title to ensure we've navigated to the correct page
			wait.until(ExpectedConditions.titleContains(menuItem));

			// Verify "Request a Demo" button
			verifyRequestDemoButton();

			System.out.println("Verified '" + menuItem + "' page and 'Request a Demo' button.");
		}

	}

	@Test(priority = 3)
	private static void testContactForm() {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tendable.com");
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Navigate to Contact Us page
		WebElement contactUsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Contact Us")));
		contactUsLink.click();

		// Wait for the form to be visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("contact_request_form")));

		// Fill out the form
		new Select(driver.findElement(By.id("form_type"))).selectByVisibleText("Marketing");
		driver.findElement(By.id("first_name")).sendKeys("John");
		driver.findElement(By.id("last_name")).sendKeys("Doe");
		driver.findElement(By.id("email")).sendKeys("john.doe@example.com");
		driver.findElement(By.id("phone_number")).sendKeys("1234567890");
		driver.findElement(By.id("organization_name")).sendKeys("Test Organization");
		new Select(driver.findElement(By.id("country"))).selectByVisibleText("United States");
		// Intentionally leaving out the message field

		// Submit the form
		driver.findElement(By.name("commit")).click();

		// Check for error message
		try {
			WebElement errorMessage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".errors")));
			if (errorMessage.isDisplayed()) {
				System.out.println("TEST PASS: Error message is displayed as expected.");
			} else {
				System.out.println("TEST FAIL: Error message is not displayed.");
			}
		} catch (Exception e) {
			System.out.println("TEST FAIL: Error message is not displayed. Exception: " + e.getMessage());
		}
	}
}
