package com.example.mobilevrlab.script;

import static org.junit.Assert.*;

import com.example.mobilevrlab.script.data.Action;
import com.example.mobilevrlab.script.data.Scene;
import com.example.mobilevrlab.script.data.ScriptText;
import com.example.mobilevrlab.script.data.VrExperience;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RunWith(RobolectricTestRunner.class) // To avoid mocking the XmlPullParser
public class ScriptParserTest {

    @Test
    public void scriptParser_vrExperience_noScenes() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals("my vr experience", actualVrExp.title);
        assertEquals(0, actualVrExp.sceneList.size());
    }

    @Test
    public void scriptParser_emptyScene() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\"></scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals(1, actualVrExp.sceneList.size());

        Scene scene1 = actualVrExp.sceneList.get(0);
        assertEquals("my title 1", scene1.title);
        assertEquals("1", scene1.id);
        assertEquals(0, scene1.script.size());
    }

    @Test
    public void scriptParser_emptyScenes() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\"></scene>\n" +
                "    <scene title=\"my title 2\" id=\"2\"></scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals(2, actualVrExp.sceneList.size());

        Scene scene1 = actualVrExp.sceneList.get(0);
        assertEquals("my title 1", scene1.title);
        assertEquals("1", scene1.id);
        assertEquals(0, scene1.script.size());

        Scene scene2 = actualVrExp.sceneList.get(1);
        assertEquals("my title 2", scene2.title);
        assertEquals("2", scene2.id);
        assertEquals(0, scene2.script.size());
    }

    @Test
    public void scriptParser_scriptText() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\">\n" +
                "        my script here 1\n" +
                "    </scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals(1, actualVrExp.sceneList.size());

        Scene scene1 = actualVrExp.sceneList.get(0);
        assertEquals(1, scene1.script.size());

        ScriptText scriptText = scene1.script.get(0);
        assertFalse(scriptText.isAction());
        assertEquals("\n        my script here 1\n    ", scriptText.text);
    }

    @Test
    public void scriptParser_action() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\">\n" +
                "        <action id=\"5\" type=\"highlight\">action</action>\n" +
                "    </scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals(1, actualVrExp.sceneList.size());

        Scene scene1 = actualVrExp.sceneList.get(0);
        assertEquals(1, scene1.script.size());

        ScriptText scriptText = scene1.script.get(0);
        assertTrue(scriptText.isAction());

        Action action = (Action) scriptText;
        assertEquals("5", action.id);
        assertEquals("highlight", action.type);
        assertEquals("action", action.text);
    }

    @Test
    public void scriptParser_actionTextAction() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\">\n" +
                "        my script here 3.\n" +
                "        this is an <action id=\"5\" type=\"highlight\">action</action>\n" +
                "        what a paragraph.\n" +
                "    </scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertEquals(1, actualVrExp.sceneList.size());

        Scene scene1 = actualVrExp.sceneList.get(0);
        assertEquals(3, scene1.script.size());

        ScriptText scriptText1 = scene1.script.get(0);
        assertFalse(scriptText1.isAction());
        assertEquals("\n        my script here 3.\n        this is an ", scriptText1.text);

        ScriptText scriptText2 = scene1.script.get(1);
        assertTrue(scriptText2.isAction());

        Action action = (Action) scriptText2;
        assertEquals("5", action.id);
        assertEquals("highlight", action.type);
        assertEquals("action", action.text);

        ScriptText scriptText3 = scene1.script.get(2);
        assertFalse(scriptText3.isAction());
        assertEquals("\n        what a paragraph.\n    ", scriptText3.text);
    }

    @Test
    public void scriptParser_badTag_vrExperience() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertNull(actualVrExp);
    }

    @Test
    public void scriptParser_badTag_scene() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\">\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertNull(actualVrExp);
    }

    @Test
    public void scriptParser_badTag_action() throws XmlPullParserException, IOException {
        String testXmlFile = "<vrExperience title=\"my vr experience\">\n" +
                "    <scene title=\"my title 1\" id=\"1\">\n" +
                "        <action id=\"5\" type=\"highlight\">action\n" +
                "    </scene>\n" +
                "</vrExperience>";
        InputStream inputStream = new ByteArrayInputStream(testXmlFile.getBytes());

        VrExperience actualVrExp = (new ScriptParser()).parseInputStream(inputStream);
        assertNull(actualVrExp);
    }
}
