Feature: passenger_actions



Scenario Outline: passenger/profile.info
  Given sending passenger/profile.info request using <token>
  When GET request send to <resource>
  Then Statuscode <status_code> is received
  And Response contains <key> and <value>
  Examples:
  |token   |resource               |status_code|key|value|
  |"true"  |/passenger/profile.info|200        |first_name   |Auto|
  |false |/passenger/profile.info|401        |error.message|Authentication key: 'false' is incorrect.|
  |"father"|/passenger/profile.info|404        |error.message|User with ID. '1' not found in system. |


  Scenario Outline: passenger/profile.edit
    Given sending passenger/profile.edit request using <token>, <photo>, <first_name>, <last_name>, <email>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|photo|first_name|last_name|email|resource|status_code|key|value|
    |false|"base64"|"First"|"Last"|"buggserazzer@hotmail.com"|/passenger/profile.edit|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"First"|"Last"|"buggserazzer@hotmail.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'photo' are required.|
    |"true"|"base64"|""|"Last"|"buggserazzer@hotmail.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
    |"true"|"base64"|"First"|""|"buggserazzer@hotmail.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
    |"true"|"base64"|"First"|"Last"|""|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'email' are required.|
    |"true"|"base64"|"First"|"Last"|"123"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'email' are malformed or incorrect.|
    |"true"|"base64"|"First"|"Last"|"buggserazzer@hotmail.com"|/passenger/profile.edit|400|error.message|Email already exist in system.|


  Scenario Outline: /passenger/pass.change
    Given sending /passenger/pass.change request using <token>, <password>, <password_old>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|password|password_old|resource|status_code|key|value|
    |"true"|"newpass"|"passengerpass"|/passenger/pass.change|200|"success"|200|
    |"true"|"passengerpass"|"newpass"|/passenger/pass.change|200|"success"|200|
    |false|"passengerpass"|"newpass"|/passenger/pass.change|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|"passengerpass"|"newpass"|/passenger/pass.change|400|error.message|Old password is incorrect.|
    |"true"|"false"|"newpass"|/passenger/pass.change|400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|
    |"true"|"newpass"|"false"|/passenger/pass.change|400|error.message|Incorrect request body. Parameters: 'password_old' are malformed or incorrect.|
    |"true"|null|"passengerpass"|/passenger/pass.change|400|error.message|Incorrect request body. Parameters: 'password' are required.|
    |"true"|"passengerpass"|null|/passenger/pass.change|400|error.message|Incorrect request body. Parameters: 'password_old' are required.|
    |"true"|"passengerpass"|"passengerpass"|/passenger/pass.change|404|error.message|Old and new passwords are equal|











