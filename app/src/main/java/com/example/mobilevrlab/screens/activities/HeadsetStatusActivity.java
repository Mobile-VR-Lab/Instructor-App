package com.example.mobilevrlab.screens.activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.rest.RestClient;

import java.util.List;

public class HeadsetStatusActivity extends AppCompatActivity {

    public static class HeadsetStatus {
        public String name;
        public String connectionStatus;
        public String batteryLevel;
        public String errorStatus;
    }

    private LinearLayout linearLayoutHeadsets;
    private Button buttonRefresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headset_status);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        linearLayoutHeadsets = findViewById(R.id.linearLayout_headsets);
        buttonRefresh = findViewById(R.id.button_refresh);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshHeadsetStatuses();
            }
        });

        // Initial refresh on activity start
        refreshHeadsetStatuses();
    }

    private void refreshHeadsetStatuses() {
        // Clear previous status views
        linearLayoutHeadsets.removeAllViews();

        // Fetch the latest headset statuses
        List<HeadsetStatus> headsetStatuses = getHeadsetStatuses();

        // Iterate over the headset statuses and create a view for each one
        for (HeadsetStatus status : headsetStatuses) {
            View headsetStatusView = createHeadsetStatusView(status);
            linearLayoutHeadsets.addView(headsetStatusView);
        }
    }

    private View createHeadsetStatusView(HeadsetStatus status) {
        // Inflate a custom layout for the headset status
        // Set the battery level, error status, and connection status
        // Return the created view
        return null;
    }

    private List<HeadsetStatus> getHeadsetStatuses() {
        // Fetch the latest headset statuses
        // This could involve making a network request or querying a local database
        // Return a list of HeadsetStatus objects
        return null;
    }
}
