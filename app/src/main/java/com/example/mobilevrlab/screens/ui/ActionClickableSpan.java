package com.example.mobilevrlab.screens.ui;

import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.mobilevrlab.script.data.Action;

/**
 * A clickable hyperlink for an Action that can be configure in the OnClick method.
 * This does not actually open a link in a browser like a traditional hyperlink.
 */
public class ActionClickableSpan extends ClickableSpan {
    private final Action action;

    /**
     * Create the ActionClickableSpan for an Action.
     *
     * @param action the Action object this is representing
     */
    public ActionClickableSpan(Action action) {
        this.action = action;
    }

    /**
     * Trigger a REST API call to the server when an Action is clicked.
     *
     * @param widget the View that this ClickableAction is tied to.
     */
    @Override
    public void onClick(@NonNull View widget) {
        // TODO replace with a REST API call in a future issue
        System.out.println("TODO action REST API call for: " + action.toString());
    }
}
