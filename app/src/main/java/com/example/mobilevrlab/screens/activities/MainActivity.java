package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.mobilevrlab.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
    }

    public void toConfigureActivity(View view) {
        Intent intent = new Intent(this, ConfigureActivity.class);
        startActivity(intent);
    }

    public void toScriptActivity(View view) {
        Intent intent = new Intent(this, ScriptActivity.class);
        startActivity(intent);
    }
}