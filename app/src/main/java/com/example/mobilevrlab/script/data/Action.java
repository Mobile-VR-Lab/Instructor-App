package com.example.mobilevrlab.script.data;

import androidx.annotation.NonNull;

public class Action extends ScriptText {
    public final String id;
    public final String type;

    public Action(String id, String type, String text) {
        super(text);
        this.id = id;
        this.type = type;
    }

    @Override
    public boolean isAction() {
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        return "Action: " + text + " (#" + id + ", '" + type + "')";
    }
}
