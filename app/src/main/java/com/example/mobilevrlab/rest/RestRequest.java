package com.example.mobilevrlab.rest;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * A class to handle creating a specific request for the Rest Client to execute.
 * This sends the request off to a new Async Task Runner to be executed on a background thread.
 */
public class RestRequest {
    public void getHeadsets() { // TODO test and cleanup in future issue-41 here
        Request request = new Request.Builder()
                .url(RestClient.baseUrl + "headsets")
                .get()
                .build();
        new AsyncTaskRunner().execute(request);
    }

    public void postChangeScene(String scene) {
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("scene", scene);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String data = json.toString();
//        System.out.println(data); // TODO add logger in future issue

        RequestBody body = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));

        // Construct the request
        Request request = new Request.Builder()
                .url(RestClient.baseUrl + "command/1")
                .post(body)
                .build();
        new AsyncTaskRunner().execute(request);
    }
}
