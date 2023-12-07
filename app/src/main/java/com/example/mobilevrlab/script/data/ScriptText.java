package com.example.mobilevrlab.script.data;

import androidx.annotation.NonNull;

public class ScriptText {
    public final String text;

    public ScriptText(String text) {
        this.text = text;
    }

    public boolean isAction() {
        return false;
    }

    /**
     * Create a String representation of this object, which is useful for debugging.
     *
     * @return String representation
     */
    @NonNull
    @Override
    public String toString() {
        return "ScriptText: " + text;
    }

    /**
     * Create a CharSequence of this object, for displaying the script to the user.
     *
     * @return CharSequence representation
     */
    public CharSequence toCharSequence() {
        return (CharSequence) text;
    }
}
