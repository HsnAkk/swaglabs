package AbstractComponents;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import pageobjects.CartPage;

public class AbstractComponent {

	WebDriver driver;

	public AbstractComponent(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="shopping_cart_container")
	WebElement cartNavButton;
	
	public void waitForElementToAppear(By findBy) {
		// EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// wait until
		wait.until(ExpectedConditions.visibilityOfElementLocated(findBy));
	}

	public void waitForWebElementToAppear(WebElement findBy) {
		// EXPLICIT WAIT
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		// wait until
		wait.until(ExpectedConditions.visibilityOf(findBy));
	}

	public String getLabel (WebElement element) {
		String text = element.getText();
		return text;
	}
	
	public CartPage goToCartPage() {
		cartNavButton.click();
		
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}

}