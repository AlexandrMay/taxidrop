Feature: passenger_notifications

  Scenario Outline: /passenger/notification.info
    Given sending /passenger/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/notification.info/2|200|"id"|2|
    |"true"|/passenger/notification.info/0|404|error.message|Notification with ID '0' not found.|
    |false |/passenger/notification.info/2|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/notification.list
    Given sending /passenger/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/notification.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/notification.list?amount=100|200|"id"|"fromDB"|
    |"true"|/passenger/notification.list?amount=0  |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false |/passenger/notification.list?amount=100|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /passenger/notification.count
    Given sending /passenger/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /passenger/notification.count contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/passenger/notification.count|200|"count"|"fromDB"|
    |false |/passenger/notification.count|401|error.message|Authentication key: 'false' is incorrect.|



    

