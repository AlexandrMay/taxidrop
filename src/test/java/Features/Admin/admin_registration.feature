Feature: admin_registration


  Scenario Outline: admin_add
    Given Sending request with correct token and using <first_name>, <last_name>, <phone_number>, <email>, <role_id>, <password> parameters
    When POST request send to resource <resource>
    Then Status-code "200" is received
    And Response contains id of admin

    Examples:
    |first_name|last_name|phone_number   |email             |password|resource        |role_id|
    |"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"|/admin/admin.add|1|



    Scenario Outline: admin_add with incorrect token
      Given Sending request with incorrect token and parameters <first_name>, <last_name>, <phone_number>, <email>, <role_id>, <password>
      When POST request send to "/admin/admin.add"
      Then Status-code "401" is received
      And Response contains error
      |error.message|Authentication key: '1eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpIjoxLCJzIjoxfQ.N-pQ1zdrISWDGKuJUczZegnhuFKkB2e7qAqEh3f_Ge0' is incorrect.|
      Examples:
        |first_name|last_name|phone_number   |email             |password|role_id|
        |"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"|1|


      Scenario Outline: admin_add errors
        Given Sending request with correct token and using <first_name>, <last_name>, <phone_number>, <email>, <role_id>, <password> parameters
        When POST request send to resource <resource>
        Then Statuscode <status-code> is received
        And Response contains <error_key> and <error_text>
        Examples:
          |first_name|last_name|phone_number   |email             |password|resource        |role_id|status-code|error_key|error_text|
          |""    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"|/admin/admin.add| 1  |400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
          |"Auto"    |"Bot"    |"+380664853399"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.add| 1  |409|error.message|Phone number already used.|
          |"Auto"    |"Bot"    |"+380664853397"|"maysalexandr@gmail.com"|"pass"|/admin/admin.add| 1  |409|error.message|Email already used.|
          |"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.add| 10  |404|error.message|Role with ID '10' does not exist.|
          |"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.ad| 1  |404|message|Requested resource were not found at given endpoint.|
          |"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"anypass"|/admin/admin.add| 1  |400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|

  Scenario: admin_authorization
      Given Sending request with generated API key for admin using
      |"maysalexandr@gmail.com"|"pass"|
      When POST request send to "/admin/authorization"
      Then Status-code "200" is received
      And Response contains authorization token

    Scenario Outline: admin_authorization errors
      Given Sending request with <admin_token> using <email> and <password>
      When POST request send to resource <resource>
      Then Statuscode <status-code> is received
      And Response contains <error_key> and <error_text>
      Examples:
      |admin_token|email|password|resource|status-code|error_key|error_text|
      |"wrong"    |"maysalexandr@gmail.com"|"pass"|/admin/authorization|412|error.message|Incorrect request headers. Headers: 'Key' are malformed or incorrect.|
      |7365cddb0b1608adb153cc856a82384e|"maysalexandr@gmail.com"|"pass"|/admin/authorization|401|error.message|Either your user session has expired, or your access credentials is malformed.|
      |"right"    |""|"pass"|/admin/authorization|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"right"    |"mail@mail.com"|"pass"|/admin/authorization|404|error.message|User with email 'mail@mail.com' not found.|
      |"right"    |"maysalexandr@gmail.com"|"pass"|/admin/authorizations|404|message|Requested resource were not found at given endpoint.|
      |"right"    |"maysalexandr@gmail.com"|"wrong"|/admin/authorization|400|error.message|Password is incorrect.|
      |"right"    |"maysalexandr@gmail.com"|""|/admin/authorization|400|error.message|Incorrect request body. Parameters: 'password' are required.|


  Scenario: admin_password_recovery
      Given Sending request with generated API key for admin with
      |"maysalexandr@gmail.com"|
      When PUT request send to "/admin/password.recovery"
      Then Status-code "200" is received


    Scenario Outline: admin_password_recovery errors
      Given Sending request with <admin_token> using <email>
      When PUT request send to resource <resource>
      Then Statuscode <status-code> is received
      And Response contains <error_key> and <error_text>
      Examples:
      |admin_token|email|resource|status-code|error_key|error_text|
      |"wrong"    |"maysalexandr@gmail.com"|/admin/password.recovery|412|error.message|Incorrect request headers. Headers: 'Key' are malformed or incorrect.|
      |7365cddb0b1608adb153cc856a82384e|"maysalexandr@gmail.com"|/admin/password.recovery|401|error.message|Either your user session has expired, or your access credentials is malformed.|
      |"right"    |""|/admin/password.recovery|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"right"    |"mail@mail.com"|/admin/password.recovery|404|error.message|User with email 'mail@mail.com' not found.|
      |"right"    |"maysalexandr@gmail.com"|admin/password.recoveries|404|message|Requested resource were not found at given endpoint.|


