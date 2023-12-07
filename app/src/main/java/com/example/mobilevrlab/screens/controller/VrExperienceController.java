package com.example.mobilevrlab.screens.controller;

import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.VrExperience;

import java.util.ArrayList;
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
     * Method for TESTING only!
     * Get the script that is used inside fo this controller.
     *
     * @return VrExperience script
     */
    public VrExperience getScript() {
        return script;
    }

    /**
     * Advance the scene, if possible. If at the end of the scenes list, do not advance.
     *
     * @return true if can advance, false if cannot
     */
    public boolean nextScene() {
        if (currentScene + 1 < script.sceneList.size()) {
            currentScene++;
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