Feature: admin_registration


  Scenario Outline: admin_add
    Given Sending request with correct token and using <first_name>, <last_name>, <phone_number>, <email>, <role_id>, <password> parameters
    When POST request send to resource <resource>
    Then Status-code "200" is received
    And Response contains id of admin

    Examples:
    |first_name|last_name|phone_number   |email             |password|resource        |role_id|
    |"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"|/admin/admin.add|1|



    Scenario: admin_authorization
      Given Sending request with generated API key for admin using
      |"maysalexandr@gmail.com"|"pass"|
      When POST request send to "/admin/authorization"
      Then Status-code "200" is received
      And Response contains authorization token


    Scenario: admin_password_recovery
      Given Sending request with generated API key for admin with
      |"maysalexandr@gmail.com"|
      When PUT request send to "/admin/password.recovery"
      Then Status-code "200" is received














