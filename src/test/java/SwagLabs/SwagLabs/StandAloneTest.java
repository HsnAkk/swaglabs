package SwagLabs.SwagLabs;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTest {

	public static WebDriver driver;

	private static final By LOCATOR_USERNAME = By.id("user-name");
	private static final By LOCATOR_PASSWORD = By.cssSelector("#password");
	private static final By LOCATOR_LOGIN_BUTTON = By.xpath("//input[@id=\"login-button\"]");
	private static final By LOCATOR_PRODUCTS_LABEL = By.className("product_label");
	private static final By LOCATOR_PRODUCT_NAME = By.xpath("//div[@class=\"inventory_item_name\"]");
	private static final By LOCATOR_PRODUCT_PRICE = By.xpath("//div[@class=\"inventory_item_price\"]");
	private static final By LOCATOR_PRODUCT_SORT_SELECT = By.className("product_sort_container");

	public static void main(String[] args) {

		// ChromeDriver
		System.setProperty("webdriver.chrome.driver", "/Users/albatros/Selenium_Training/drivers/chromedriver");
		driver = new ChromeDriver();

		// IMPLICIT WAIT
		// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// webpage to test
		driver.get("https://www.saucedemo.com/v1/index.html");

		// let's login with the credentials
		String username = "standard_user";
		String password = "secret_sauce";

		// enter username
		driver.findElement(LOCATOR_USERNAME).sendKeys(username);

		// enter password
		driver.findElement(LOCATOR_PASSWORD).sendKeys(password);

		// click login button
		driver.findElement(LOCATOR_LOGIN_BUTTON).click();

		// EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// wait until "Products" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_PRODUCTS_LABEL));

		// get the label of products page, and check the label is "Products"
		String productslabel = driver.findElement(LOCATOR_PRODUCTS_LABEL).getText();
		Assert.assertEquals("Products", productslabel);

		// grab all products, and check the number of products should be 6
		List<WebElement> products = driver.findElements(LOCATOR_PRODUCT_NAME);
		Assert.assertEquals(6, products.size());

		// check sorting works fine
		// sorting by Name and in accending order, which one is default option of select
		// element
		Boolean result = Check_Sorting("Name", "accending");
		System.out.println("Result : " + result);
		Assert.assertTrue(result);

		// sorting by Name and in descending order
		ClickSelectOption("za");
		Boolean result2 = Check_Sorting("Name", "descending");
		System.out.println("Result2 : " + result2);
		Assert.assertTrue(result2);

		// sorting by price and in accending order
		ClickSelectOption("lohi");
		Boolean result3 = Check_Sorting("price", "accending");
		System.out.println("Result3 : " + result3);
		Assert.assertTrue(result3);

		// sorting by price and in descending order
		ClickSelectOption("hilo");
		Boolean result4 = Check_Sorting("price", "descending");
		System.out.println("Result4 : " + result4);
		Assert.assertTrue(result4);

		// driver.close();

	}

	public static Boolean Check_Sorting(String sortType, String sortOrder) {
		List<WebElement> products = null;
		List productNames = null;
		List sortedNames = null;
		Boolean areSorted = false;

		if (sortType.equalsIgnoreCase("NAME")) {
			products = driver.findElements(LOCATOR_PRODUCT_NAME);
			productNames = products.stream().map(n -> n.getText()).collect(Collectors.toList());
			sortedNames = productNames;

			if (sortOrder.equalsIgnoreCase("accending")) {
				Collections.sort(sortedNames);
			} else if (sortOrder.equalsIgnoreCase("descending")) {
				Collections.reverse(sortedNames);
			}

		} else if (sortType.equalsIgnoreCase("PRICE")) {
			products = driver.findElements(LOCATOR_PRODUCT_PRICE);
			
			productNames = products.stream().map(n -> Float.parseFloat(n.getText().substring(1))).collect(Collectors.toList());
			sortedNames = productNames;

			if (sortOrder.equalsIgnoreCase("accending")) {
				Collections.sort(sortedNames);
			} else if (sortOrder.equalsIgnoreCase("descending")) {
				Collections.reverse(sortedNames);
			}
		}
		areSorted = productNames.equals(sortedNames);
		return areSorted;
	}

	public static void ClickSelectOption(String value) {
		// static dropdown with SELECT tag
		WebElement staticDropdown = driver.findElement(LOCATOR_PRODUCT_SORT_SELECT);

		// Dropdown with SELECT TAG
		Select dropdown = new Select(staticDropdown);
		dropdown.selectByValue(value);

	}

}
