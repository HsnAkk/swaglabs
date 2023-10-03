package TestComponents;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.CartPage;
import pageobjects.CheckoutPage;
import pageobjects.FinishPage;
import pageobjects.OverviewPage;
import pageobjects.ProductCatalogue;

import org.testng.Assert;

public class SubmitOrderTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void submitOrder(HashMap<String, String> input) throws IOException {

		// PAGE OBJECT MODEL BELOW

		// Landing Page (Login + go to products catalogue page)
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("username"), input.get("password"));

		// ProductsCatalogue PAGE
		productCatalogue.verifyProductsLabel();
		productCatalogue.verifySorting();
		productCatalogue.addProductToCart(input.get("productName"));

		// Go to Cart Page
		CartPage cartPage = productCatalogue.goToCartPage();

		// CART PAGE
		cartPage.verifyCartLabel();
		Boolean match = cartPage.verifyProductDisplay(input.get("productName"));
		Assert.assertTrue(match);

		// Go to Checkout Page
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.verifyCheckoutLabel();
		checkoutPage.addYourInformation();

		// Go to Overview Page
		OverviewPage overviewPage = checkoutPage.goToOverviewPage();
		
		// OVERVIEW PAGE
		overviewPage.verifyOverviewLabel();
		
		// go to Finish Page
		FinishPage finishpage = overviewPage.goToFinishPage();
		
		// FINISH PAGE
		finishpage.verifyFinishLabel();
		finishpage.verifySuccessMessage();
		
		// LOGOUT
		finishpage.logout();
		
		
		

	}

	@DataProvider
	public Object[][] getData() throws IOException {
		// get data from JSON file
		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "//src//test//java//data//purchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
