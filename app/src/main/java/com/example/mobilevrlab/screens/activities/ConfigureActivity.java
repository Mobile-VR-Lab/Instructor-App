package com.example.mobilevrlab.screens.activities;

import static com.example.mobilevrlab.screens.controller.ScriptLoader.OPEN_DIRECTORY_CODE;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.screens.controller.ScriptLoader;

public class ConfigureActivity extends AppCompatActivity {

    private ScriptLoader scriptLoader;
    // a type-safe method to handle the result of an activity start
    private ActivityResultLauncher<Intent> fileSelectionActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configure_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        scriptLoader = new ScriptLoader();

        // Initialize the ActivityResultLauncher
        fileSelectionActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        scriptLoader.onActivityResult(OPEN_DIRECTORY_CODE, RESULT_OK, result.getData(), 0, getContentResolver());
                    }
                }
        );
    }

    // Go back to the home screen if the "x" button is clicked
    public void goBack(View view) {
        this.finish();
    }

    // Open the system file selection dialog when the "select file" button is clicked
    public void onSelectScriptFileClick(View view) {
        Intent intent = scriptLoader.getIntentForFileSelection();
        fileSelectionActivityResultLauncher.launch(intent);
    }

    // the deprecated onActivityResult is no longer needed and has been removed
}
