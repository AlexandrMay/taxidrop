Feature: passenger_registration


  Scenario: passenger/registration
    Given Sending request with correct token and with parameters
    |"Auto"|"Passenger"|"+380664853398"|"a.may@woxapp.com"|"passengerpass"|""|"7lKUL^3oFk6meWiIdAw4"|
    When POST request send to "/passenger/registration"
    Then Status-code "200" is received
    And Response contains id of passenger


  Scenario: authorization



