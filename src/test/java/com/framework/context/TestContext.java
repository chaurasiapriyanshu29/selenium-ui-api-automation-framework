package com.framework.context;

import com.framework.driver.DriverFactory;
import com.framework.pages.CartPage;
import com.framework.pages.CheckoutPage;
import com.framework.pages.LoginPage;
import com.framework.pages.ProductsPage;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;

public class TestContext {

	private Scenario scenario;
	private LoginPage loginPage;
	private ProductsPage productsPage;
	private CartPage cartPage;
	private CheckoutPage checkoutPage;
	private CommonMethods commonMethods;
	private Response apiResponse;

	public Scenario getScenario() {
		return scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public LoginPage loginPage() {
		if (loginPage == null)
			loginPage = new LoginPage(DriverFactory.getDriver());
		return loginPage;
	}

	public ProductsPage productsPage() {
		if (productsPage == null)
			productsPage = new ProductsPage(DriverFactory.getDriver());
		return productsPage;
	}

	public CommonMethods commonMethods() {
		if (commonMethods == null)
			commonMethods = new CommonMethods(DriverFactory.getDriver());
		return commonMethods;
	}

	public CartPage cartPage() {
		if (cartPage == null)
			cartPage = new CartPage(DriverFactory.getDriver());
		return cartPage;
	}

	public CheckoutPage checkoutPage() {
		if (checkoutPage == null)
			checkoutPage = new CheckoutPage(DriverFactory.getDriver());
		return checkoutPage;
	}

	public Response getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(Response apiResponse) {
		this.apiResponse = apiResponse;
	}
}
