package com.example.mobilevrlab.rest;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class RestRequest {
    // TODO idea: could add callback to this and accept this class into asynctaskrunner, then callback post ececute

    public void getHeadsets() {
        Request request = new Request.Builder()
                .url(RestClient.baseUrl + "headsets")
                .get()
                .build();
        new AsyncTaskRunner().execute(request);
    }

    public void postChangeScene(int scene) { // TODO make this a class so it can be constructed dynamically in the future
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("scene", scene);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        String data = json.toString();
        System.out.println(data); // TODO remove or log

        RequestBody body = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));

        // Construct the request
        Request request = new Request.Builder()
                .url(RestClient.baseUrl + "command/1")
                .post(body)
                .build();
        new AsyncTaskRunner().execute(request);
    }
}
