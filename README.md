This project is the Test Automation project to test FizzBuzz application.
REST API testing is done as part of this project.
To run this test, the test class to run is TestFizzBuzzAPI.
Test data is placed under /test/resources which is TestData.xlsx
Input json is also placed under /test/resources
There are two utilities classes placed under src/main/utilities, these are RestAPIHelper which contains methods to do a POST call. And the other class is ExcelUtilities which is used to read the TestData excel sheet.
The project is a Maven Project and the pom.xml contains all dependencies.
The project also has TestNG framework for writing tests, RestAssured Java framework for performing REST calls and also uses Apache POI for reading test data from excel sheet.
