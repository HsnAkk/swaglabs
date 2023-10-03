package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
	WebDriver driver;
	String checkoutLabel = "Checkout: Your Information";
	String firstName = "Joe";
	String lastName = "Doe";
	String postalCode = "40500";

	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE FACTORY

	@FindBy(css = ".subheader")
	WebElement checkoutLabelElement;

	@FindBy(css = "input#first-name")
	WebElement checkoutFirstName;

	@FindBy(css = "input#last-name")
	WebElement checkoutLastName;

	@FindBy(css = "input#postal-code")
	WebElement checkoutPostalCode;

	@FindBy(css = "input[type=\"submit\"]")
	WebElement submitButton;

	// ACTIONS

	public void verifyCheckoutLabel() {
		// explicit wait
		waitForWebElementToAppear(checkoutLabelElement);
		// get Label of page and verify it
		String label = getLabel(checkoutLabelElement);
		Assert.assertEquals(checkoutLabel, label);
	}
	
	public OverviewPage postInformation () {
		checkoutFirstName.sendKeys(firstName);
		checkoutLastName.sendKeys(lastName);
		checkoutPostalCode.sendKeys(postalCode);
		submitButton.click();
		
		return new OverviewPage(driver);
	}
	
}
