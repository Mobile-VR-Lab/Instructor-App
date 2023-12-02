package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.screens.controller.VrExperienceController;

/**
 * This activity assumes a Script XML File has been imported, parsed, and saved into the ScriptSingleton instance.
 */
public class VrExperienceActivity extends AppCompatActivity {
    VrExperienceController controller;

    TextView vr_exp_title;
    TextView vr_scene_title;
    TextView vr_action_count;

    /**
     * Create this activity and connect the layout file.
     * Lock the orientation into landscape mode.
     * Get the controller for this activity.
     * Get refs to all of the UI elements that need to be updated.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_experience);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE);

        controller = new VrExperienceController();

        vr_exp_title = (TextView) findViewById(R.id.vr_exp_title);
        vr_scene_title = (TextView) findViewById(R.id.vr_scene_title);
        vr_action_count = (TextView) findViewById(R.id.vr_action_count);
    }

    /**
     * Display all of the initial script details to the user.
     */
    @Override
    protected void onStart() {
        super.onStart();
        vr_exp_title.setText(controller.getVrExpTitle());
        vr_scene_title.setText(controller.getVrSceneTitle());
        vr_action_count.setText(controller.getVrSceneActionCount());
    }

    /**
     * Overrides the back button to prevent accidental exits of the VR Experience.
     * Users will still be able to exit through the settings menu.
     */
    @Override
    public void onBackPressed() {
        System.out.println("Log user pressed back button"); // TODO replace with logger in future issue
    }

    public void onNext(View view) {
        if (controller.nextScene()) {
            vr_scene_title.setText(controller.getVrSceneTitle());
            vr_action_count.setText(controller.getVrSceneActionCount());
        } else {
            Toast.makeText(this, "End Of Scenes", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPrevious(View view) {
        if (controller.previousScene()) {
            vr_scene_title.setText(controller.getVrSceneTitle());
            vr_action_count.setText(controller.getVrSceneActionCount());
        } else {
            Toast.makeText(this, "End Of Scenes", Toast.LENGTH_SHORT).show();
        }
    }
}