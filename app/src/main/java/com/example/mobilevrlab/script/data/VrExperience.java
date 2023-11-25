package com.example.mobilevrlab.script.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class VrExperience {
    public final String title;
    public final ArrayList<Scene> sceneList;

    public VrExperience(String title, ArrayList<Scene> scenes) {
        this.title = title.trim();
        this.sceneList = scenes;
    }

    @NonNull
    @Override
    public String toString() {
        return "VR Experience: " + title + " (" + sceneList.size() + " scenes)";
    }

    public String toCombinedString() {
        return this + sceneList.stream()
                .map(s -> "\n\t" + s.toString() + s.script.stream()
                        .map(a -> "\n\t\t" + a.toString())
                        .collect(Collectors.joining()))
                .collect(Collectors.joining());
    }
}
