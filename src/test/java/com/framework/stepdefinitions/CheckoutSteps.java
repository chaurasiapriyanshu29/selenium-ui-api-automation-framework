package com.framework.stepdefinitions;

import com.framework.config.ConfigReader;
import com.framework.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class CheckoutSteps {

	private final TestContext ctx;

	public CheckoutSteps(TestContext ctx) {
		this.ctx = ctx;
	}

	@When("the user completes the checkout with {string},{string}, {string} and {string}")
	public void checkout(String productName, String firstName, String lastName, String postCode) {
		ctx.productsPage().openBasket();
		Assert.assertTrue(ctx.cartPage().isDisplayed(), "Cart page not displayed");
		Assert.assertTrue(ctx.cartPage().containsProduct(productName), "Added product missing from cart");
		ctx.cartPage().proceedToCheckout();
		ctx.checkoutPage().enterShippingDetails(firstName, lastName, postCode);
		ctx.checkoutPage().finishOrder();
	}

	@Then("the order should be completed successfully")
	public void orderComplete() {
		Assert.assertTrue(ctx.checkoutPage().isOnCompletePage(), "Not on checkout-complete page");
		Assert.assertEquals(ctx.checkoutPage().getCompletionMessage(), "Thank you for your order!");
	}
}
