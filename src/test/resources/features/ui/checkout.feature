@ui @checkout
Feature: SauceDemo checkout

  @smoke
  Scenario: Test 3 - Checkout journey
  
     Given the user is logged in successfully
    When the user adds the product "<Product>" to the basket
    And the user completes the checkout with "<Product>","<FirstName>", "<LastName>" and "<PostCode>"
    Then the order should be completed successfully

    Examples:
      | Product             | FirstName | LastName | PostCode |
      | Sauce Labs Backpack | John      | Doe      | E14 5AB  |
