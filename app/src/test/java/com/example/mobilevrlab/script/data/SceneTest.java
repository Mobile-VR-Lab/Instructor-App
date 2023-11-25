package com.example.mobilevrlab.script.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class SceneTest {
    String expectedId = "myId7";
    String expectedTitle = "My scene title";

    ArrayList<ScriptText> scripts = new ArrayList<>();
    Scene scene = null;

    @Before
    public void setup() {
        scene = new Scene(expectedId, expectedTitle, scripts);
    }

    @After
    public void cleanup() {
        scene = null;
        scripts = new ArrayList<>();
    }

    @Test
    public void scene_instantiation() {
        assertEquals(expectedId, scene.id);
        assertEquals(expectedTitle, scene.title);
        assertEquals(scripts, scene.script);
    }

    @Test
    public void scene_toString() {
        String expectedString = "Scene: My scene title (#myId7, 0 script parts)";
        assertEquals(expectedString, scene.toString());

        scene.script.add(null);
        String expectedString2 = "Scene: My scene title (#myId7, 1 script parts)";
        assertEquals(expectedString2, scene.toString());
    }
}
