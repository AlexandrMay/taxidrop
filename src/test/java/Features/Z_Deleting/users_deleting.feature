Feature: users deleting

  Scenario Outline: /admin/driver.delete
    Given sending /admin/driver.delete using <token>
    When DELETE request send to <resource> with <driver_id>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|driver_id|status_code|key|value|
      |"true"|/admin/driver.delete/|24|200 |"success"|200|
      |"true"|/admin/driver.delete/|0|404 |error.message|Driver user with ID. '0' not found in system.|
      |false|/admin/driver.delete/|24|401 |error.message|Authentication key: 'false' is incorrect.|

  Scenario: deleting admin
    Given Sending request to delete admin
    When DELETE request send
    Then Status_code is 200


  Scenario Outline: admin/passenger.delete
    Given sending /admin/passenger.delete using <token>
    When DELETE request send to <resource> with <driver_id>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|driver_id|status_code|key|value|
      |"true"|/admin/passenger.delete/|24|200 |"success"|200|
      |"true"|/admin/passenger.delete/|0|404 |error.message|Driver user with ID. '0' not found in system.|
      |false|/admin/passenger.delete/|24|401 |error.message|Authentication key: 'false' is incorrect.|
