Feature: User Registration
  As a new user
  I want to register on nopCommerce
  So that I can make purchases and manage my account

  Scenario: Successful registration with valid credentials
    Given I am on the registration page
    When I select gender "Male"
    And I enter first name "John"
    And I enter last name "Doe"
    And I enter register email "johndoe12318@example.com"
    And I enter company name "ABC Company"
    And I enter register password "password123"
    And I enter confirm password "password123"
    And I click the register button
    Then I should see the registration success message
    And I should be able to logout

  Scenario: Registration with existing email should fail
    Given I am on the registration page
    When I select gender "Female"
    And I enter first name "Jane"
    And I enter last name "Doe"
    And I enter register email "johndoe12318@example.com"
    And I enter company name "XYZ Company"
    And I enter register password "password123"
    And I enter confirm password "password123"
    And I click the register button
    Then I should see an error message about existing email

  Scenario: Registration with password mismatch should fail
    Given I am on the registration page
    When I select gender "Male"
    And I enter first name "Bob"
    And I enter last name "Smith"
    And I enter register email "bobsmith@example.com"
    And I enter company name "Test Company"
    And I enter register password "password123"
    And I enter confirm password "differentpassword"
    And I click the register button
    Then I should see an error message about password mismatch
