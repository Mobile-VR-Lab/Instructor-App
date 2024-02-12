package com.example.mobilevrlab.rest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RestClient {
    public static final String baseUrl = "http://10.21.149.117:3000/"; // replace with actual server URL

    public final OkHttpClient client;

    private static RestClient instance;

    // Private constructor to prevent instantiation
    private RestClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    // Thread-safe method to get the instance
    public static synchronized RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    /**
     * Synchronous call to execute a given request through the RestClient
     *
     * @param req request to execute
     * @return response from request
     */
    public Response syncCall(Request req) {
        Call call = client.newCall(req);
        Response response = null;
        try {
            response = call.execute();
            System.out.println("Successful rest call!"); // TODO logger in future issue
        } catch (IOException e) {
            System.out.println("Error on rest call"); // TODO logger in future issue
            throw new RuntimeException(e);
        }
        return response;
    }
}
