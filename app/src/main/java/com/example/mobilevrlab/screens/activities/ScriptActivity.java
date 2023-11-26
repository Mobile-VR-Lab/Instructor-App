package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.mobilevrlab.R;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ScriptActivity extends AppCompatActivity {

    private ToggleButton transparencyToggle;
    private ToggleButton attentionToggle;
    private CompoundButton.OnCheckedChangeListener toggleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_script);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        transparencyToggle = findViewById(R.id.transparency_toggle);
        attentionToggle = findViewById(R.id.attention_toggle);

        toggleListener = (v, isChecked) -> {

            if (v.getId() == R.id.transparency_toggle) {

                if (isChecked) {
                    setCheckedWithoutListener(transparencyToggle, false);
                    activateTransparencyMode(v);
                } else {
                    setCheckedWithoutListener(transparencyToggle, true);
                    deactivateTransparencyMode(v);
                }
            } else if (v.getId() == R.id.attention_toggle) {
                if (isChecked) {
                    setCheckedWithoutListener(attentionToggle, false);
                    activateAttentionMode(v);
                } else {
                    setCheckedWithoutListener(attentionToggle, true);
                    deactivateAttentionMode(v);
                }
            }
        };

        transparencyToggle.setOnCheckedChangeListener(toggleListener);
        attentionToggle.setOnCheckedChangeListener(toggleListener);
    }


    public void activateTransparencyMode(CompoundButton view) {
        doubleCheck("Activate transparency mode?", result -> {
            setCheckedWithoutListener(transparencyToggle, result);
        });
    }

    public void deactivateTransparencyMode(CompoundButton view) {
        doubleCheck("Deactivate transparency mode?", result -> {
            setCheckedWithoutListener(transparencyToggle, !result);
        });
    }

    public void activateAttentionMode(CompoundButton view) {
        doubleCheck("Activate attention mode?", result -> {
            setCheckedWithoutListener(attentionToggle, result);
        });
    }

    public void deactivateAttentionMode(CompoundButton view) {
        doubleCheck("Deactivate attention mode?", result -> {
            setCheckedWithoutListener(attentionToggle, !result);
        });
    }


    private void setCheckedWithoutListener(ToggleButton toggleButton, boolean checked) {
        toggleButton.setOnCheckedChangeListener(null); // Temporarily remove the listener
        toggleButton.setChecked(checked); // Set the checked state
        toggleButton.setOnCheckedChangeListener(toggleListener); // Re-add the listener
    }


    public interface DoubleCheckCallback {
        void onResult(boolean result);
    }

    public void doubleCheck(String message, DoubleCheckCallback callback) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(ScriptActivity.this);
            builder.setMessage(message);
            builder.setPositiveButton("Yes", (dialog, which) -> callback.onResult(true));
            builder.setNegativeButton("No", (dialog, which) -> callback.onResult(false));
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }


}