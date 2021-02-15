package api;

import dto.Outcome;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utilities.ExcelUtilities;
import utilities.JsonUtilities;
import utilities.RestAPIHelper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is the test class which tests the REST API of FIZZBUZZ application
 */
public class TestFizzBuzzAPI {

    private List<Outcome> expectedOutcomeList = new ArrayList<>();
    private List<Outcome> actualOutcomeList = new ArrayList<>();

    //Read the input test data and get the expected outcomee result in a list.
    @BeforeTest
    public void readTestData() {
        expectedOutcomeList = ExcelUtilities.readDataExcel("src/test/resources/testdata/TestData.xlsx", "Sheet1");
    }

    //Verify expected outcome with actual outcome from the REST API
    @Test
    public void testFizzBuzz() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        Response response = RestAPIHelper.doPost("http://fizzbuzz-exercise.herokuapp.com/api/fizzbuzz-calculator",
                new String(Files.readAllBytes(Paths.get("src/test/resources/testdata/input.json"))), 200);

        System.out.println(response.asString());
        Outcome[] apiRespons = JsonUtilities.fromResponseToClassObj(response.asString(), Outcome[].class);

        Collections.addAll(actualOutcomeList, apiRespons);

        actualOutcomeList.removeAll(expectedOutcomeList);
        for (int i = 0; i < actualOutcomeList.size(); i++) {
            softAssert.fail("The outcome for " + actualOutcomeList.get(i).getInput() + " which is " + actualOutcomeList.get(i).getResult() + " is NOT as per requirement");
        }
        softAssert.assertAll();
    }
}




