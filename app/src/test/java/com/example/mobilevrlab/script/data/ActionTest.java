package com.example.mobilevrlab.script.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ActionTest {
    String expectedText = "my action text";
    String expectedId = "myId5";
    String expectedType = "myType";
    Action action = null;

    @Before
    public void setup() {
        action = new Action(expectedId, expectedType, expectedText);
    }

    @After
    public void cleanup() {
        action = null;
    }

    @Test
    public void action_instantiation() {
        assertTrue(action.isAction());
        assertEquals(expectedText, action.text);
        assertEquals(expectedId, action.id);
        assertEquals(expectedType, action.type);
    }

    @Test
    public void action_toString() {
        String expectedString = "Action: my action text (#myId5, 'myType')";
        assertEquals(expectedString, action.toString());
    }
}
