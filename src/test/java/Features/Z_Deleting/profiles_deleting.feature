Feature: profiles_deleting

  Scenario Outline: profile.delete
    Given Sending profile.delete request using <token>
    When DELETE request /driver/profile.delete is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
      |token|status_code|key|value|
      |false|401        |error.message|Authentication key: 'false' is incorrect.|
      |"bearer"|200     |"status"|200|

