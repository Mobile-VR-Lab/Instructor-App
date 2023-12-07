package com.example.mobilevrlab.script;

import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.mobilevrlab.script.data.Action;

public class ActionClickableSpan extends ClickableSpan {
    private final Action action;

    public ActionClickableSpan(Action action) {
        this.action = action;
    }

    @Override
    public void onClick(@NonNull View widget) {
        // TODO replace with REST API calls in a future issue
        System.out.println("TODO action REST API call for: " + action.toString());
    }
}
