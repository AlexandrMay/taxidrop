Feature: admin_actions

  Scenario Outline: /admin/admins.list
    Given sending /admin/admins.list request with <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/admins.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/admins.list?amount=10|200|"total"|"fromDB"|
    |"true"|/admin/admins.list?amount=0|400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/admin/admins.list?amount=10|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/admin.edit
    Given sending /admin/admin.edit request with <token>, <photo>, <first_name>, <last_name>, <phone_number>, <email>, <role_id>
    When PUT /admin/admin.edit request with <resource> and <id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/admins.list contains <key> and <value>
    Examples:
    |token|photo|first_name|last_name|phone_number|email|role_id|resource|id|status_code|key|value|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|200|"success"|200|
    |false|"base64"|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|0|404|error.message|Admin with ID '0' does not exist.|
    |"true"|""|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'photo' are required.|
    |"true"|"base64"|""|"Bot"|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
    |"true"|"base64"|"Auto"|""|"+380664853399"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
    |"true"|"base64"|"Auto"|"Bot"|"+380991234567"|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|409|error.message|Phone number already used.|
    |"true"|"base64"|"Auto"|"Bot"|""|"a.may@woxapp.com"|"roleID"|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'phone_number' are required.|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|"maysalexandr@gmail.com"|"roleID"|/admin/admin.edit/|"true"|409|error.message|Email already used.|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|""|"roleID"|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'email' are required.|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|0|/admin/admin.edit/|"true"|404|error.message|Role with ID '0' does not exist.|
    |"true"|"base64"|"Auto"|"Bot"|"+380664853399"|"a.may@woxapp.com"|""|/admin/admin.edit/|"true"|400|error.message|Incorrect request body. Parameters: 'role_id' are required.|



















