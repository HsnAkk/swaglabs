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
	private static final By LOCATOR_LOGIN_ERROR = By.xpath("//h3[@data-test=\"error\"] ");
	private static final By LOCATOR_PRODUCT = By.className("inventory_item");
	private static final By LOCATOR_PRODUCTS_LABEL = By.className("product_label");
	private static final By LOCATOR_PRODUCT_NAME = By.xpath("//div[@class=\"inventory_item_name\"]");
	private static final By LOCATOR_PRODUCT_PRICE = By.xpath("//div[@class=\"inventory_item_price\"]");
	private static final By LOCATOR_PRODUCT_SORT_SELECT = By.className("product_sort_container");
	private static final By LOCATOR_CART = By.id("shopping_cart_container");
	private static final By LOCATOR_CART_LABEL = By.cssSelector(".subheader");
	private static final By LOCATOR_CHECKOUT_BUTTON = By.cssSelector(".cart_footer > .checkout_button");
	private static final By LOCATOR_CHECKOUT_LABEL = By.cssSelector(".subheader");
	private static final By LOCATOR_CHECKOUT_FIRSTNAME = By.xpath("//input[@id=\"first-name\"]");
	private static final By LOCATOR_CHECKOUT_LASTNAME = By.xpath("//input[@id=\"last-name\"]");
	private static final By LOCATOR_CHECKOUT_POSTALCODE = By.xpath("//input[@id=\"postal-code\"]");
	private static final By LOCATOR_CHECKOUT_SUBMIT = By.xpath("//input[@type=\"submit\"]");
	private static final By LOCATOR_CHECKOUT_OVERVIEW_LABEL = By.cssSelector("div.subheader");
	private static final By LOCATOR_CHECKOUT_OVERVIEW_FINISH = By.cssSelector("a.btn_action.cart_button");
	private static final By LOCATOR_FINISH_LABEL = By.cssSelector("div.subheader");
	private static final By LOCATOR_FINISH_SUCCESS = By.cssSelector("h2.complete-header");
	private static final By LOCATOR_BM_MENU = By.cssSelector("div.bm-burger-button button");
	private static final By LOCATOR_BM_MENU_ITEMS = By.cssSelector(".bm-item-list");

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
		String fakeUsername = "locked_out_user";
		String password = "secret_sauce";

		// enter username
		driver.findElement(LOCATOR_USERNAME).sendKeys(username);

		// enter fake username
		// driver.findElement(LOCATOR_USERNAME).sendKeys(fakeUsername);

		// enter password
		driver.findElement(LOCATOR_PASSWORD).sendKeys(password);

		// click login button
		driver.findElement(LOCATOR_LOGIN_BUTTON).click();

		// EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

		// wait until login error message appears
		// wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_LOGIN_ERROR));

		// get error message text
		// String errorMessage = driver.findElement(LOCATOR_LOGIN_ERROR).getText();
		// System.out.println(errorMessage);
		// Assert.assertTrue(errorMessage.contains("Epic sadface: Sorry, this user has
		// been locked out."));

		// wait until "Products" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_PRODUCTS_LABEL));

		// get the label of products page, and check the label is "Products"
		String productsLabel = driver.findElement(LOCATOR_PRODUCTS_LABEL).getText();
		Assert.assertEquals("Products", productsLabel);

		// grab all products, and check the number of products should be 6
		List<WebElement> productNames = driver.findElements(LOCATOR_PRODUCT_NAME);
		Assert.assertEquals(6, productNames.size());

		// check sorting works fine // sorting by Name and in accending order, which
		// one is default option of select element
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

		// iterate one by one all products and select "Sauce Labs Onesie" and add to
		// cart
		String selectedProduct = "Sauce Labs Onesie";
		List<WebElement> products = driver.findElements(LOCATOR_PRODUCT);
		WebElement selectedPro = products.stream().filter(product -> product
				.findElement(By.cssSelector(".inventory_item_name")).getText().equals(selectedProduct)).findFirst()
				.orElse(null);
		System.out.println("selectedPro " + selectedPro);
		// click add to cart button
		selectedPro.findElement(By.cssSelector(".btn_primary.btn_inventory")).click();

		// time to click cart button on top
		driver.findElement(LOCATOR_CART).click();

		// wait until "Your Cart" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_CART_LABEL));

		// verify that selected product is in cart
		List<WebElement> itemsInCart = driver.findElements(By.cssSelector(".cart_item .inventory_item_name"));
		Boolean isMatched = itemsInCart.stream().anyMatch(elem -> elem.getText().equalsIgnoreCase(selectedProduct));
		System.out.println("isMatched " + isMatched);
		Assert.assertTrue(isMatched);

		// consider "CONTINUE SHOPPING" BUTTON in CART ??????????????????????
		// consider "REMOVE" BUTTON in CART ?????????????????????????

		// click to checkout button
		driver.findElement(LOCATOR_CHECKOUT_BUTTON).click();

		// wait until "Checkout: Your Information" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_CHECKOUT_LABEL));

		String firstName = "Joe";
		String lastName = "Doe";
		String postalCode = "40500";

		// enter firstname
		driver.findElement(LOCATOR_CHECKOUT_FIRSTNAME).sendKeys(firstName);

		// enter lastname
		driver.findElement(LOCATOR_CHECKOUT_LASTNAME).sendKeys(lastName);

		// enter postalcode
		driver.findElement(LOCATOR_CHECKOUT_POSTALCODE).sendKeys(postalCode);

		// click submit button
		driver.findElement(LOCATOR_CHECKOUT_SUBMIT).click();

		// wait until "Checkout: Overview" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_CHECKOUT_OVERVIEW_LABEL));

		// click "finish" button
		driver.findElement(LOCATOR_CHECKOUT_OVERVIEW_FINISH).click();

		// wait until "Finish" label appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_FINISH_LABEL));
		
		String orderMessage = driver.findElement(LOCATOR_FINISH_SUCCESS).getText();
		System.out.println(orderMessage);
		Assert.assertEquals(orderMessage, "THANK YOU FOR YOUR ORDER");

		// open the burger menu to Logout
		driver.findElement(LOCATOR_BM_MENU).click();
		
		// wait until menu items appears
		wait.until(ExpectedConditions.visibilityOfElementLocated(LOCATOR_BM_MENU_ITEMS));
		
		driver.findElement(By.linkText("Logout")).click();
		
		driver.close();

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

			productNames = products.stream().map(n -> Float.parseFloat(n.getText().substring(1)))
					.collect(Collectors.toList());
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
