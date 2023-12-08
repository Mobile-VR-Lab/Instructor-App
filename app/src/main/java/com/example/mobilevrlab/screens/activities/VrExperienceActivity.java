package com.example.mobilevrlab.screens.activities;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilevrlab.R;
import com.example.mobilevrlab.rest.Command;
import com.example.mobilevrlab.rest.RestClient;
import com.example.mobilevrlab.screens.controller.VrExperienceController;

import java.util.ArrayList;

/**
 * This activity assumes a Script XML File has been imported, parsed, and saved into the ScriptSingleton instance.
 */
public class VrExperienceActivity extends AppCompatActivity {
    VrExperienceController controller;
    final @ColorInt int deactivatedModeColor = Color.parseColor("#CB989898");

    /**
     * UI Variables
     */
    TextView attention_toggle;
    TextView transparency_toggle;
    ImageView status_button;
    TextView vr_exp_title;
    LinearLayout scene_buttons_layout;
    TextView vr_scene_title;
    TextView vr_action_count;
    TextView script_text;

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

        attention_toggle = (TextView) findViewById(R.id.attention_toggle);
        attention_toggle.setTag(false);
        attention_toggle.setOnClickListener(this::toggleAttentionMode);
        transparency_toggle = (TextView) findViewById(R.id.transparency_toggle);
        transparency_toggle.setTag(false);
        transparency_toggle.setOnClickListener(this::toggleTransparencyMode);
        status_button = (ImageView) findViewById(R.id.status_button);
        status_button.setOnClickListener(this::toHeadsetStatusActivity);
        vr_exp_title = (TextView) findViewById(R.id.vr_exp_title);
        scene_buttons_layout = (LinearLayout) findViewById(R.id.scene_buttons_layout);
        vr_scene_title = (TextView) findViewById(R.id.vr_scene_title);
        vr_action_count = (TextView) findViewById(R.id.vr_action_count);
        script_text = (TextView) findViewById(R.id.script_text);
        script_text.setMovementMethod(LinkMovementMethod.getInstance());

        // Add dynamic scene buttons
        ArrayList<String> sceneNames = controller.getAllSceneTitles();
        for (int i = 0; i < sceneNames.size(); i++) {
            scene_buttons_layout.addView(createSceneButton(sceneNames.get(i), i));
        }

        RestClient.getInstance().subscribe(this::handleMessageReceived);
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

    // TODO comment
    public void handleMessageReceived(String message) {
        // received message from server
        System.out.println(message);

        // for now, just display a popup
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    // TODO comment
    public void toHeadsetStatusActivity(View view) {
        Intent intent = new Intent(this, HeadsetStatusActivity.class);
        startActivity(intent);
    }

    // TODO comment
    public void sendCommand(Command command) {
        RestClient.sendCommandToServer(command, (response, e) -> {
            if (e != null) {
                e.printStackTrace();
            } else {
                System.out.println(response);
            }
        });
    }

    // TODO comment
    public interface DoubleCheckCallback {
        void onResult(boolean result);
    }

    // TODO comment
    public void doubleCheck(String message, DoubleCheckCallback callback) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message);
            builder.setPositiveButton("Yes", (dialog, which) -> callback.onResult(true));
            builder.setNegativeButton("No", (dialog, which) -> callback.onResult(false));
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    // TODO comment
    public void toggleAttentionMode(View v) {
        doubleCheck("Toggle Attention Mode?", result -> {
            if (result) {
                if ((boolean) v.getTag()) {
                    v.setTag(false);
                    attention_toggle.setTextColor(deactivatedModeColor);
                    sendCommand(Command.DEACTIVATE_ATTENTION_MODE);
                } else {
                    v.setTag(true);
                    attention_toggle.setTextColor(Color.BLACK);
                    sendCommand(Command.ACTIVATE_ATTENTION_MODE);
                }
            }
        });
    }

    // TODO comment
    public void toggleTransparencyMode(View v) {
        doubleCheck("Toggle Transparency Mode?", result -> {
            if (result) {
                if ((boolean) v.getTag()) {
                    v.setTag(false);
                    transparency_toggle.setTextColor(deactivatedModeColor);
                    sendCommand(Command.DEACTIVATE_TRANSPARENCY_MODE);
                } else {
                    v.setTag(true);
                    transparency_toggle.setTextColor(Color.BLACK);
                    sendCommand(Command.ACTIVATE_TRANSPARENCY_MODE);
                }
            }
        });
    }
}