package com.example.mobilevrlab.rest;

import android.os.AsyncTask;

import okhttp3.Request;
import okhttp3.Response;

/**
 * An Async Task Runner used to execute Rest Client Requests not on the UI thread.
 */
public class AsyncTaskRunner extends AsyncTask<Request, String, String> {
    @Override
    protected String doInBackground(Request... requests) {
        // Synchronous call of rest client to endpoint in background thread (separate from UI thread)
        try {
            Response response = RestClient.getInstance().syncCall(requests[0]);
        } catch (Exception e) {
            System.out.println("ERROR: Rest Client unable to send message!");
            // TODO eventually pass back a toast request to the user???
        }
        return null;
    }

    // TODO send data back to UI thread for headsets requests (callback?) in future issue-41 here
}
