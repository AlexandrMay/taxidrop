Feature: driver_registration


  Scenario: phone/confirm
    Given Sending request with FB token to confirm drivers number
    When POST request phone.confirm is sent
    Then Status-code "200" is received
    And Response contains drivers registration token


  Scenario: driver/registration
    Given Sending request to register driver with correct token and with parameters
      |"Auto"|"Driver"|"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|""|"base64"|"base64"|"base64"|"AUTO123"|"2010-01-01"|"2030-02-01"|"Tavria"|"Nova"|"AUTO-123 R"|"base64"|"base64"|"base64"|"Privat"|"23423423423"|"robo driver"|"2345/3453/345"|"+234857463284"|
    When POST request driver/registration send to correct resource
    Then Status-code "200" is received
    And Response contains id of driver


  Scenario Outline: driver/registration with errors
    Given Given sending driver/registration request using <token>, <first_name>, <last_name>, <email>, <phone_number>, <password>, <registration_token>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|first_name|last_name|email|phone_number|password|registration_token|resource|status_code|key|value|
      |"false"|"Auto"  |"Driver" |"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|412|error.message|Incorrect request headers. Headers: 'Key' are malformed or incorrect.|
      |"true"|""  |"Driver" |"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
      |"true"|"Auto"  |"" |"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
      |"true"|"Auto"  |"Driver" |""|"+380664853398"|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"true"|"Auto"  |"Driver" |"maysalexandr1@i.ua"|""|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'phone_number' are required.|
      |"true"|"Auto"  |"Driver" |"maysalexandr1@i.ua"|"+380664853398"|"123"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|
      |"true"|"Auto"  |"Driver" |"maysalexandr1@i.ua"|"+380664853398"|""|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Incorrect request body. Parameters: 'password' are required.|
      |"true"|"Auto"  |"Driver" |"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|"L7QaIH&N@wX4B5UJgM-d"|/driver/registration|400|error.message|Registration token is incorrect.|




