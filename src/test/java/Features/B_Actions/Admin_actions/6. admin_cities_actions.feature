Feature: admin_cities_actions

  Scenario Outline: /admin/city.list
    Given sending /admin/city request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /admin/city.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/city.list?amount=100|200|cities.id|"fromDB"|
    |"true"|/admin/city.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/admin/city.list?amount=100|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/city.info
    Given sending /admin/city request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/city.info/1|200|"id"|1|
    |"true"|/admin/city.info/0|404|error.message|City with ID. '0' not found in system.|
    |false|/admin/city.info/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/city.add
    Given sending /admin/city.add request using <token>, <name>, <enabled>, <country_id>, <lat>, <lng>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/city.add contains <key> and <value>
    Examples:
      |token|name|enabled|country_id|lat|lng|resource|status_code|key|value|
      |"true"|"Auto city"|1|1       |55.2356|99.2569|/admin/city.add|200|"city_id"|"fromDB_1"|
      |"true"|"Auto city"|0|1       |55.2356|99.2569|/admin/city.add|200|"city_id"|"fromDB_2"|
      |false|"Auto city"|1|1       |55.2356|99.2569|/admin/city.add|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|""|1|1       |55.2356|99.2569|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'name' are required.|
      |"true"|"Auto city"|-1|1       |55.2356|99.2569|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'enabled' are required.|
      |"true"|"Auto city"|2|1       |55.2356|99.2569|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'enabled' are malformed or incorrect.|
      |"true"|"Auto city"|1|-1       |55.2356|99.2569|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'country_id' are required.|
      |"true"|"Auto city"|1|0      |55.2356|99.2569|/admin/city.add|404|error.message|Country with ID. '0' not found in system.|
      |"true"|"Auto city"|1|1      |-1|99.2569|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'coords' are malformed or incorrect.|
      |"true"|"Auto city"|1|1      |55.2356|-1|/admin/city.add|400|error.message|Incorrect request body. Parameters: 'coords' are malformed or incorrect.|

  Scenario Outline: /admin/city.edit
    Given sending /admin/city.add request using <token>, <name>, <enabled>, <country_id>, <lat>, <lng>
    When PUT /admin/city.edit request with <resource> and <city_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|name|enabled|country_id|lat|lng|resource|city_id|status_code|key|value|
    |"true"|"New City"|1|1        |44.12345|78.12345|/admin/city.edit/|"cityID"|200|"success"|200|
    |false|"New City"|1|1        |44.12345|78.12345|/admin/city.edit/|"cityID"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|1|1        |44.12345|78.12345|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'name' are required.|
    |"true"|"New City"|-1|1        |44.12345|78.12345|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'enabled' are required.|
    |"true"|"New City"|2|1        |44.12345|78.12345|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'enabled' are malformed or incorrect.|
    |"true"|"New City"|1|-1        |44.12345|78.12345|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'country_id' are required.|
    |"true"|"New City"|1|0        |44.12345|78.12345|/admin/city.edit/|"cityID"|404|error.message|Country with ID. '0' not found in system.|
    |"true"|"New City"|1|1        |-1|78.12345|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'coords' are malformed or incorrect.|
    |"true"|"New City"|1|1        |44.12345|-1|/admin/city.edit/|"cityID"|400|error.message|Incorrect request body. Parameters: 'coords' are malformed or incorrect.|

  Scenario Outline: /admin/city.delete
    Given sending /admin/city request using <token>
    When DELETE /admin/city.delete request with <resource> and <city_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|city_id|status_code|key|value|
    |"true"|/admin/city.delete/|"city_id_1"|200|"success"|200|
    |"true"|/admin/city.delete/|"city_id_2"|200|"success"|200|
    |"true"|/admin/city.delete/|0|404|error.message|City with ID. '0' not found in system.|
    |false|/admin/city.delete/|1|401|error.message|Authentication key: 'false' is incorrect.|




