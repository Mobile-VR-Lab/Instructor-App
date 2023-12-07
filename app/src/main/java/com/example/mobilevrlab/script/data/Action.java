package com.example.mobilevrlab.script.data;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;

import androidx.annotation.NonNull;

import com.example.mobilevrlab.script.ActionClickableSpan;

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
        SpannableString spannableStr = new SpannableString(text);
        spannableStr.setSpan(new ActionClickableSpan(this), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStr;
    }
}
