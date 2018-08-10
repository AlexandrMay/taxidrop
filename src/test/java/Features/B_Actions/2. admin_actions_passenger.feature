Feature: admin_actions_passenger

  Scenario: /admin/passengers.list with DB
    Given sending /admin/passengers.list
    When GET request "/admin/passengers.list?amount=10" is sent
    Then Status_code is 200
    And count of passengers equals to DB

Scenario Outline: /admin/passengers.list
  Given sending request using <token>
  When GET request send to <resource>
  Then Statuscode <status_code> is received
  And Response contains <key> and <value>
  Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/passengers.list?amount=0 |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false |/admin/passengers.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/passenger.get
    Given sending request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/passenger.get/2|200|"id"|2|
      |"true"|/admin/passenger.get/0|404|error.message|User with ID. '0' not found in system.|
      |false |/admin/passenger.get/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/passenger.edit
    Given sending /admin/passenger.edit using <token>, <first_name>, <last_name>, <email>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|first_name|last_name|email|status_code|key|value|
      |"true"|/admin/passenger.edit/2|"some"|"passenger"|"test@test.com"|200|"success"|200|
      |"true"|/admin/passenger.edit/27|"some"|"passenger"|"test@test.com"|409|error.message|Email already used.|
      |false|/admin/passenger.edit/2|"some"|"passenger"|"test@test.com"|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|/admin/passenger.edit/0|"some"|"passenger"|"test@test.com"|404|error.message|User with ID. '0' not found in system.|
      |"true"|/admin/passenger.edit|"some"|"passenger"|"test@test.com"|404|"message"|Requested resource were not found at given endpoint.|
      |"true"|/admin/passenger.edit/2|""|"passenger"|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
      |"true"|/admin/passenger.edit/2|"some"|""|"test@test.com"|400|error.message|Incorrect request body. Parameters: 'last_name' are required.  |
      |"true"|/admin/passenger.edit/2|"some"|"passenger"|""|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"true"|/admin/passenger.edit/2|"some"|"passenger"|"123"|400|error.message|Incorrect request body. Parameters: 'email' are malformed or incorrect.|

  Scenario Outline: /admin/passenger.block/
    Given sending request using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/passenger.block/2|200|"success"|200|
      |"true"|/admin/passenger.block/2|400|error.message|User already have this status.|
      |"true"|/admin/passenger.block/0|404|error.message|User with ID. '0' not found in system.|
      |false|/admin/passenger.block/1|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/passenger.invite/
    Given sending request using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/passenger.invite/2|200|"success"|200|
      |"true"|/admin/passenger.invite/2|400|error.message|User already have this status.|
      |"true"|/admin/passenger.invite/0|404|error.message|User with ID. '0' not found in system.|
      |false|/admin/passenger.invite/1|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/passenger.activate
    Given sending request using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/passenger.activate/2|200|"success"|200|
      |"true"|/admin/passenger.activate/2|400|error.message|User already have this status.|
      |"true"|/admin/passenger.activate/0|404|error.message|User with ID. '0' not found in system.|
      |false|/admin/passenger.activate/2|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario: /admin/owner.applications with DB
    Given sending /admin/owner.applications
    When GET request "/admin/owner.applications?amount=10" is sent
    Then Status_code is 200
    And count of owner_applications equals to DB

  Scenario Outline: /admin/owner.applications
    Given sending request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/owner.applications?amount=0 |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
      |false |/admin/owner.applications?amount=10|401|error.message|Authentication key: 'false' is incorrect.|


