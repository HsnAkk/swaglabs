package pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// PAGEFACTORY
	
	@FindBy(css=".subheader")
	WebElement cartLabelElement;
	
	@FindBy(css=".inventory_item_name")
	List<WebElement> cartProductNames;
	
	@FindBy(css=".cart_footer > .checkout_button")
	WebElement checkoutButton;
	
	
	// ACTIONS
	
	public Boolean verifyProductDisplay(String productName) {
		Boolean isMatched = cartProductNames.stream().anyMatch(elem -> elem.getText().equalsIgnoreCase(productName));
		return isMatched;
	}
	
	public CheckoutPage goToCheckout() {
		checkoutButton.click();
				
		return new CheckoutPage(driver);
	}
}
