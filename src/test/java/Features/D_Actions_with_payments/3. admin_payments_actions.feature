Feature: admin_payments_actions

  Scenario Outline: /admin/passenger/payment.list
    Given sending /admin/request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/passenger/payment.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/passenger/payment.list?amount=100|200|payments.id|"fromDB"|

