Feature: passenger_registration


  Scenario: phone/confirm
    Given Sending request with FB token to confirm passengers number
    When POST request phone.confirm for passenger is sent
    Then Status-code "200" is received
    And Response contains passengers registration token


  Scenario Outline: phone/confirm error
    Given sending phone/confirm request using <token>, <user_type> and <FB_code>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|user_type|FB_code|resource|status_code|key|value|
    |6c6c5f6139dd1f56b63f720488ac8a3b|1|"FB"|/phone.confirm|401|error.message|Either your user session has expired, or your access credentials is malformed.|
    |"true"                          |1|"nonFB"|/phone.confirm|400|error.message|Confirmation code is incorrect.|
    |"true"                          |3|"FB"|/phone.confirm|400|error.message|Incorrect request body. Parameters: 'user_type' are malformed or incorrect.|
    |"true"                          |""|"FB"|/phone.confirm|400|error.message|Incorrect request body. Parameters: 'user_type' are required.|
    |"true"                          |1|""|/phone.confirm|400|error.message|Incorrect request body. Parameters: 'code' are required.|


  Scenario: passenger/registration
    Given Sending request with correct token and with parameters
    |"Auto"|"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|"1"|
    When POST request passenger/registration is sent
    Then Status-code "200" is received
    And Response contains id of passenger


    Scenario Outline: passenger/registration with errors
      Given sending passenger/registration request using <token>, <first_name>, <last_name>, <phone_number>, <email>, <password>, <invite_code>, <registration_token>
      When POST request send to <resource>
      Then Statuscode <status_code> is received
      And Response contains <key> and <value>
      Examples:
      |token|first_name|last_name  |phone_number   |email                     |password|invite_code|registration_token|resource|status_code|key|value|
      |"false"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|412|error.message|Incorrect request headers. Headers: 'Key' are malformed or incorrect.|
      |"true"|""  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'first_name' are required.|
      |"true"|"Auto"  |""|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'last_name' are required.|
      |"true"|"Auto"  |"Passenger"|""|"buggserazzer@hotmail.com"|"passengerpass"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'phone_number' are required.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|""|"passengerpass"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'email' are required.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"123"|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'password' are malformed or incorrect.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|""|""  |"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'password' are required.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|"123456789123456789123"|"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Incorrect request body. Parameters: 'invite_code' are malformed or incorrect.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|""|"L5F4i7dryng8Bc!Gf6JV"|/passenger/registration|400|error.message|Registration token is incorrect.|
      |"true"|"Auto"  |"Passenger"|"+380672594067"|"buggserazzer@hotmail.com"|"passengerpass"|""|""|/passenger/registration|400|error.message|Registration token is incorrect.|



    Scenario: passenger/authorization
      Given sending passenger/authorization request using
      |1|"+380672594067"|"passengerpass"|
      When POST request passenger/authorization is sent
      Then Status-code "200" is received
      And Response contains passengers authorization token












