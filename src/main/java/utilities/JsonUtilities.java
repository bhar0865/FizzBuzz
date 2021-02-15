package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * This class contains method to convert the response of REST API call to Class Oject
 * Input parameters jsonString, Class
 * Output: Class Object consisting of Json values
 */
public class JsonUtilities {

    public static <T> T fromResponseToClassObj(String jsonString, Class<T> jsonClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, jsonClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }




}
