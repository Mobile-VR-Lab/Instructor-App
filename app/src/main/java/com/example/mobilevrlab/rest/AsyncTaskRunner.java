package com.example.mobilevrlab.rest;

import android.os.AsyncTask;

import okhttp3.Request;
import okhttp3.Response;

public class AsyncTaskRunner extends AsyncTask<Request, String, String> {
    @Override
    protected String doInBackground(Request... requests) {
        // Synchronous call of rest client to endpoint in background thread (separate from UI thread)
        Response response = RestClient.getInstance().syncCall(requests[0]);
        return null;
    }

    // TODO send data back to UI thread for headsets requests (callback?)

//        private String resp;
//        ProgressDialog progressDialog;
//
//        @Override
//        protected String doInBackground(String... params) {
//            publishProgress("Sleeping..."); // Calls onProgressUpdate()
//            try {
//                int time = Integer.parseInt(params[0])*1000;
//
//                Thread.sleep(time);
//                resp = "Slept for " + params[0] + " seconds";
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            } catch (Exception e) {
//                e.printStackTrace();
//                resp = e.getMessage();
//            }
//            return resp;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            // execution of result of Long time consuming operation
//            finalResult.setText(result);
//        }
//
//
//        @Override
//        protected void onPreExecute() {
//            progressDialog = ProgressDialog.show(MainActivity.this,
//                    "ProgressDialog",
//                    "Wait for "+time.getText().toString()+ " seconds");
//        }
//
//
//        @Override
//        protected void onProgressUpdate(String... text) {
//            finalResult.setText(text[0]);
//
//        }
}
