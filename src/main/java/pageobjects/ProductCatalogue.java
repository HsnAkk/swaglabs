package pageobjects;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import AbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent {

	WebDriver driver;
	String productLabel = "Products";

	public ProductCatalogue(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGEFACTORY
	@FindBy(css = ".product_label")
	WebElement productsLabelElement;

	@FindBy(css = "div.inventory_item_name")
	static List<WebElement> productNameElements;

	@FindBy(css = "div.inventory_item_price")
	static List<WebElement> productPriceElements;

	@FindBy(css = ".product_sort_container")
	static WebElement selectElement;

	@FindBy(css = ".inventory_item")
	List<WebElement> productElements;

	// BY variables
	By productsBy = By.cssSelector(".inventory_item");
	By productBameBy = By.cssSelector(".inventory_item_name");
	By addToCartBy = By.cssSelector(".btn_primary.btn_inventory");

	// ACTIONS
	public void verifyProductsLabel() {
		// explicit wait
		waitForWebElementToAppear(productsLabelElement);
		// get Label of page and verify it
		String label = getLabel(productsLabelElement);
		Assert.assertEquals(productLabel, label);
	}

	public void verifySorting() {
		String[] sortingTypes = { "az", "za", "lohi", "hilo" };
		String[][] sorting = { { "Name", "accending" }, { "Name", "descending" }, { "price", "accending" },
				{ "price", "descending" } };
		for (int i = 0; i < sortingTypes.length; i++) {
			ClickSelectOption(sortingTypes[i]);
			Boolean result = Check_Sorting(sorting[i][0], sorting[i][1]);
			System.out.println("Result of " + sorting[i][0] + " " + sorting[i][1] + " :" + result);
			Assert.assertTrue(result);

		}
	}

	public List<WebElement> getProductsList() {
		// wait until all products listed or appears
		waitForElementToAppear(productsBy);
		return productElements;
	}

	public WebElement getProductByName(String productName) {
		WebElement product = getProductsList().stream()
				.filter(prod -> prod.findElement(productBameBy).getText().equals(productName))
				.findFirst().orElse(null);
		return product;
	}
	
	public void addProductToCart(String productName) {
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCartBy).click();
	}

	public static Boolean Check_Sorting(String sortType, String sortOrder) {
		List<String> productNames = null;
		List<Float> productPrices = null;
		List<String> sortedNames = null;
		List<Float> sortedPrices = null;
		Boolean areSorted = false;

		if (sortType.equalsIgnoreCase("NAME")) {
			productNames = productNameElements.stream().map(n -> n.getText()).collect(Collectors.toList());
			sortedNames = productNames;

			if (sortOrder.equalsIgnoreCase("accending")) {
				Collections.sort(sortedNames);
			} else if (sortOrder.equalsIgnoreCase("descending")) {
				Collections.reverse(sortedNames);
			}
			areSorted = productNames.equals(sortedNames);

		} else if (sortType.equalsIgnoreCase("PRICE")) {
			productPrices = productPriceElements.stream().map(n -> Float.parseFloat(n.getText().substring(1)))
					.collect(Collectors.toList());
			sortedPrices = productPrices;

			if (sortOrder.equalsIgnoreCase("accending")) {
				Collections.sort(sortedPrices);
			} else if (sortOrder.equalsIgnoreCase("descending")) {
				Collections.reverse(sortedPrices);
			}
			areSorted = productPrices.equals(sortedPrices);
		}
		return areSorted;
	}

	public static void ClickSelectOption(String value) {
		// static dropdown with SELECT tag
		WebElement staticDropdown = selectElement;

		// Dropdown with SELECT TAG
		Select dropdown = new Select(staticDropdown);
		dropdown.selectByValue(value);
	}

}
