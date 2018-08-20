Feature: admin_editables

  Scenario Outline: /admin/news.list
    Given sending /admin/news request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /admin/news.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/news.list?amount=100&status=0|200|news.id|"fromDB_status_0"|
    |"true"|/admin/news.list?amount=100&status=1|200|news.id|"fromDB_status_1"|
    |"true"|/admin/news.list?amount=100         |200|news.id|"fromDB"|
    |"true"|/admin/news.list?amount=0           |400|error.message|Incorrect request body. Parameters: 'amount' are malformed or incorrect.|
    |false|/admin/news.list?amount=100          |401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/news.info
    Given sending /admin/news request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/news.info/1|200|"id"|1|
    |"true"|/admin/news.info/0|404|error.message|Article with ID '0' not found in system.|
    |false|/admin/news.info/1|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/news.add
    Given sending /admin/news.add request using <token>, <title>, <text>, <image>, <published_date>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/news.add contains <key> and <value>
    Examples:
    |token|title|text|image|published_date|resource|status_code|key|value|
    |"true"|"auto title"|"auto text"|"base64"|"tomorrow_date"|/admin/news.add|200|"article_id"|"article_id_1"|
    |"true"|"auto title"|"auto text"|""|"current_date"|/admin/news.add|200|"article_id"|"article_id_2"|
    |"true"|"auto title"|"auto text"|"base64"|"yesterday_date"|/admin/news.add|400|error.message|Incorrect request body. Parameters: 'published_date' are malformed or incorrect.|
    |false|"auto title"|"auto text"|"base64"|"current_date"|/admin/news.add|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|"auto text"|"base64"|"current_date"|/admin/news.add|400|error.message|Incorrect request body. Parameters: 'title' are required.|
    |"true"|"auto title"|""|"base64"|"2016-07-29 15:50:00"|/admin/news.add|400|error.message|Incorrect request body. Parameters: 'text' are required.|
    |"true"|"auto title"|"auto text"|"base64"|"1"|/admin/news.add|400|error.message|Incorrect request body. Parameters: 'published_date' are malformed or incorrect.|
    |"true"|"auto title"|"auto text"|"base64"|""|/admin/news.add|400|error.message|Incorrect request body. Parameters: 'published_date' are required.|

  Scenario Outline: /admin/news.edit
    Given sending /admin/news.add request using <token>, <title>, <text>, <image>, <published_date>
    When PUT /admin/news.edit request with <resource> and <news_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|title|text|image|published_date|resource|news_id|status_code|key|value|
    |"true"|"new title"|"new text"|"base64new"|"current_date"|/admin/news.edit/|"newsId"|200|"success"|200|
    |"true"|"new title"|"new text"|""|"current_date"|/admin/news.edit/|"newsId"|200|"success"|200|
    |"true"|"new title"|"new text"|"base64new"|"tomorrow_date"|/admin/news.edit/|"newsId"|200|"success"|200|
    |"true"|"new title"|"new text"|"base64new"|"yesterday_date"|/admin/news.edit/|"newsId"|400|error.message|Incorrect request body. Parameters: 'published_date' are malformed or incorrect.|
    |false|"new title"|"new text"|"base64new"|"tomorrow_date"|/admin/news.edit/|"newsId"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|"new title"|"new text"|"base64new"|"tomorrow_date"|/admin/news.edit/|0|404|error.message|Article with ID '0' not found in system.|
    |"true"|""|"new text"|"base64new"|"tomorrow_date"|/admin/news.edit/|"newsId"|400|error.message|Incorrect request body. Parameters: 'title' are required.|
    |"true"|"new title"|""|"base64new"|"tomorrow_date"|/admin/news.edit/|"newsId"|400|error.message|Incorrect request body. Parameters: 'text' are required.|
    |"true"|"new title"|"new text"|"base64new"|""|/admin/news.edit/|"newsId"|400|error.message|Incorrect request body. Parameters: 'published_date' are required.|

  Scenario Outline: /admin/news.status
    Given sending /admin/news.status request using <token>, <status>
    When PUT /admin/news.edit request with <resource> and <news_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|status|resource|news_id|status_code|key|value|
    |"true"|0|/admin/news.status/|"newsId"|200|"success"|200|
    |"true"|1|/admin/news.status/|"newsId"|200|"success"|200|
    |"true"|2|/admin/news.status/|"newsId"|400|error.message|Incorrect request body. Parameters: 'status' are malformed or incorrect.|
    |"true"|-1|/admin/news.status/|"newsId"|400|error.message|Incorrect request body. Parameters: 'status' are required.|
    |false|0|/admin/news.status/|"newsId"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|0|/admin/news.status/|0|404|error.message|Article with ID '0' not found in system.|

  Scenario Outline: /admin/news.delete
    Given sending /admin/news request using <token>
    When DELETE /admin/news.delete request with <resource> and <news_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|news_id|status_code|key|value|
    |"true"|/admin/news.delete/|"newsId_1"|200|"success"|200|
    |"true"|/admin/news.delete/|"newsId_2"|200|"success"|200|
    |"true"|/admin/news.delete/|0|404|error.message|Article with ID '0' not found in system.|
    |false|/admin/news.delete/|1|401|error.message|Authentication key: 'false' is incorrect.|





  Scenario Outline: /admin/help.list
    Given sending /admin/news request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /admin/help.list contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/help.list?type=0|200|"id"|"type0"|
    |"true"|/admin/help.list?type=1|200|"id"|"type1"|
    |"true"|/admin/help.list?type=2|200|"id"|"type2"|
    |"true"|/admin/help.list?type=3|400|error.message|Incorrect request body. Parameters: 'type' are malformed or incorrect.|
    |false|/admin/help.list?type=0|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/help.add
    Given sending /admin/help.add request using <token>, <title>, <type>, <appeal1>, <appeal2>
    When POST request send to <resource>
    Then Statuscode <status_code> is received
    And Response of /admin/help.add contains <key> and <value>
    Examples:
    |token|title|type|appeal1|appeal2|resource|status_code|key|value|
    |"true"|"auto title"|0|"Appeal1"|"Appeal2"|/admin/help.add|200|"section_id"|"section_id"|
    |"true"|"auto title"|-1|"Appeal1"|"Appeal2"|/admin/help.add|400|error.message|Incorrect request body. Parameters: 'type' are required.|
    |"true"|"auto title"|3|"Appeal1"|"Appeal2"|/admin/help.add|400|error.message|Incorrect request body. Parameters: 'type' are malformed or incorrect.|
    |"true"|""|1|"Appeal1"|"Appeal2"|/admin/help.add|400|error.message|Incorrect request body. Parameters: 'title' are required.|
    |"true"|"auto title"|1|"empty"|"empty"|/admin/help.add|400|error.message|Incorrect request body. Parameters: 'appeals' are required.|
    |false|"auto title"|1|"Appeal1"|"Appeal2"|/admin/help.add|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/help.info/
    Given sending /admin/news request using <token>
    When GET /admin/help.info request with <resource> and <help_id> is sent
    Then Statuscode <status_code> is received
    And Response /admin/help.info/ contains <key> and <value>
    Examples:
    |token|resource|help_id|status_code|key|value|
    |"true"|/admin/help.info/|"helpId"|200|"id"|"helpId"|
    |"true"|/admin/help.info/|0       |404|error.message|Help section with ID '0' not found in system.|
    |false|/admin/help.info/|"helpId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/help.edit
    Given sending /admin/help.add request using <token>, <title>, <type>, <appeal1>, <appeal2>
    When PUT /admin/help.edit request with <resource> and <help_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|title|type|appeal1|appeal2|resource|help_id|status_code|key|value|
    |"true"|"new title"|1|"new_appeal1"|"new_appeal2"|/admin/help.edit/|"helpId"|200|"success"|200|
    |"true"|"new title"|1|"new_appeal1"|"new_appeal2"|/admin/help.edit/|0|404|error.message|Help section with ID '0' not found in system.|
    |false|"new title"|1|"new_appeal1"|"new_appeal2"|/admin/help.edit/|"helpId"|401|error.message|Authentication key: 'false' is incorrect.|
    |"true"|""|1|"new_appeal1"|"new_appeal2"|/admin/help.edit/|"helpId"|400|error.message|Incorrect request body. Parameters: 'title' are required.|
    |"true"|"new title"|-1|"new_appeal1"|"new_appeal2"|/admin/help.edit/|"helpId"|400|error.message|Incorrect request body. Parameters: 'type' are required.|
    |"true"|"new title"|3|"new_appeal1"|"new_appeal2"|/admin/help.edit/|"helpId"|400|error.message|Incorrect request body. Parameters: 'type' are malformed or incorrect.|
    |"true"|"new title"|1|"empty"|"empty"|/admin/help.edit/|"helpId"|400|error.message|Incorrect request body. Parameters: 'appeals' are required.|

  Scenario Outline: /admin/help.delete
    Given sending /admin/news request using <token>
    When DELETE /admin/help.delete request with <resource> and <help_id> is sent
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|resource|help_id|status_code|key|value|
    |"true"|/admin/help.delete/|"helpId"|200|"success"|200|
    |"true"|/admin/help.delete/|0|404|error.message|Help section with ID '0' not found in system.|
    |false|/admin/help.delete/|"helpId"|401|error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/about
    Given sending /admin/news request using <token>
    When GET request send to <resource>
    Then Statuscode <status_code> is received
    And Response /admin/about contains <key> and <value>
    Examples:
    |token|resource|status_code|key|value|
    |"true"|/admin/about|200   |"author_id"|"fromDB"|
    |false |/admin/about|401   |error.message|Authentication key: 'false' is incorrect.|

  Scenario Outline: /admin/about.edit
    Given sending /admin/about.edit request using <token>, <text>
    When PUT request send to <resource>
    Then Statuscode <status_code> is received
    And Response contains <key> and <value>
    Examples:
    |token|text|resource|status_code|key|value|
    |"true"|"auto robot text"|/admin/about.edit|200|"success"|200|
    |"true"|""|/admin/about.edit|400|error.message|Incorrect request body. Parameters: 'text' are required.|
    |false|"auto robot text"|/admin/about.edit|401|error.message|Authentication key: 'false' is incorrect.|























