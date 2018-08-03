Feature: passenger_registration


  Scenario: phone/confirm
    Given Sending request with FB token to confirm passengers number
    When POST request phone.confirm for passenger is sent
    Then Status-code "200" is received
    And Response contains passengers authorization token


  Scenario: passenger/registration
    Given Sending request with correct token and with parameters
    |"Auto1"|"Passenger1"|"+380672594067"|"buggserazzer1@hotmail.com"|"passengerpass"|"1"|
    When POST request passenger/registration is sent
    Then Status-code "200" is received
    And Response contains id of passenger



