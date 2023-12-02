package com.example.mobilevrlab.screens.controller;

import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.VrExperience;

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

    public boolean nextScene() {
        // TODO handle out of bounds here
        if (currentScene + 1 < script.sceneList.size()) {
            currentScene++;
            return true;
        } else {
            return false;
        }
    }

    public boolean previousScene() {
        // TODO handle out of bounds here
        if (currentScene - 1 >= 0) {
            currentScene--;
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

    /**
     * Get the text t display the count of actions in this scene.
     * @return actions count String
     */
    public String getVrSceneActionCount() {
        // TODO handle out of bounds
        Long count = script.sceneList.get(currentScene).script.stream().filter(s -> s.isAction()).count();
        return count + (count == 1 ? " Action" : " Actions");
    }
}