package com.example.mobilevrlab.screens.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.rest.Command;
import com.example.mobilevrlab.rest.RestClient;

import java.util.List;

import com.example.mobilevrlab.rest.RestRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

public class HeadsetStatusActivity extends AppCompatActivity {

    public static class HeadsetStatus {
        public String name;
        public String connectionStatus;
        public String batteryLevel;
        public String errorStatus;
    }

    private LinearLayout linearLayoutHeadsets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headset_status);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        linearLayoutHeadsets = findViewById(R.id.linearLayout_headsets);
        Button buttonRefresh = findViewById(R.id.button_refresh);

        buttonRefresh.setOnClickListener(v -> getHeadsetStatuses());

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> finish());

        // Initial refresh on activity start
        getHeadsetStatuses();
    }

    private View createHeadsetStatusView(HeadsetStatus status) {
        // Inflate the custom layout for the headset status
        View headsetStatusView = getLayoutInflater().inflate(R.layout.headset_status_item, null);

        // Find and set the TextViews for name, battery level, and error status
        TextView textViewName = headsetStatusView.findViewById(R.id.textViewHeadsetName);
        TextView textViewBattery = headsetStatusView.findViewById(R.id.textViewBatteryLevel);
        TextView textViewError = headsetStatusView.findViewById(R.id.textViewErrorStatus);
        TextView textViewConnection = headsetStatusView.findViewById(R.id.textViewConnectionStatus);

        textViewName.setText(status.name);
        textViewBattery.setText(String.format("Battery: %s", status.batteryLevel));
        textViewConnection.setText(String.format("Status: %s", status.connectionStatus));
        textViewError.setText(String.format("Error: %s", status.errorStatus.isEmpty() ? "No errors" : status.errorStatus));

        return headsetStatusView;
    }

    @UiThread
    private void getHeadsetStatuses() {
        linearLayoutHeadsets.removeAllViews();

        // Return a list of HeadsetStatus objects
//        RestClient.sendCommandToServer(Command.GET_STATUSES, (response, e) -> {
//            if (e != null) {
//                e.printStackTrace();
//            } else {
//                //System.out.println(response);
//                // Parse the JSON response into a List of HeadsetStatus objects
//                Type listType = new TypeToken<List<HeadsetStatus>>(){}.getType();
//                List<HeadsetStatus> headsetStatuses = new Gson().fromJson(response, listType);
//                // Iterate over the headset statuses and create a view for each one
//                if (headsetStatuses == null) {
//                    System.out.println("Headset statuses is null");
//                    return;
//                }
//                for (HeadsetStatus status : headsetStatuses) {
//                    if (status == null) {
//                        System.out.println("Headset status is null");
//                        continue;
//                    }
//                    View headsetStatusView = createHeadsetStatusView(status);
//                    linearLayoutHeadsets.addView(headsetStatusView);
//                }
//            }
//        });

        // TODO fix later with real request, add controller, etc.
//        new RestRequest().getHeadsets(); // TODO move to headset activity
    }
}
