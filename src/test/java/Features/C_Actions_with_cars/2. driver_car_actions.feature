Feature: driver_car_actions


  Scenario: /driver/car.add with DB
    Given sending /driver/car.add request
      |"Tesla"|"model x"|"ABC-123 D"|"base64"|
    When POST request "/driver/car.add" is sent
    Then Status_code is 200
    And id of drivers car equals to DB

  Scenario Outline: /driver/car.add with errors
    Given sending /driver/car.add request using <token>, <make>, <model>, <license_plate_number>, <car_photo>, <proof_of_ownership>, <insurance>
    When POST request send to <resource>
    Then Status-code "<status_code>" is received
    And Response contains <key> and <value>
    Examples:
      |token|make|model|license_plate_number|car_photo|proof_of_ownership|insurance|resource|status_code|key|value|
      |"true"|"Tesla"|"model x"|"ABC-123 D" |"base64" |"base64"|"base64" |/driver/car.add|200| |     |
      |"true"|"Tesla"|"model x"|"ABC-123 D" |"base64" |"base64"|"base64" |/driver/car.add|400|error.message|Maximum number of cars.|
      |false|"Tesla"|"model x"|"ABC-123 D" |"base64" |"base64"|"base64" |/driver/car.add|401|error.message|Authentication key: 'false' is incorrect.|
      |"true"|""|"model x"|"ABC-123 D" |"base64" |"base64"|"base64" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'make' are required.|
      |"true"|"Tesla"|""|"ABC-123 D" |"base64" |"base64"|"base64" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'model' are required.|
      |"true"|"Tesla"|"model x"|"" |"base64" |"base64"|"base64" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'license_plate_number' are required.|
      |"true"|"Tesla"|"model x"|"ABC-123 D" |"" |"base64"|"base64" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'car_photo' are required.|
      |"true"|"Tesla"|"model x"|"ABC-123 D" |"base64" |""|"base64" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'proof_of_ownership' are required.|
      |"true"|"Tesla"|"model x"|"ABC-123 D" |"base64" |"base64"|"" |/driver/car.add|400|error.message|Incorrect request body. Parameters: 'insurance' are required.|

  Scenario Outline: /driver/cars.list
    Given sending /driver/cars.list request using <token>
    When GET request send to <resource>
    Then Status-code "<status_code>" is received
    And Response contains <key> and <value>
    Examples:
      |token|resource|status_code|key|value|
      |"true"|/driver/cars.list|200|"count"|3|
      |false|/driver/cars.list|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /driver/car.delete
    Given sending /driver/car.delete request using <token>
    When DELETE /driver/car.delete request with <resource> and <car_id> is sent
    Then Status-code "<status_code>" is received
    And Response contains <key> and <value>
    Examples:
      |token|car_id|resource|status_code|key|value|
      |"true"|"true"|/driver/car.delete/|200|"success"|200|
      |"true"|0|/driver/car.delete/|404|error.message|Car with ID '0' does not exist.|
      |false|"true"|/driver/car.delete/|401|error.message|Authentication key: 'false' is incorrect.|