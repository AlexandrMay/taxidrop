Feature: admin_actions

Scenario Outline:
  Given sending /admin/drivers.list using <token>
  When GET request send to <resource>
  Then Statuscode <status_code> is received
  And Response contains <key> and <value>
  Examples:
  |token|resource|status_code|key|value|
  |"true"|/admin/drivers.list?amount=10|200|"total"|3|
  |"true"|/admin/drivers.list?amount=0 |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
  |false |/admin/drivers.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|


  