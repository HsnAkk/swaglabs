package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import AbstractComponents.AbstractComponent;

public class OverviewPage extends AbstractComponent {
	WebDriver driver;
	String overviewLabel = "Checkout: Overview";

	public OverviewPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// PAGE FACTORY

	@FindBy(css = ".subheader")
	WebElement overviewLabelElement;

	@FindBy(css = "a.btn_action.cart_button")
	WebElement finishButton;

	
	
	// ACTIONS

	public void verifyOverviewLabel() {
		// explicit wait
		waitForWebElementToAppear(overviewLabelElement);
		// get Label of page and verify it
		String label = getLabel(overviewLabelElement);
		Assert.assertEquals(overviewLabel, label);
	}
	
	public FinishPage goToFinishPage () {
		finishButton.click();
		
		return new FinishPage(driver);
	}

}
