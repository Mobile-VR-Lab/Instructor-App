package com.example.mobilevrlab.script.data;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Scene {
    public final String id;
    public final String title;
    public final ArrayList<ScriptText> script;

    public Scene(String id, String title, ArrayList<ScriptText> scriptTexts) {
        this.id = id;
        this.title = title.trim();
        this.script = scriptTexts;
    }

    @NonNull
    @Override
    public String toString() {
        return "Scene: " + title + " (#" + id + ", " + script.size() + " script parts)";
    }
}
