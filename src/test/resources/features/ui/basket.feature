@ui @basket
Feature: SauceDemo basket

  @smoke
  Scenario: Test 2 - Add product to basket
    Given the user is logged in successfully
    When the user adds the product "<Product>" to the basket
    Then the basket count should be updated to 1
    
    Examples:
      | Product             |
      | Sauce Labs Backpack |
