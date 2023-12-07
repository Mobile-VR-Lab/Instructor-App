package com.example.mobilevrlab.script.data;

import android.text.Html;

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

    @Override
    public CharSequence toCharSequence() {
        // TODO replace with REST API calls in a future issue
        // TODO remove hyperlink and make this a clickable element
        return (CharSequence) Html.fromHtml("<a href='http://www.google.com'>" + text + "</a>");
    }
}
