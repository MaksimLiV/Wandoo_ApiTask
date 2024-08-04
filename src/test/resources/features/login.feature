Feature: User Login and Balance Check

  Scenario: Authorize user and fetch balance
    Given User logs in with valid credentials
    When User fetches the account balance
    Then The opening balance should be 0.00