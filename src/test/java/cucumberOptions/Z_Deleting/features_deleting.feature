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
