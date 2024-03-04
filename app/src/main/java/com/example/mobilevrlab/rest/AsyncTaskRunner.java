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
        Response response = RestClient.getInstance().syncCall(requests[0]);
        return null;
    }

    // TODO send data back to UI thread for headsets requests (callback?) in future issue-41 here
}
