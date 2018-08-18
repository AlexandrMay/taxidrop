Feature: features_deleting

  Scenario Outline: /admin/role.delete
    Given sending /admin/role.delete request using <token>
    When DELETE request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|id|status_code|key|value|
      |"true"|/admin/role.delete/|"roleID"|200|"success"|200|
      |"true"|/admin/role.delete/|0|404|error.message|Role with ID '0' does not exist.|
      |false|/admin/role.delete/|"roleID"|401|error.message|Authentication key: 'false' is incorrect.|



  Scenario Outline: /passenger/route.delete
    Given sending /passenger/route.delete request using <token>
    When DELETE /passenger/route.delete with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|id|status_code|key|value|
      |"true"|/passenger/route.delete/|"routeId"|200|"success"|200|
      |"true"|/passenger/route.delete/|0|404|error.message|Route with ID '0' not found.|
      |false|/passenger/route.delete/|"routeId"|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: /passenger/address.delete
    Given sending /passenger/route.delete request using <token>
    When DELETE /passenger/address.delete with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|id|status_code|key|value|
      |"true"|/passenger/address.delete/|"addressId"|200|"success"|200|
      |"true"|/passenger/address.delete/|0|404|error.message|Address with ID '0' not found.|
      |false|/passenger/address.delete/|"addressId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/car.delete
    Given sending /passenger/car.delete request using <token>
    When DELETE /passenger/car.delete request with <resource> and <car_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|car_id|resource|status_code|key|value|
      |"true"|"id1"|/passenger/car.delete/|200|"success"|200|
      |"true"|"id2"|/passenger/car.delete/|200|"success"|200|
      |"true"|0|/passenger/car.delete/|404|error.message|Car with ID '0' does not exist.|
      |false|"true"|/passenger/car.delete/|401|error.message|Authentication key: 'false' is incorrect.|
@Go
  Scenario Outline: /passenger/bank.delete
    Given sending /passenger/bank.delete request using <token>
    When DELETE /passenger/bank.delete request with <resource> and <bank_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|bank_id|status_code|key|value|
    |"true"|/passenger/bank.delete/|"bank_id"|200|"success"|200|
    |"true"|/passenger/bank.delete/|0|400|error.message|Bank with ID '0' does not exist.|
    |false|/passenger/bank.delete/|"bank_id"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/bank.delete
    Given sending /driver/bank.delete request using <token>
    When DELETE /driver/bank.delete request with <resource> and <bank_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|bank_id|status_code|key|value|
      |"true"|/passenger/bank.delete/|"bank_id"|200|"success"|200|
      |"true"|/passenger/bank.delete/|0|400|error.message|Bank with ID '0' does not exist.|
      |false|/passenger/bank.delete/|"bank_id"|401|error.message|Authentication key: 'false' is incorrect.|




