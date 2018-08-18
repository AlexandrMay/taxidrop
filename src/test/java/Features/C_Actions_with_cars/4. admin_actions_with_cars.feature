Feature: admin_actions_with_cars

  Scenario Outline: /admin/passenger.cars
    Given sending /admin/cars request using <token>
    When GET /admin/passenger.cars request with <resource> and <passenger_id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/passenger.cars contains <key> and <value>
    Examples:
    |token|resource|passenger_id|status_code|key|value|
    |"true"|/admin/passenger.cars/|"trueId"|200|cars.id|"fromDB"|
    |"true"|/admin/passenger.cars/|0|404|error.message|User with ID. '0' not found in system.|
    |false|/admin/passenger.cars/|"trueId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/driver.cars
    Given sending /admin/cars request using <token>
    When GET /admin/driver.cars request with <resource> and <driver_id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/driver.cars contains <key> and <value>
    Examples:
      |token|resource|driver_id|status_code|key|value|
      |"true"|/admin/driver.cars/|"trueId"|200|cars.id|"fromDB"|
      |"true"|/admin/driver.cars/|0|404|error.message|User with ID. '0' not found in system.|
      |false|/admin/driver.cars/|"trueId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario: /passenger/car.add with DB
    Given sending /passenger/car.add request
      |"Tesla"|"model x"|"ABC-123 D"|"base64"|
    When POST request "/passenger/car.add" is sent
    Then Status_code is 200
    And id of car equals to DB

  Scenario Outline: /admin/car.approve
    Given sending /admin/cars request using <token>
    When PUT /admin/car.approve request with <resource> and <car_id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/car.approve contains <key> and <value>
    Examples:
    |token|resource|car_id|status_code|key|value|
    |"true"|/admin/car.approve/|"carId"|200|"success"|200|
    |"true"|/admin/car.approve/|"carId"|400|error.message|"repeat"|
    |"true"|/admin/car.approve/|0|404|error.message|Car with ID '0' does not exist.|
    |false|/admin/car.approve/|"carId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/car.edit
    Given sending /admin/car.edit request using <token>, <make>, <model>, <year>, <car_photo>, <license_plate_number>, <color>
    When PUT /admin/car.approve request with <resource> and <car_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|make|model|year|car_photo|license_plate_number|color|resource|car_id|status_code|key|value|
    |"true"|"Cyberdyne systems"|"Some_model"|2028|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|200|"success"|200|
    |"true"|"Cyberdyne systems"|"Some_model"|2028|"base64"|"ABC-123"|"steel"|/admin/car.edit/|0|404|error.message|Car with ID '0' does not exist.|
    |false|"Cyberdyne systems"|"Some_model"|2028|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"Some_model"|2028|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'make' are required.|
    |"true"|"Cyberdyne systems"|""|2028|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'model' are required.|
    |"true"|"Cyberdyne systems"|"Some_model"|0|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'year' are required.|
    |"true"|"Cyberdyne systems"|"Some_model"|20288|"base64"|"ABC-123"|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'year' are malformed or incorrect.|
    |"true"|"Cyberdyne systems"|"Some_model"|2028|""|"ABC-123"|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'car_photo' are malformed or incorrect.|
    |"true"|"Cyberdyne systems"|"Some_model"|2028|"base64"|""|"steel"|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'license_plate_number' are required.|
    |"true"|"Cyberdyne systems"|"Some_model"|2028|"base64"|"ABC-123"|""|/admin/car.edit/|"carId"|400|error.message|Incorrect request body. Parameters: 'color' are required.|

  Scenario Outline: /admin/car.delete/
    Given sending /admin/cars request using <token>
    When DELETE /admin/car.delete request with <resource> and <car_id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/car.delete contains <key> and <value>
    Examples:
    |token|resource|car_id|status_code|key|value|
    |"true"|/admin/car.delete/|"carId"|200|"success"|200|
    |"true"|/admin/car.delete/|"carId"|404|error.message|"repeat"|
    |false|/admin/car.delete/|"carId"|401|error.message|Authentication key: 'false' is incorrect.|










