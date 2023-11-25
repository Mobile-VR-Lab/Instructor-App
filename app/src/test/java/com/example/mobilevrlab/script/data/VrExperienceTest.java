package com.example.mobilevrlab.script.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class VrExperienceTest {
    String expectedTitle = "My VR Experience Title";
    ArrayList<Scene> scenes = new ArrayList<>();
    VrExperience vrExperience = null;

    @Before
    public void setup() {
        vrExperience = new VrExperience(expectedTitle, scenes);
    }

    @After
    public void cleanup() {
        vrExperience = null;
        scenes = new ArrayList<>();
    }

    @Test
    public void vrExperience_instantiation() {
        assertEquals(expectedTitle, vrExperience.title);
        assertEquals(scenes, vrExperience.sceneList);
    }

    @Test
    public void vrExperience_toString() {
        String expectedString = "VR Experience: My VR Experience Title (0 scenes)";
        assertEquals(expectedString, vrExperience.toString());

        vrExperience.sceneList.add(null);

        String expectedString2 = "VR Experience: My VR Experience Title (1 scenes)";
        assertEquals(expectedString2, vrExperience.toString());
    }

    @Test
    public void vrExperience_toCombinedString() {
        String expectedString = "VR Experience: My VR Experience Title (0 scenes)";
        assertEquals(expectedString, vrExperience.toCombinedString());

        ArrayList<ScriptText> scripts = new ArrayList<>();
        scripts.add(new ScriptText("This is my sentence."));
        scripts.add(new Action("myId2", "myType", "action text"));
        vrExperience.sceneList.add(new Scene("myId3", "Scene Title", scripts));

        String expectedString2 = "VR Experience: My VR Experience Title (1 scenes)"
                + "\n\tScene: Scene Title (#myId3, 2 script parts)"
                + "\n\t\tScriptText: This is my sentence."
                + "\n\t\tAction: action text (#myId2, 'myType')";
        assertEquals(expectedString2, vrExperience.toCombinedString());
    }
}
