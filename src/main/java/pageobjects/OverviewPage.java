package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import AbstractComponents.AbstractComponent;

public class OverviewPage extends AbstractComponent {
	WebDriver driver;
	String overviewLabel = "Checkout: Overview";

	public OverviewPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
}
