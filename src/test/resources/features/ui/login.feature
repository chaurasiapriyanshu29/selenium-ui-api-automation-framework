@ui @login
Feature: SauceDemo login

  @smoke
  Scenario: Test 1 - Successful login
    Given the user navigates to the SauceDemo application
    When the user logs in with valid credentials
    Then the user should be logged in successfully
    Then the user validates "<Title>" and "<Url>"

  Examples:
    | Title     | Url            |
    | Swag Labs | inventory.html |