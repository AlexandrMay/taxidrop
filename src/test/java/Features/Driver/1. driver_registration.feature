Feature: driver_registration



  Scenario: driver/registration
    Given Sending request to register driver with correct token and with parameters
      |"Auto"|"Driver"|"maysalexandr@i.ua"|"+380689224214"|"driverpass"|""|"base64"|"base64"|"base64"|"AUTO123"|"2010-01-01"|"2030-02-01"|"Tavria"|"Nova"|"AUTO-123 R"|"base64"|"base64"|"base64"|"Privat"|"23423423423"|"robo driver"|"2345/3453/345"|"+234857463284"|"9MojZz^FK16Se#WxLU!3"|
    When POST request driver/registration send to correct resource
    Then Status-code "200" is received
    And Response contains id of driver

