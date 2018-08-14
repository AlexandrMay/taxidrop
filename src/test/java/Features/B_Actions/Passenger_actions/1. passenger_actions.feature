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
    |false|"base64"|"First"|"Last"|"test@test.com"|/passenger/profile.edit|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"First"|"Last"|"test@test.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'photo' are required.|
    |"true"|"base64"|""|"Last"|"test@test.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
    |"true"|"base64"|"First"|""|"test@test.com"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
    |"true"|"base64"|"First"|"Last"|""|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'email' are required.|
    |"true"|"base64"|"First"|"Last"|"123"|/passenger/profile.edit|400|error.message|Incorrect request body. Parameters: 'email' are malformed or incorrect.|
    |"true"|"base64"|"First"|"Last"|"test@test.com"|/passenger/profile.edit|400|error.message|Update data error: 'Email already used.'|


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
    |"true"|"passengerpass"|"passengerpass"|/passenger/pass.change|404|error.code|77|





@NeedTo
  Scenario Outline: /passenger/address.add
    Given sending /passenger/address.add request using <token>, <address>, <name>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/address.add contains <key> and <value>
    Examples:
    |token|address|name|resource|status_code|key|value|
    |"true"|"Idea st."|"Bot’s address"|/passenger/address.add|200|"address_id"|"fromBD"|
    |false|"Idea st."|"Bot’s address"|/passenger/address.add|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"Bot’s address"|/passenger/address.add|400|error.message|Incorrect request body. Parameters: 'address' are required.|
    |"true"|"Idea st."|""|/passenger/address.add|400|error.message|Incorrect request body. Parameters: 'name' are required.|
  @NeedTo
  Scenario Outline: /passenger/address.edit
    Given sending /passenger/address.edit request using <token>, <address>, <name>
    When PUT /passenger/address.edit request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|address|name|resource|id|status_code|key|value|
    |"true"|"New Idea st."|"New Bot’s address"|/passenger/address.edit/|"addressId"|200|"success"|200|
    |"true"|"New Idea st."|"New Bot’s address"|/passenger/address.edit/|0|404|error.message|Address with ID '0' not found.|
    |"true"|""|"New Bot’s address"|/passenger/address.edit/|"addressId"|400|error.message|Incorrect request body. Parameters: 'address' are required.|
    |"true"|"New Idea st."|""|/passenger/address.edit/|"addressId"|400|error.message|Incorrect request body. Parameters: 'name' are required.|
    |false|"New Idea st."|"Bot’s address"|/passenger/address.edit/|"addressId"|401|error.message|Authentication key: 'false' is incorrect.|
  @NeedTo
  Scenario Outline: /passenger/address.list
    Given sending /passenger/address request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/address.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/address.list?amount=10|200|"id"|"addressId"|
    |false|/passenger/address.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|/passenger/address.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
  @NeedTo
  Scenario Outline: /passenger/address.info
    Given sending /passenger/address request using <token>
    When GET /passenger/address.info request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response /passenger/address.info contains <key> and <value>
    Examples:
    |token|resource|id|status_code|key|value|
    |"true"|/passenger/address.info/|"addressId"|200|"id"|"addressId"|
    |"true"|/passenger/address.info/|0|404|error.message|Address with ID '0' not found.|
    |false|/passenger/address.info/|"addressId"|401|error.message|Authentication key: 'false' is incorrect.|





























