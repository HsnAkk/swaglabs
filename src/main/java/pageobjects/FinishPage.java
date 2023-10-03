package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class FinishPage extends AbstractComponent {
	WebDriver driver;
	String finishLabel = "Finish";

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
	
	
	// ACTIONS
	
	
	

}
