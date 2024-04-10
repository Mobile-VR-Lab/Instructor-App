package com.example.mobilevrlab.screens.controller;

import com.example.mobilevrlab.rest.RestRequest;
import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.VrExperience;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A controller for the business logic for the VR Experience Activity.
 * Is responsible for interacting with the stored VR Experience script data and keeping track of the
 * current scene index being displayed to the user.
 */
public class VrExperienceController {

    VrExperience script;
    int currentScene = 0;

    /**
     * Create the VR Experience Controller and get a copy of the current VR Experience data.
     */
    public VrExperienceController() {
        script = ScriptSingleton.getInstance().getVrExperience();
    }

    /**
     * Create a Change Scene Request to be sent through the RestClient on a background thread.
     */
    protected void sendChangeSceneRequest() {
        Optional<String> opt = getVrSceneId();
        if (opt.isPresent()) {
            new RestRequest().postChangeScene(Integer.parseInt(opt.get()));
        } else {
            System.out.println("Error: No current scene ID was able to be retrieved. No scene change request sent."); // TODO add logger in future issue
        }
    }

    /**
     * Create a Change Attention Mode Request to be sent through the RestClient on a background thread.
     */
    public void sendChangeAttentionModeRequest(boolean mode) {
        new RestRequest().postChangeAttentionMode(mode);
    }

    /**
     * Create a Change Transparency Mode Request to be sent through the RestClient on a background thread.
     */
    public void sendChangeTransparencyModeRequest(boolean mode) {
        new RestRequest().postChangeTransparencyMode(mode);
    }

    /**
     * Advance the scene, if possible. If at the end of the scenes list, do not advance.
     *
     * @return true if can advance, false if cannot
     */
    public boolean nextScene() {
        if (currentScene + 1 < script.sceneList.size()) {
            currentScene++;
            sendChangeSceneRequest();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Go back a scene, if possible. If at the beginning fo the scenes list, do not go back.
     *
     * @return true if can go back, false if cannot
     */
    public boolean previousScene() {
        if (currentScene - 1 >= 0) {
            currentScene--;
            sendChangeSceneRequest();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set the current scene to a specific index (such as clicking a scene button directly).
     *
     * @param index of scene to set
     * @return true if scene index is possible, false if it is out of bounds
     */
    public boolean setSceneIndex(int index) {
        if (index >= 0 && index < script.sceneList.size()) {
            currentScene = index;
            sendChangeSceneRequest();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the title of the overall VR Experience.
     *
     * @return VR Exp Title string
     */
    public String getVrExpTitle() {
        return script.title;
    }

    /**
     * Get the title of a scene by its index.
     *
     * @return title of that VR Scene
     */
    public String getVrSceneTitle() {
        if (currentScene >= 0 && currentScene < script.sceneList.size()) {
            return script.sceneList.get(currentScene).title;
        } else {
            return "Error: Scene index out of bounds"; // TODO replace with error logger in future issue
        }
    }

    /**
     * Gets the ID of the scene specified in the script file for the current scene
     * based off of the current scene index.
     *
     * @return Optional String of current scene ID or empty Optional
     */
    public Optional<String> getVrSceneId() { // TODO change ID from int to string
        if (currentScene >= 0 && currentScene < script.sceneList.size()) {
            return Optional.of(script.sceneList.get(currentScene).id);
        } else {
            System.out.println("Error: Scene index out of bounds"); // TODO replace with error logger in future issue
            return Optional.empty();
        }
    }

    /**
     * Get the titles of all of the scenes in an Array List.
     *
     * @return ArrayList of scene title Strings
     */
    public ArrayList<String> getAllSceneTitles() {
        return new ArrayList<>(script.sceneList.stream().map(s -> s.title).collect(Collectors.toList()));
    }

    /**
     * Get the text to display the count of actions in this scene.
     *
     * @return actions count String
     */
    public String getVrSceneActionCount() {
        if (currentScene >= 0 && currentScene < script.sceneList.size()) {
            long count = script.sceneList.get(currentScene).script.stream().filter(s -> s.isAction()).count();
            return count + (count == 1 ? " Action" : " Actions");
        } else {
            return "Error: Scene index out of bounds"; // TODO replace with error logger in future issue
        }
    }

    /**
     * Get a Stream of the Scene's script texts, which are a mix of plain text and clickable actions.
     *
     * @return Stream of script texts as CharSequence
     */
    public Stream<CharSequence> getVrSceneScriptText() {
        return script.sceneList.get(currentScene).script.stream().map(s -> s.toCharSequence());
    }
}