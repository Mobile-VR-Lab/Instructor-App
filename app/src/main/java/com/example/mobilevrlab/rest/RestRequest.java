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

    // A templated way to create a post "command" request, as define in the Headset Control Protocol on the server
    private void postCommand(int commandId, JSONObject json) {
        // Serialize scene in json
        String data = json.toString();
//        System.out.println(data); // TODO add logger in future issue

        RequestBody body = RequestBody.create(data, MediaType.get("application/json; charset=utf-8"));

        // Construct the request
        Request request = new Request.Builder()
                .url(RestClient.baseUrl + "command/" + commandId)
                .post(body)
                .build();
        new AsyncTaskRunner().execute(request);
    }

    public void postChangeScene(int scene) {
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("scene", scene);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        postCommand(1, json);
    }

    public void postFocusOnObject(String obj) {
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("object", obj);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        postCommand(2, json);
    }

    public void postChangeAttentionMode(boolean mode) {
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("attention", mode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        postCommand(3, json);
    }

    public void postChangeTransparencyMode(boolean mode) {
        // Serialize scene in json
        JSONObject json = new JSONObject();
        try {
            json.put("transparency", mode);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        postCommand(4, json);
    }
}
