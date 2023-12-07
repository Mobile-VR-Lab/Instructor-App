package com.example.mobilevrlab.screens.controller;

import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.VrExperience;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A controller for the business logic for the VR Experience Activity.
 * Is responsible for interacting with the stored VR Experience script data.
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

    // TODO comment
    public boolean nextScene() {
        if (currentScene + 1 < script.sceneList.size()) {
            currentScene++;
            return true;
        } else {
            return false;
        }
    }

    // TODO comment
    public boolean previousScene() {
        if (currentScene - 1 >= 0) {
            currentScene--;
            return true;
        } else {
            return false;
        }
    }

    // TODO comment
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
     * @return VR Exp Title string
     */
    public String getVrExpTitle() {
        return script.title;
    }

    /**
     * Get the title of a scene by its index.
     * @return title of that VR Scene
     */
    public String getVrSceneTitle() {
        // TODO handle out of bounds here
        return script.sceneList.get(currentScene).title;
    }

    // TODO comment
    public ArrayList<String> getAllSceneTitles() {
        return new ArrayList<>(script.sceneList.stream().map(s -> s.title).collect(Collectors.toList()));
    }

    /**
     * Get the text to display the count of actions in this scene.
     * @return actions count String
     */
    public String getVrSceneActionCount() {
        // TODO handle out of bounds
        long count = script.sceneList.get(currentScene).script.stream().filter(s -> s.isAction()).count();
        return count + (count == 1 ? " Action" : " Actions");
    }

    // TODO comment
    public Stream<CharSequence> getVrSceneScriptText() {
        return script.sceneList.get(currentScene).script.stream().map(s -> s.toCharSequence());
    }
}