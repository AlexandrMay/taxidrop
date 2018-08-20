Feature: passenger_payments_actions

  Scenario Outline: /passenger/wallet
    Given sending /passenger/request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/wallet contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/wallet|200|"total"|"fromDB"|
    |false|/passenger/wallet|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/payment.withdraw
    Given sending /passenger/payment.withdraw using <token>, <amount>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|amount|resource|status_code|key|value|
    |"true"|45000|/passenger/payment.withdraw|200|"success"|200|
    |"true"|5000|/passenger/payment.withdraw|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|10000|/passenger/payment.withdraw|401|error.message|Authentication key: 'false' is incorrect.|

    Scenario Outline: deleting payments from DB
      Given sending /passenger/payment.withdraw using <token>, <amount>
      When POST request send to <resource>
      Then Statuscode <status_code> is received
      And Response contains <key> and <value>
      And Payments and notifications are deleted from DB
      Examples:
        |token|amount|resource|status_code|key|value|
        |"true"|5000|/passenger/payment.withdraw|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|

    Scenario Outline: /passenger/bank.add
      Given sending /passenger/bank.add using <token>, <bank_name>, <bank_account>, <account_name>
      When POST request send to <resource>
      Then Statuscode <status_code> is received
      And Response of /passenger/bank.add contains <key> and <value>
      Examples:
      |token|bank_name|bank_account|account_name|resource|status_code|key|value|
      |"true"|"AutoBank"|"123456123456123456"|"AccountName"|/passenger/bank.add|200|"id"|"fromDB"|
      |false|"AutoBank"|"123456123456123456"|"AccountName"|/passenger/bank.add|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|""|"123456123456123456"|"AccountName"|/passenger/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_name' are required.|
      |"true"|"AutoBank"|""|"AccountName"|/passenger/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_account' are required.|
      |"true"|"AutoBank"|"1"|"AccountName"|/passenger/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_account' are malformed or incorrect.|
      |"true"|"AutoBank"|"123456123456123456"|""|/passenger/bank.add|400|error.message|Incorrect request body. Parameters: 'account_name' are required.|







