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


  @NeedTo
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

    @NeedTo
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

