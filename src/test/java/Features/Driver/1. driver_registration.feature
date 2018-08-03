Feature: driver_registration

  Scenario: phone/confirm
    Given Sending request with FB token to confirm drivers number
    When POST request phone.confirm is sent
    Then Status-code "200" is received
    And Response contains drivers registration token

@NeedTo
  Scenario: driver/registration
    Given Sending request to register driver with correct token and with parameters
      |"Auto"|"Driver"|"maysalexandr1@i.ua"|"+380664853398"|"driverpass"|""|"base64"|"base64"|"base64"|"AUTO123"|"2010-01-01"|"2030-02-01"|"Tavria"|"Nova"|"AUTO-123 R"|"base64"|"base64"|"base64"|"Privat"|"23423423423"|"robo driver"|"2345/3453/345"|"+234857463284"|
    When POST request driver/registration send to correct resource
    Then Status-code "200" is received
    And Response contains id of driver

  Scenario: driver/authorization
    Given Sending request to authorize driver using creds
    |1|"+380664853398"|"driverpass"|
    When POST request authorization is sent
    Then Status-code "200" is received
    And Response contains drivers authorization token



