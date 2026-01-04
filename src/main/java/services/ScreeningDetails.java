package services;

import base.APIControlActions;
import base.ScreeningControl;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ScreeningDetails extends APIControlActions {

    public Response getScreeningDetails(String TestID) {
        setHeader("Accept", "application/json");

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("candidateScreeningId", ScreeningControl.candidateScreeningId);
        queryParams.put("candidateApplicationId", ScreeningControl.jobApplicationId);
        setQueryParams(queryParams);

        return executeGetAPI("/api/candidateScreening/get-screening-questions/"+TestID);
    }
}