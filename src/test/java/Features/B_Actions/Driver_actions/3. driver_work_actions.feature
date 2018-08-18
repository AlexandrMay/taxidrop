Feature: driver_work_actions
  @Go
  Scenario Outline: /driver/work.cars
    Given sending /driver/work request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /driver/work.cars contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/driver/work.cars|200|"id"|"fromDB"|
    |false|/driver/work.cars|401|error.message|Authentication key: 'false' is incorrect.|
  @Go
  Scenario Outline: /driver/work.radius
    Given sending /driver/work.radius request using <token>, <radius>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|radius|resource|status_code|key|value|
      |"true"|"1"|/driver/work.radius|200|"success"|200|
      |false|"1"|/driver/work.radius|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|"123"|/driver/work.radius|400|error.message|Incorrect request body. Parameters: 'radius' are malformed or incorrect.|
      |"true"|""|/driver/work.radius|400|error.message|Incorrect request body. Parameters: 'radius' are required.|
  @Go
    Scenario: /driver/work.statistics
      Given sending /driver/work.statistics request
      When GET /driver/work.statistics request is sent
      Then Status_code is 200
      And Response contains data from DB
  @Go
  Scenario Outline: /driver/work/closing.destination
    Given sending /driver/work/closing.destination request using <token>, <lat>, <lng>, <address>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|lat|lng|address|resource|status_code|key|value|
    |"true"|"65.54"|"65.32"|"Auto address"|/driver/work/closing.destination|200|"success"|200|
    |"true"|""|"65.32"|"Auto address"|/driver/work/closing.destination|400|error.message|Incorrect request body. Parameters: 'lat' are required.|
    |"true"|"65.54"|""|"Auto address"|/driver/work/closing.destination|400|error.message|Incorrect request body. Parameters: 'lng' are required.|
    |"true"|"65.54"|"65.32"|""|/driver/work/closing.destination|400|error.message|Incorrect request body. Parameters: 'address' are required.|
    |false|"65.54"|"65.32"|"Auto address"|/driver/work/closing.destination|401|error.message|Authentication key: 'false' is incorrect.|





