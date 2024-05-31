# Test Driven Development Demo
SpendWise is an JAVA base CLI application which is used to keep track of expenses made. As the main purpose of this application is to showcase TDD, the database is not durable i.e. data is vanished when program terminates. The main agenda of TDD is to write code that is testable.

## Features provided by the application
* Add an expense
* Remove an expense
* Update an expense
* Get all expenses for current month
* Get all expenses for specific month
* Get current month expenses cost
* Get specific month expenses cost
* Get recent expenses

## How to run CLI application?
1. Clone this repository or Download zip (and unzip) to specific location.
2. Open project in your preferred IDE that supports JAVA.
3. Right click pom.xml <br>
   a. If not added as maven project -> Add as maven project. <br>
   b. If added as maven project -> Maven -> Reload project (to make sure all necessary dependencies for testing are added)
4. Run SpendWiseApplication class main method to start the application.
5. App will start, and now you can use application features.

## Folder structure
- `data` - All files related to the data layer, typically, database operations, API calling, etc.
    - `entity` - All entity files of the database, e.g. ExpenseEntity. Entity represent a table row in a database
    - `enums` - All enums used in the application.
- `model` - All model class that holds data for UI layer.
- `repository` - All repositories used in the application and thier concrete implmentation classes. Ideally, repository is the part of data layer which acts as bridge between the data layer and UI layer. They provide high level overview of what functionalities are provided by the data layer. But for this application, to not over-complicate using services and repositories, opted for direct repository intervention with the UI.
- `ui` - All classes that deals with the UI(CLI) of the application.
- `util` - All utility classes of the application.

## Test coverage report
All the business logic classes are covered and highlighted in the screenshot: [IntelliJ Code Coverage Screenshot][TestCoverageScreenshot]

| Exception               | Reason                                                                                                             |
|:------------------------|:-------------------------------------------------------------------------------------------------------------------|
| ExpenseEntity           | Data level model class which contains getter, setter and equals method accessed inside DatabaseRepository(mocked). |
| MenuOption              | UI level enum class to handle user input.                                                                          |
| DatabaseRepository/Impl | Is always mocked as it deals with database CRUD operations                                                         |
| UI Package              | On purpose no test cases written for CLI                                                                           |
| SpendWiseApplication    | Part of UI - Application entry point                                                                               |

## Thanks
> Any fool can write code that a computer can understand. Good programmers write code that humans can understand.<br> - Martin Fowler

[TestCoverageScreenshot]: /assets/TestCoverageReport.png "Test Coverage Screenshot"