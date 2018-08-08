Feature: driver_actions


  Scenario: driver/profile.info
    Given Sending request with correct token
    When GET request is sent
    Then Status-code "200" is received
    And Response contains referral code

  Scenario Outline: profile.edit
    Given Sending profile.edit request with correct token and using <token>, <photo>, <first_name>, <last_name>, <email> parameters
    When PUT request /driver/profile.edit is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
  Examples:
  |token|photo|first_name|last_name|email|status_code|key|value|
  |"bearer"|"base64"|"robot"|"auto"|"test@test.com"|200|"success"|200|
  |false|"base64"|"robot"|"auto"|"test@test.com"|401|error.message|Authentication key: 'false' is incorrect.|
  |"bearer"|""|"robot"|"auto"|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'photo' are required.|
  |"bearer"|"base64"|""|"auto"|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
  |"bearer"|"base64"|"robot"|""|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
  |"bearer"|"base64"|"robot"|"auto"|""|400|error.message|Incorrect request body. Parameters: 'email' are required.|


  Scenario Outline: rating.info
    Given Sending rating.info request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
  Examples:
    |token|resource|status_code|key|value|
    |"bearer"|/driver/rating.info?amount=10|200|||
    |false   |/driver/rating.info?amount=10|401|error.message|Authentication key: 'false' is incorrect.|
    |"bearer"|/driver/rating.info?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |"bearer"|/driver/rating.info|400|error.message|Incorrect request body. Parameters: 'amount' are required.|


  Scenario Outline: pass.change
    Given Sending pass.change request using <token>, <password>, <password_old>
    When PUT request /driver/pass.change is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|password|password_old|status_code|key|value|
    |"bearer"|"4e34b28fa8c19987a04b68f314844b20"|"25d55ad283aa400af464c76d713c07ad"|200|"success"|200|
    |"bearer"|"25d55ad283aa400af464c76d713c07ad"|"4e34b28fa8c19987a04b68f314844b20"|200|"success"|200|
    |false|"4e34b28fa8c19987a04b68f314844b20"|"25d55ad283aa400af464c76d713c07ad"|401|error.message|Authentication key: 'false' is incorrect.|
    |"bearer"|""|"25d55ad283aa400af464c76d713c07ad"|400|error.message|Incorrect request body. Parameters: 'password' are required.|
    |"bearer"|"4e34b28fa8c19987a04b68f314844b20"|""|400|error.message|Incorrect request body. Parameters: 'password_old' are required.|
    |"bearer"|"123"|"25d55ad283aa400af464c76d713c07ad"|400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|
    |"bearer"|"4e34b28fa8c19987a04b68f314844b20"|"123"|400|error.message|Incorrect request body. Parameters: 'password_old' are malformed or incorrect.|
    |"bearer"|"25d55ad283aa400af464c76d713c07ad"|"4e34b28fa8c19987a04b68f314844b20"|400|error.message|Old password is incorrect.|














