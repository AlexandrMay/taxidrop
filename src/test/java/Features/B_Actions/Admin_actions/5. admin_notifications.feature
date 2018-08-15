Feature: admin_notifications

  Scenario Outline: /admin/notifications.list
    Given sending /admin/notifications request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/notifications.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/notifications.list?amount=10|200|"total"|"fromDB"|
    |"true"|/admin/notifications.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/admin/notifications.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/notification.info
    Given sending /admin/notifications request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/notification.info/1|200|"id"|1|
    |"true"|/admin/notification.info/0|404|error.message|Notification with ID '0' not found.|
    |false|/admin/notification.info/1|401|error.message|Authentication key: 'false' is incorrect.|


  Scenario Outline: /admin/notification.status
    Given sending /admin/notification.status request using <token>, <notification_status>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|notification_status|resource|status_code|key|value|
    |"true"|0                  |/admin/notification.status/1|409|error.message|Notification already have this status.|
    |false|1                  |/admin/notification.status/1|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|1                  |/admin/notification.status/1|200|"success"|200|
    |"true"|2                  |/admin/notification.status/1|200|"success"|200|
    |"true"|3                  |/admin/notification.status/1|400|error.message|Incorrect request body. Parameters: 'status' are malformed or incorrect.|
    |"true"|1                  |/admin/notification.status/1|400|error.message|Notification status can't come back.|



  Scenario: set 0 to notification status
    Given sending /admin/notification.status
    When PUT request /admin/notification.status is sent
    Then Set 0 status to this notification from DB after
    |error.message|Notification status can't come back.|

  Scenario Outline: /admin/notification.answer
    Given sending /admin/notification.answer request using <token>, <text>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|text|resource|status_code|key|value|
    |"true"|"Some text 123"|/admin/notification.answer/1|200|"success"|200|
    |"true"|"Some text 123"|/admin/notification.answer/0|404|error.message|Notification with ID '0' not found.|
    |"true"|""|/admin/notification.answer/1|400|error.message|Incorrect request body. Parameters: 'text' are required.|
    |false|"Some text 123"|/admin/notification.answer/1|401|error.message|Authentication key: 'false' is incorrect.|













