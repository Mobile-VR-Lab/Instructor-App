package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.mobilevrlab.R;

public class ScriptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        findViewById(R.id.transparency_toggle).setOnClickListener(v -> {
            if (v.isActivated()) {
                activateTransparencyMode(v);
            } else {
                activateTransparencyMode(v);
            }
        });
    }

    public void activateTransparencyMode(View view) {

    }

    public void activateAttentionMode(View view) {

    }

    public void deactivateAttentionMode(View view) {

    }
}