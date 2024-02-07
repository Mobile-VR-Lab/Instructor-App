package com.example.mobilevrlab.script.data;

import android.text.SpannableString;
import android.text.Spanned;

import androidx.annotation.NonNull;

import com.example.mobilevrlab.screens.ui.ActionClickableSpan;

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

    /**
     * Create a String representation of this object, which is useful for debugging.
     *
     * @return String representation
     */
    @NonNull
    @Override
    public String toString() {
        return "Action: " + text + " (#" + id + ", '" + type + "')";
    }

    /**
     * Create a CharSequence of this object, for displaying the script to the user.
     * This also includes creating a SpannableString and linking an ActionClickableSpan to that String
     * so that the Action looks like a hyperlink in text but will actually trigger a custom function when clicked.
     *
     * @return CharSequence representation
     */
    @Override
    public CharSequence toCharSequence() {
        SpannableString spannableStr = new SpannableString(text);
        spannableStr.setSpan(new ActionClickableSpan(this), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStr;
    }
}
