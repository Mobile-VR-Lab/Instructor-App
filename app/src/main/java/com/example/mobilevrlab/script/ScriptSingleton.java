package com.example.mobilevrlab.script;

import com.example.mobilevrlab.script.data.VrExperience;

public class ScriptSingleton {
    private static ScriptSingleton instance = null;
    private VrExperience vrExperience = null;

    public static ScriptSingleton getInstance() {
        if (instance == null) {
            instance = new ScriptSingleton();
        }
        return instance;
    }

    public VrExperience getVrExperience() {
        return vrExperience;
    }

    public void setVrExperience(VrExperience vrExperience) {
        this.vrExperience = vrExperience;
    }
}
