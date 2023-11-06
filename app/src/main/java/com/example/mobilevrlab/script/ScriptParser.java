package com.example.mobilevrlab.script;

import android.util.Xml;

import com.example.mobilevrlab.script.data.Action;
import com.example.mobilevrlab.script.data.Scene;
import com.example.mobilevrlab.script.data.ScriptText;
import com.example.mobilevrlab.script.data.VrExperience;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ScriptParser {

    // Parse out a VR Experience from an input stream (the script file) using an XML parser
    public VrExperience parseInputStream(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
//            printParserContents(parser); // Useful for testing / debugging

            return parseVrExperience(parser);
        } finally {
            in.close();
        }
    }

    // Parse out a VR Experience object
    // Ex: <vrExperience title="my vr experience">
    //         <scene title="my title 1" id="1">my script here 1</scene>
    //     </vrExperience>
    private VrExperience parseVrExperience(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "vrExperience");
        String title = parser.getAttributeValue(null, "title");
        ArrayList<Scene> scenes = new ArrayList<>();

        // Parse scenes in loop
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("scene")) {
                scenes.add(parseScene(parser));
            }
        }

        return new VrExperience(title, scenes);
    }

    // Parse out a scene object
    // Ex: <scene title="my title 2" id="2">
    //         my script here 2 <action id="5" type="highlight">action</action> then more text
    //     </scene>
    private Scene parseScene(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "scene");
        String title = parser.getAttributeValue(null, "title");
        String idString = parser.getAttributeValue(null, "id");
        ArrayList<ScriptText> scriptTexts = new ArrayList<>();

        // Parse text in loop
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() == XmlPullParser.TEXT) {
                scriptTexts.add(new ScriptText(parser.getText()));
            } else if (parser.getEventType() == XmlPullParser.START_TAG) {
                String name = parser.getName();
                if (name.equals("action")) {
                    scriptTexts.add(parseAction(parser));
                }
            }
        }

        return new Scene(idString, title, scriptTexts);
    }

    // Parse out an action object
    // Ex: <action id="5" type="highlight">inner text here</action>
    private Action parseAction(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "action");
        String idString = parser.getAttributeValue(null, "id");
        String type = parser.getAttributeValue(null, "type");
        String text = "";

        // Parse text in loop
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() == XmlPullParser.TEXT) {
                text = parser.getText();
            } else if (parser.getEventType() == XmlPullParser.START_TAG) {
                skip(parser);
            }
        }

        return new Action(idString, type, text);
    }

    // Skip over xml objects until you reach a closing tag at the same indentation level
    // Assumes that the starting tag has already been consumed
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    // Print out xml tags as they are consumed
    private void printParserContents(XmlPullParser parser) throws XmlPullParserException, IOException {
        // Print whole file
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document.");
            } else if(eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End document.");
            } else if(eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag: " + parser.getName());
                System.out.println("\tAttribute title? " + parser.getAttributeValue(null, "title"));
                System.out.println("\tAttribute id? " + parser.getAttributeValue(null, "id"));
            } else if(eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag: " + parser.getName());
            } else if(eventType == XmlPullParser.TEXT) {
                System.out.println("Text: " + parser.getText());
            }
            eventType = parser.next();
        }
    }
}
