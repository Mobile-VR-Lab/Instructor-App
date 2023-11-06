package com.example.mobilevrlab.script.data;

import androidx.annotation.NonNull;

public class ScriptText {
    public final String text;

    public ScriptText(String text) {
        this.text = text.trim();
    }

    public boolean isAction() {
        return false;
    }

    @NonNull
    @Override
    public String toString() {
        return "ScriptText: " + text;
    }
}
