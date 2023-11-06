package com.example.mobilevrlab.script.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScriptTextTest {
    String expectedText = "This is my script sentence.";
    ScriptText scriptText = null;

    @Before
    public void setup() {
        scriptText = new ScriptText(expectedText);
    }

    @After
    public void cleanup() {
        scriptText = null;
    }

    @Test
    public void scriptText_instantiation() {
        assertFalse(scriptText.isAction());
        assertEquals(expectedText, scriptText.text);
    }

    @Test
    public void scriptText_toString() {
        String expectedString = "ScriptText: This is my script sentence.";
        assertEquals(expectedString, scriptText.toString());
    }
}
