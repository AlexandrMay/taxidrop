Feature: passenger_address_and_route
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


  @NeedTo
  Scenario Outline: /passenger/route.add
    Given sending /passenger/route.add request using <token>, <polyline>, <street>, <street_number>, <lat>, <lng>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/route.add contains <key> and <value>
    Examples:
    |token|polyline|street|street_number|lat|lng|resource|status_code|key|value|
    |"true"|"auto line"|"aoto street"|"auto number"|55.236546|44.123456|/passenger/route.add|200|"route_id"|"fromDB"|
    |"true"|""|"aoto street"|"auto number"|55.236546|44.123456|/passenger/route.add|400|error.message|Incorrect request body. Parameters: 'route' are malformed or incorrect.|
    |false|"auto line"|"aoto street"|"auto number"|55.236546|44.123456|/passenger/route.add|401|error.message|Authentication key: 'false' is incorrect.|
  @NeedTo
  Scenario Outline: /passenger/route.edit
    Given sending /passenger/route.add request using <token>, <polyline>, <street>, <street_number>, <lat>, <lng>
    When When PUT /passenger/route.edit request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|lat|lng|polyline|street|street_number|resource|id|status_code|key|value|
    |"true"|44.456|55.123|"new line"|"new street"|"new number"|/passenger/route.edit/|"routeId"|200|"success"|200|
    |"true"|44.456|55.123|"new line"|"new street"|"new number"|/passenger/route.edit/|0|404|error.message|Route with ID '0' not found.|
    |"true"|44.456|55.123|""|"new street"|"new number"|/passenger/route.edit/|"routeId"|400|error.message|Incorrect request body. Parameters: 'route' are malformed or incorrect.|
    |false|44.456|55.123|"new line"|"new street"|"new number"|/passenger/route.edit/|"routeId"|401|error.message|Authentication key: 'false' is incorrect.|
  @NeedTo
  Scenario Outline: /passenger/route.list
    Given sending /passenger/route request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/route.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/route.list?amount=100|200|"id"|"fromDB"|
    |"true"|/passenger/route.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/passenger/route.list?amount=100|401|error.message|Authentication key: 'false' is incorrect.|
  @NeedTo
  Scenario Outline: /passenger/route.info
    Given sending /passenger/route request using <token>
    When GET /passenger/route.info request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response /passenger/route.info contains <key> and <value>
    Examples:
    |token|resource|id|status_code|key|value|
    |"true"|/passenger/route.info/|"routeId"|200|"id"|"routeId"|
    |"true"|/passenger/route.info/|0|404|error.message|Route with ID '0' not found.|
    |false|/passenger/route.info/|"routeId"|401|error.message|Authentication key: 'false' is incorrect.|











