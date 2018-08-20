Feature: admin_districts_actions

  Scenario Outline: /admin/district.list
    Given sending /admin/districts request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /admin/district.list contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/district.list?amount=100&status=0|200|districts.id|"fromDB_status_0"|
      |"true"|/admin/district.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
      |false|/admin/district.list?amount=100|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/district.info
    Given sending /admin/districts request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/district.info/1|200|"id"|1|
      |"true"|/admin/district.info/0|404|error.message|District with ID. '0' not found in system.|
      |false|/admin/district.info/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/district.add
    Given sending /admin/district.add request using <token>, <name>, <polygon>, <city_id>, <enabled>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/district.add contains <key> and <value>
    Examples:
    |token|name|polygon|city_id|enabled|resource|status_code|key|value|
    |"true"|"Auto district"|"Auto polygon"|1|1|/admin/district.add|200|"district_id"|"fromDB_1"|
    |"true"|"Auto district"|"Auto polygon"|1|0|/admin/district.add|200|"district_id"|"fromDB_2"|
    |false|"Auto district"|"Auto polygon"|1|0|/admin/district.add|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"Auto polygon"|1|1|/admin/district.add|400|error.message|Incorrect request body. Parameters: 'name' are required.|
    |"true"|"Auto district"|""|1|1|/admin/district.add|400|error.message|Incorrect request body. Parameters: 'polygon' are required.|
    |"true"|"Auto district"|"Auto polygon"|-1|1|/admin/district.add|400|error.message|Incorrect request body. Parameters: 'city_id' are required.|
    |"true"|"Auto district"|"Auto polygon"|0|1|/admin/district.add|404|error.message|City with ID. '0' not found in system.|
    |"true"|"Auto district"|"Auto polygon"|1|-1|/admin/district.add|400|error.message|Incorrect request body. Parameters: 'enabled' are required.|
    |"true"|"Auto district"|"Auto polygon"|1|2|/admin/district.add|400|error.message|Incorrect request body. Parameters: 'enabled' are malformed or incorrect.|

  Scenario Outline: /admin/district.edit
    Given sending /admin/district.add request using <token>, <name>, <polygon>, <city_id>, <enabled>
    When PUT /admin/district.edit request with <resource> and <district_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|name|polygon|city_id|enabled|resource|district_id|status_code|key|value|
    |"true"|"New district"|"New polygon"|1|1|/admin/district.edit/|"district_id"|200|"success"|200|
    |false|"New district"|"New polygon"|1|1|/admin/district.edit/|"district_id"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|"New district"|"New polygon"|1|1|/admin/district.edit/|0|404|error.message|District with ID. '0' not found in system.|
    |"true"|""|"New polygon"|1|1|/admin/district.edit/|"district_id"|400|error.message|Incorrect request body. Parameters: 'name' are required.|
    |"true"|"New district"|""|1|1|/admin/district.edit/|"district_id"|400|error.message|Incorrect request body. Parameters: 'polygon' are required.|
    |"true"|"New district"|"New polygon"|-1|1|/admin/district.edit/|"district_id"|400|error.message|Incorrect request body. Parameters: 'city_id' are malformed or incorrect.|
    |"true"|"New district"|"New polygon"|0|1|/admin/district.edit/|"district_id"|404|error.message|City with ID. '0' not found in system.|
    |"true"|"New district"|"New polygon"|1|-1|/admin/district.edit/|"district_id"|400|error.message|Incorrect request body. Parameters: 'enabled' are required.|
    |"true"|"New district"|"New polygon"|1|2|/admin/district.edit/|"district_id"|400|error.message|Incorrect request body. Parameters: 'enabled' are malformed or incorrect.|

  Scenario Outline: district.disable
    Given sending /admin/districts request using <token>
    When PUT /admin/district.edit request with <resource> and <district_id> is sent
    Then Statuscode <status_code> is received
    And Response of /district.disable contains <key> and <value>
    Examples:
    |token|resource|district_id|status_code|key|value|
    |"true"|/admin/district.disable/|"district_id"|200|"success"|200|
    |"true"|/admin/district.disable/|"district_id"|404|error.message|"repeatError"|
    |"true"|/admin/district.disable/|0|404|error.message|District with ID. '0' not found in system.|
    |false|/admin/district.disable/|"district_id"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/district.delete
    Given sending /admin/districts request using <token>
    When DELETE /admin/district.delete request with <resource> and <district_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|district_id|status_code|key|value|
    |"true"|/admin/district.delete/|"district_id_1"|200|"success"|200|
    |"true"|/admin/district.delete/|"district_id_2"|200|"success"|200|
    |"true"|/admin/district.delete/|0|404|error.message|District with ID. '0' not found in system.|
    |false|/admin/district.delete/|1|401|error.message|Authentication key: 'false' is incorrect.|











