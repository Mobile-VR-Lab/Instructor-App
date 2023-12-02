package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.script.ScriptSingleton;

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

    public void toVrExperienceActivity(View view) {
        // Check if a script file has been loaded
        if (ScriptSingleton.getInstance().getVrExperience() == null) {
            Toast.makeText(this, "No Script File Selected", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, VrExperienceActivity.class);
            startActivity(intent);
        }
    }
}