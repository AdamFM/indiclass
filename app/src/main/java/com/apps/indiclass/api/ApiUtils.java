package com.apps.indiclass.api;

/**
 * Created by Dell_Cleva on 09/03/2018.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://indiclass.id/api/v1/";

    public static ApiCall getRestAPI(String base) {

        return ApiClient.getClient(base).create(ApiCall.class);
    }
}
