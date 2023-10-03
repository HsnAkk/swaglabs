package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import AbstractComponents.AbstractComponent;

public class FinishPage extends AbstractComponent {
	WebDriver driver;
	String finishLabel = "Finish";
	String successText = "THANK YOU FOR YOUR ORDER"; 

	public FinishPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	// PAGE FACTORY
	
	@FindBy(css = "div.subheader")
	WebElement finishLabelElement;

	@FindBy(css = "h2.complete-header")
	WebElement successMessage;
	
	// By
	By finishSuccess = By.cssSelector("h2.complete-header");
	
	// ACTIONS
	
	public void verifyFinishLabel() {
		// explicit wait
		waitForWebElementToAppear(finishLabelElement);
		// get Label of page and verify it
		String label = getLabel(finishLabelElement);
		Assert.assertEquals(finishLabel, label);
	}

	public void verifySuccessMessage() {
		String text = driver.findElement(finishSuccess).getText();
		Assert.assertEquals(successText, text);
	}

}
