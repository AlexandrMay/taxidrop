Feature: admin_actions

  Scenario: /admin/drivers.list with DB
    Given sending /admin/drivers.list
    When GET request drivers.list is sent
    Then Status_code is 200
    And count of drivers equals to DB


Scenario Outline: /admin/drivers.list
  Given sending /admin/drivers.list using <token>
  When GET request send to <resource>
  Then Statuscode <status_code> is received
  And Response contains <key> and <value>
  Examples:
  |token|resource|status_code|key|value|
  |"true"|/admin/drivers.list?amount=0 |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
  |false |/admin/drivers.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: /admin/driver.get
    Given sending /admin/driver.get using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/driver.get/1|200|user.id|1|
      |"true"|/admin/driver.get/0|404|error.message|Driver user with ID. '0' not found in system.|
      |false |/admin/driver.get/1|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: /admin/driver.edit
    Given sending /admin/driver.edit using <token>, <first_name>, <last_name>, <email>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|first_name|last_name|email|status_code|key|value|
      |"true"|/admin/driver.edit/1|"First"|"Driver"|"test@test.com"|200|"success"|200|
      |"true"|/admin/driver.edit/3|"First"|"Driver"|"test@test.com"|409|error.message|Email already used.|
      |false|/admin/driver.edit/1|"First"|"Driver"|"test@test.com"|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|/admin/driver.edit/0|"First"|"Driver"|"test@test.com"|404|error.message|Driver user with ID. '0' not found in system.|
      |"true"|/admin/driver.edit|"First"|"Driver"|"test@test.com"|404|"message"|Requested resource were not found at given endpoint.|
      |"true"|/admin/driver.edit/1|""|"Driver"|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
      |"true"|/admin/driver.edit/1|"First"|""|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'last_name' are required.  |
      |"true"|/admin/driver.edit/1|"First"|"Driver"|""|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"true"|/admin/driver.edit/1|"First"|"Driver"|"123"|400|error.message|Incorrect request body. Parameters: 'email' are malformed or incorrect.|


  Scenario Outline: /admin/driver.block
    Given sending /admin/driver.block using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/driver.block/1|200|"success"|200|
    |"true"|/admin/driver.block/1|400|error.message|User already have this status.|
    |"true"|/admin/driver.block/0|404|error.message|Driver user with ID. '0' not found in system.|
    |false|/admin/driver.block/1|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/driver.invite/
    Given sending request using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/driver.invite/1|200|"success"|200|
      |"true"|/admin/driver.invite/1|400|error.message|User already have this status.|
      |"true"|/admin/driver.invite/0|404|error.message|Driver user with ID. '0' not found in system.|
      |false|/admin/driver.invite/1|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/driver.activate
    Given sending /admin/driver.activate using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/driver.activate/1|200|"success"|200|
      |"true"|/admin/driver.activate/1|400|error.message|User already have this status.|
      |"true"|/admin/driver.activate/0|404|error.message|Driver user with ID. '0' not found in system.|
      |false|/admin/driver.activate/1|401 |error.message|Authentication key: 'false' is incorrect.|


  Scenario: /admin/driver.applications with DB
    Given sending /admin/driver.applications
    When GET request /admin/driver.applications is sent
    Then Status_code is 200
    And count of applications equals to DB


  Scenario Outline: /admin/driver.applications
    Given sending /admin/driver.applications using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/driver.applications?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/admin/driver.applications?amount=10|401 |error.message|Authentication key: 'false' is incorrect.|




