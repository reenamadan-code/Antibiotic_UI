package com.example.testgage.Network;

public class AntibioticAPIUtils {
    private AntibioticAPIUtils() {}

    public static final String BASE_URL = "https://m6fea98ao1.execute-api.us-west-1.amazonaws.com/dev/";

    public static AntibioticAPIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(AntibioticAPIService.class);
    }
}
