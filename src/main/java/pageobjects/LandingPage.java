package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent {

	WebDriver driver;

	public LandingPage (WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
		/*
		 * Page Factory is a class provided by Selenium WebDriver to support Page Object
		 * Design patterns. In Page Factory, testers use @FindBy annotation. The
		 * initElements method is used to initialize web elements. @FindBy: An
		 * annotation used in Page Factory to locate and declare web elements using
		 * different locators.
		 */
	}
	
	// ********** ALL WEB ELEMENTS *************
	
	@FindBy(id="user-name")
	WebElement usernameInput;
	
	@FindBy(id="password")
	WebElement passwordInput;
	
	@FindBy(id="login-button")
	WebElement loginButton;
	
	@FindBy(css="h3[data-test=\"error\"]")
	WebElement errorMessageToaster;
	
	// ********** ACTIONS *********************
	
	public ProductCatalogue loginApplication (String username, String password) {
		usernameInput.sendKeys(username);
		passwordInput.sendKeys(password);
		loginButton.click();
		
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	public String getErrorMessage () {
		waitForWebElementToAppear(errorMessageToaster);
		return errorMessageToaster.getText();
	}
	
	public void goTo() {
		// webpage to test
		driver.get("https://www.saucedemo.com/v1/index.html");
	}
	

}
