Feature: driver_notifications

  Scenario Outline: /driver/notification.info
    Given sending /driver/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/notification.info/1|200|"id"|1|
      |"true"|/driver/notification.info/0|404|error.message|Notification with ID '0' not found.|
      |false |/driver/notification.info/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/notification.list
    Given sending /driver/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /driver/notification.list contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/notification.list?amount=100|200|"id"|"fromDB"|
      |"true"|/driver/notification.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
      |false |/driver/notification.list?amount=100|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/notification.count
    Given sending /driver/notification request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /driver/notification.count contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/notification.count|200|"count"|"fromDB"|
      |false |/driver/notification.count|401|error.message|Authentication key: 'false' is incorrect.|