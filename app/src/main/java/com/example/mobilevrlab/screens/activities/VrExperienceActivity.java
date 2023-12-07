package com.example.mobilevrlab.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.screens.controller.VrExperienceController;

import java.util.ArrayList;

/**
 * This activity assumes a Script XML File has been imported, parsed, and saved into the ScriptSingleton instance.
 */
public class VrExperienceActivity extends AppCompatActivity {
    VrExperienceController controller;

    TextView vr_exp_title;
    TextView vr_scene_title;
    TextView vr_action_count;
    TextView script_text;
    LinearLayout scene_buttons_layout;

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
        script_text = (TextView) findViewById(R.id.script_text);
        script_text.setMovementMethod(LinkMovementMethod.getInstance());
        scene_buttons_layout = (LinearLayout) findViewById(R.id.scene_buttons_layout);
    }

    /**
     * Display all of the initial script details to the user.
     * Create the scene buttons dynamically for directly changing the scene.
     */
    @Override
    protected void onStart() {
        super.onStart();
        vr_exp_title.setText(controller.getVrExpTitle());
        loadCurrentSceneData();

        // Add dynamic scene buttons
        ArrayList<String> sceneNames = controller.getAllSceneTitles();
        for (int i = 0; i < sceneNames.size(); i++) {
            scene_buttons_layout.addView(createSceneButton(sceneNames.get(i), i));
        }
    }

    /**
     * Create a single Scene Button View to be added into the scene list.
     *
     * @param text to display on button
     * @param index of the corresponding scene
     * @return configured View of the scene button
     */
    private View createSceneButton(String text, int index) {
        View sceneButtonView = getLayoutInflater().inflate(R.layout.scene_button, scene_buttons_layout, false);
        Button sceneButton = (Button) sceneButtonView.findViewById(R.id.scene_button);
        sceneButton.setText(text);
        sceneButton.setTag(index); // Storing index of scene into tag of view to be read from later
        sceneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onSceneButtonClick((int) v.getTag());
            }
        });
        return sceneButtonView;
    }

    /**
     * Overrides the back button to prevent accidental exits of the VR Experience.
     * Users will still be able to exit through the settings menu.
     */
    @Override
    public void onBackPressed() {
        System.out.println("Log user pressed back button"); // TODO replace with logger in future issue
    }

    /**
     * Load the current Scene's title, action count, and script texts into the activity.
     */
    protected void loadCurrentSceneData() {
        vr_scene_title.setText(controller.getVrSceneTitle());
        vr_action_count.setText(controller.getVrSceneActionCount());

        // Load script texts for the current scenes
        script_text.setText(""); // Clear
        controller.getVrSceneScriptText().forEach(charseq -> script_text.append(charseq));
    }

    /**
     * When the user clicks the "Next" button to advance the scene, only advance the scene if possible.
     *
     * @param view of button
     */
    public void onNext(View view) {
        System.out.println("Next button clicked!"); // TODO replace with logger
        if (controller.nextScene()) {
            loadCurrentSceneData();
        } else {
            Toast.makeText(this, "End Of Scenes", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When the user clicks the "Previous" button to go back a scene, only go back if possible.
     *
     * @param view of button
     */
    public void onPrevious(View view) {
        System.out.println("Previous button clicked!"); // TODO replace with logger
        if (controller.previousScene()) {
            loadCurrentSceneData();
        } else {
            Toast.makeText(this, "End Of Scenes", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * When a specific scene button is clicked, then go to that scene.
     *
     * @param index of scene selected
     */
    public void onSceneButtonClick(int index) {
        System.out.println("Scene button " + index + " clicked!"); // TODO replace with logger
        if (controller.setSceneIndex(index)) {
            loadCurrentSceneData();
        } else {
            Toast.makeText(this, "ERROR: Scene Not Available", Toast.LENGTH_SHORT).show();
        }
    }
}