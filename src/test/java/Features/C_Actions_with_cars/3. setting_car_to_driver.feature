Feature: setting_car_to_driver

  Scenario Outline: /driver/car.request
    Given sending /driver/car using <token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/driver/car.request|200|"success"|200|
    |"true"|/driver/car.request|409|error.message|Not processed request already exist.|
    |false|/driver/car.request|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario: admin/notification.status
    Given sending admin/notification.status request with status "2"
    When PUT admin/notification.status request with notification_type "15" is sent
    Then Status_code is 200
    And Notification has "2" status in DB

   Scenario Outline: /passenger/driver.proposal
     Given sending /passenger/driver using <token>
     When POST request send to <resource>
     Then Statuscode <status_code> is received
     And Response contains <key> and <value>
     Examples:
     |token|resource|status_code|key|value|
     |"true"|/passenger/driver.proposal/1/2|200|"success"|200|
     |"true"|/passenger/driver.proposal/1/2|409|error.message|Car proposal already sent to the driver with ID '1'|
     |"true"|/passenger/driver.proposal/0/2|404|error.message|Driver user with ID. '0' not found in system.|
     |"true"|/passenger/driver.proposal/1/0|404|error.message|Car with ID '0' does not exist.|
     |false|/passenger/driver.proposal/1/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/car.accept
    Given sending /driver/car using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/driver/car.accept/2|200|"success"|200|
    |"true"|/driver/car.accept/2|404|error.message|Proposal to car with ID '2' does not exist.|
    |false|/driver/car.accept/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/car.change
    Given sending /driver/car using <token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/driver/car.change/0|404|error.message|Car with ID. '0' not found in system.|
    |"true"|/driver/car.change/2|200|"success"|200|
    |"true"|/driver/car.change/2|409|error.message|Not processed request already exist.|
    |false|/driver/car.change/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario: admin/notification.status
    Given sending admin/notification.status request with status "2"
    When PUT admin/notification.status request with notification_type "16" is sent
    Then Status_code is 200
    And Notification has "2" status in DB

  Scenario Outline: /driver/car.broken
    Given sending /driver/car using <token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/car.broken/2|200|"success"|200|
      |"true"|/driver/car.broken/2|409|error.message|Сar with ID '2' already on broken status.|
      |"true"|/driver/car.broken/0|404|error.message|Car with ID '0' does not exist.|
      |false|/driver/car.broken/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/driver.release
    Given sending /passenger/driver using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/driver.release/1/2|200|"success"|200|
    |"true"|/passenger/driver.release/1/2|400|error.message|Driver with ID '1' is not assigned to this car.|
    |"true"|/passenger/driver.release/0/2|404|error.message|Driver user with ID. '0' not found in system.|
    |"true"|/passenger/driver.release/1/0|404|error.message|Car with ID '0' does not exist.|
    |false|/passenger/driver.release/1/2|401|error.message|Authentication key: 'false' is incorrect.|

    Scenario Outline: remove changes to car in DB
      Given sending /driver/car using <token>
      When POST request send to <resource>
      Then Statuscode <status_code> is received
      And Response contains <key> and <value>
      And Remove broken status from car
      Examples:
        |token|resource|status_code|key|value|
        |"true"|/driver/car.broken/2|404|error.message|Car with ID '2' does not exist.|

  Scenario Outline: /passenger/driver.proposal
    Given sending /passenger/driver using <token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/passenger/driver.proposal/1/2|200|"success"|200|

  Scenario Outline: /driver/car.decline
    Given sending /driver/car using <token>
    When DELETE request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/driver/car.decline/2|200|"success"|200|
    |"true"|/driver/car.decline/2|404|error.message|Proposal to car with ID '2' does not exist.|
    |false|/driver/car.decline/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/driver.search
    Given sending /passenger/driver using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/driver.search contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/driver.search?amount=100|200|users.id|"fromDB"|
    |"true"|/passenger/driver.search?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/passenger/driver.search?amount=100|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/driver.info
    Given sending /passenger/driver using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/driver.info/1|200|"id"|1|
    |"true"|/passenger/driver.info/0|404|error.message|Driver user with ID. '0' not found in system.|
    |false |/passenger/driver.info/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/driver.set
    Given sending /admin/driver using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/driver.set/2/1|200  |"success"|200|
    |"true"|/admin/driver.set/2/1|409  |error.message|Driver with ID '1' already sent car request.|
    |"true"|/admin/driver.set/0/1|404  |error.message|User with ID. '0' not found in system.|
    |"true"|/admin/driver.set/2/0|404  |error.message|Driver user with ID. '0' not found in system.|
    |false|/admin/driver.set/2/1|401  |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/driver.decline
    Given sending /passenger/driver using <token>
    When DELETE request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/driver.decline/1|200|"success"|200|
    |"true"|/passenger/driver.decline/1|404|error.message|Proposal from driver with ID '1' not found.|
    |"true"|/passenger/driver.decline/0|404|error.message|Driver user with ID. '0' not found in system.|
    |false|/passenger/driver.decline/1|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: set the car to driver from DB
    Given sending /driver/car using <token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    And car is mocked to driver from DB
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/car.broken/2|404|error.message|Car with ID '2' does not exist.|

  
  Scenario Outline: /admin/driver.unset
    Given sending /admin/driver using <token>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/admin/driver.unset/2/1|200  |"success"|200|
      |"true"|/admin/driver.unset/2/1|400  |error.message|Driver with ID '1' is not assigned to this car.|
      |"true"|/admin/driver.unset/0/1|404  |error.message|Car with ID '0' does not exist.|
      |"true"|/admin/driver.unset/2/0|404  |error.message|Driver user with ID. '0' not found in system.|
      |false|/admin/driver.unset/2/1|401|error.message|Authentication key: 'false' is incorrect.|


























