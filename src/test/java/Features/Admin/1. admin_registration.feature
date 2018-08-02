Feature: admin_registration


  Scenario: admin_add
    Given Sending admin_add request with
      |"base64"|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"|1|
    When POST request send with correct resource
    Then Status_code is 200
    And Response contains id of admin

@NeedTo
  Scenario Outline: admin_add with errors
    Given Sending admin_add request using <token>, <photo>, <first_name>, <last_name>, <phone_number>, <email>, <role_id>, <password> parameters
    When POST request send to <resource>
    Then Statuscode <status-code> is received
    And Response contains <key> and <value>
    Examples:
     |token |photo   |first_name|last_name|phone_number   |email                   |password|resource        |role_id|status-code|key          |value|
     |false |"base64"|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add| 1     |401        |error.message|Authentication key: 'false' is incorrect.|
     |"true" |""|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'photo' are required.|
     |"true" |"base64"|""    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'first_name' are required.|
     |"true" |"base64"|"Auto"    |""    |"+380664853399"|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'last_name' are required.|
     |"true" |"base64"|"Auto"    |"Bot"    |""|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'phone_number' are required.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853399"|""|"pass"  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'email' are required.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|""  |/admin/admin.add| 1     |400        |error.message|Incorrect request body. Parameters: 'password' are required.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr@gmail.com"|"pass"  |/admin/admin.add|  ""    |400        |error.message|Incorrect request body. Parameters: 'role_id' are required.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853399"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.add| 1  |409|error.message|Phone number already used.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853397"|"maysalexandr@gmail.com"|"pass"|/admin/admin.add| 1  |409|error.message|Email already used.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.add| 10  |404|error.message|Role with ID '10' does not exist.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"pass"|/admin/admin.ad| 1  |404|message|Requested resource were not found at given endpoint.|
     |"true" |"base64"|"Auto"    |"Bot"    |"+380664853397"|"maysalexandr1@gmail.com"|"anypass"|/admin/admin.add| 1  |400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|

  Scenario: admin_authorization
      Given Sending request with generated API key for admin using
      |"maysalexandr@gmail.com"|"pass"|
      When POST request send to "/admin/authorization"
      Then Status-code "200" is received
      And Response contains authorization token


    Scenario Outline: admin_authorization errors
      Given Sending some request with <admin_token> using <email> and <password>
      When POST request send to <resource>
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
      Given Sending error request with <admin_token> using <email>
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


