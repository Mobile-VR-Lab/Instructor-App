package com.example.mobilevrlab.screens.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.screens.controller.ScriptLoader;

public class ConfigureActivity extends AppCompatActivity {

    ScriptLoader scriptLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);
        scriptLoader = new ScriptLoader();
    }

    // Go back to the home screen if the "x" button is clicked
    public void goBack(View view) {
        this.finish();
    }

    // Open the system file selection dialog when the "select file" button is clicked
    // note that for testing, a file was put on the emulator at /storage/emulated/0/Download/myscript.xml
    public void onSelectScriptFileClick(View view) {
        startActivityForResult(scriptLoader.getIntentForFileSelection(), scriptLoader.OPEN_DIRECTORY_CODE);
    }

    // When the system file selection dialog closes, handle opening the selected XML file
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        scriptLoader.onActivityResult(requestCode, resultCode, resultData, this.RESULT_OK, getContentResolver());
    }
}
