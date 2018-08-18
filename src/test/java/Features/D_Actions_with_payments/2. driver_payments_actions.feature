Feature: driver_payments_actions

  Scenario Outline: /driver/wallet
    Given sending /driver/wallet using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /driver/wallet contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/wallet|200|"total"|"fromDB"|
      |false|/driver/wallet|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: /driver/bank.add
    Given sending /driver/bank.add using <token>, <bank_name>, <bank_account>, <account_name>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /driver/bank.add contains <key> and <value>
    Examples:
      |token|bank_name|bank_account|account_name|resource|status_code|key|value|
      |"true"|"AutoBank"|"123456123456123456"|"AccountName"|/driver/bank.add|200|"bank_id"|"fromDB"|
      |false|"AutoBank"|"123456123456123456"|"AccountName"|/driver/bank.add|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|""|"123456123456123456"|"AccountName"|/driver/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_name' are required.|
      |"true"|"AutoBank"|""|"AccountName"|/driver/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_account' are required.|
      |"true"|"AutoBank"|"1"|"AccountName"|/driver/bank.add|400|error.message|Incorrect request body. Parameters: 'bank_account' are malformed or incorrect.|
      |"true"|"AutoBank"|"123456123456123456"|""|/driver/bank.add|400|error.message|Incorrect request body. Parameters: 'account_name' are required.|

