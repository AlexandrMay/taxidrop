Feature: admin_permissions

  Scenario: /admin/role.add
    Given sending /admin/role request with data
    |"AutoRole"|"statistics"|"users"|
    When POST request "/admin/role.add" is sent
    Then Status_code is 200
    And response equals id of role that equals to id in DB

    Scenario Outline: /admin/role.add with errors
      Given sending /admin/role.add request using <token>, <name>, <permission1>, <permission2>
      When POST request send to <resource>
      Then Statuscode <status_code> is received
      And Response contains <key> and <value>
      Examples:
      |token|name|permission1|permission2|resource|status_code|key|value|
      |"true"|null|"users"|"notifications"|/admin/role.add|400 |error.message|Incorrect request body. Parameters: 'name' are required.|
      |"true"|"Test"|"empty"|"notifications"|/admin/role.add|400 |error.message|Incorrect request body. Parameters: 'permissions' are required.|
      |"true"|"Test"|"user"|"notifications"|/admin/role.add|400 |error.message|Incorrect request body. Parameters: 'permissions' are malformed or incorrect.|
      |false|"Test" |"users"|"notifications"|/admin/role.add|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/role.edit with errors
    Given sending /admin/role.edit request using <token>, <name>, <permission1>, <permission2>
    When PUT request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|name|permission1|permission2|resource|id|status_code|key|value|
      |"true"|"Test"|"users"|"notifications"|/admin/role.edit/|"roleID"|200|"success"|200|
      |"true"|""|"users"|"notifications"|/admin/role.edit/|"roleID"|400 |error.message|Incorrect request body. Parameters: 'name' are required.|
      |"true"|"Test"|"empty"|"notifications"|/admin/role.edit/|"roleID"|400 |error.message|Incorrect request body. Parameters: 'permissions' are required.|
      |"true"|"Test"|"user"|"notifications"|/admin/role.edit/|"roleID"|400 |error.message|Incorrect request body. Parameters: 'permissions' are malformed or incorrect.|
      |"true"|"Test"|"users"|"notifications"|/admin/role.edit/|0|404 |error.message|Role with ID '0' does not exist.|
      |false|"Test" |"users"|"notifications"|/admin/role.edit/|"roleID"|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/role.get
    Given sending /admin/role.get request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/role.get/1|200|"id"|1 |
    |"true"|/admin/role.get/0|404|error.message|Role with ID '0' does not exist.|
    |false|/admin/role.get/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario: /admin/roles.list with DB
    Given sending /admin/roles.list
    When GET request "/admin/roles.list?amount=10" is sent
    Then Status_code is 200
    And count of roles equals to DB

  Scenario Outline: /admin/roles.list errors
    Given sending /admin/roles.list request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/roles.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
      |"true"|/admin/roles.list|400|error.message|Incorrect request body. Parameters: 'amount' are required.|
      |false|/admin/roles.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|















