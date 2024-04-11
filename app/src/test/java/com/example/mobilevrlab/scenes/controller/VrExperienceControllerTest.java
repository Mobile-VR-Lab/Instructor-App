package com.example.mobilevrlab.scenes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import com.example.mobilevrlab.rest.RestRequest;
import com.example.mobilevrlab.screens.controller.VrExperienceController;
import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.Action;
import com.example.mobilevrlab.script.data.Scene;
import com.example.mobilevrlab.script.data.ScriptText;
import com.example.mobilevrlab.script.data.VrExperience;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(PowerMockRunner.class)
@PrepareForTest(VrExperienceController.class)
public class VrExperienceControllerTest {

    @Mock
    VrExperience mockScript;

    final ArrayList<Scene> injectSceneList = new ArrayList<>(Arrays.asList(
            new Scene("id", "title1", new ArrayList<>(Arrays.asList(
                    new ScriptText("text1")
            ))),
            new Scene("id", "title2", new ArrayList<>(Arrays.asList(
                    new ScriptText("text1"),
                    spy(new Action("id", "type", "action1")),
                    new ScriptText("text2")
            )))
    ));

    @Mock
    RestRequest mockRRequest;

    @Before
    public void setup() throws Exception {
        ScriptSingleton.getInstance().setVrExperience(mockScript);
        Whitebox.setInternalState(mockScript, "sceneList", injectSceneList);
        whenNew(RestRequest.class).withNoArguments().thenReturn(mockRRequest); // Don't actually call into RestRequest class
    }

    @Test
    public void vrExpController_instantiation_accessesScript() {
        VrExperienceController controller = new VrExperienceController();
        VrExperience actualVrExp = Whitebox.getInternalState(controller, "script");
        assertNotNull(actualVrExp);
        assertEquals(mockScript, actualVrExp);
    }

    @Test
    public void vrExpController_currentScene_initial() {
        VrExperienceController controller = new VrExperienceController();
        assertEquals(0, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_previousScene_fail() {
        VrExperienceController controller = new VrExperienceController();
        assertFalse(controller.previousScene());
        assertEquals(0, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_previousScene_succeed() {
        VrExperienceController controller = new VrExperienceController();
        controller.nextScene();
        assertTrue(controller.previousScene());
        assertEquals(0, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_nextScene_fail() {
        VrExperienceController controller = new VrExperienceController();
        controller.nextScene();
        assertFalse(controller.nextScene());
        assertEquals(1, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_nextScene_succeed() {
        VrExperienceController controller = new VrExperienceController();
        assertTrue(controller.nextScene());
        assertEquals(1, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_setScene_fail() {
        VrExperienceController controller = new VrExperienceController();
        assertFalse(controller.setSceneIndex(10));
        assertEquals(0, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_setScene_succeed() {
        VrExperienceController controller = new VrExperienceController();
        assertTrue(controller.setSceneIndex(1));
        assertEquals(1, (int) Whitebox.getInternalState(controller, "currentScene"));
    }

    @Test
    public void vrExpController_getVrExpTitle() {
        VrExperienceController controller = new VrExperienceController();
        String expectedTitle = "my vr exp title";
        Whitebox.setInternalState(mockScript, "title", expectedTitle);
        assertEquals(expectedTitle, controller.getVrExpTitle());
    }

    @Test
    public void vrExpController_getVrSceneTitle() {
        VrExperienceController controller = new VrExperienceController();
        assertEquals("title1", controller.getVrSceneTitle());
    }

    @Test
    public void vrExpController_getAllSceneTitles() {
        VrExperienceController controller = new VrExperienceController();
        assertEquals(new ArrayList<>(Arrays.asList("title1", "title2")), controller.getAllSceneTitles());
    }

    @Test
    public void vrExpController_getVrSceneActionCount_plural() {
        VrExperienceController controller = new VrExperienceController();
        assertEquals("0 Actions", controller.getVrSceneActionCount());
    }

    @Test
    public void vrExpController_getVrSceneActionCount_singular() {
        VrExperienceController controller = new VrExperienceController();
        controller.nextScene();
        assertEquals("1 Action", controller.getVrSceneActionCount());
    }

    @Test
    public void vrExpController_getVrSceneScriptText_onlyText() {
        VrExperienceController controller = new VrExperienceController();
        Stream<CharSequence> actual = controller.getVrSceneScriptText();
        assertEquals("text1", actual.collect(Collectors.joining()));
    }

    @Test
    public void vrExpController_getVrSceneScriptText_textAndAction() {
        VrExperienceController controller = new VrExperienceController();
        controller.nextScene();

        // Go around mocking SpannableString class by using a Spy and returning a String
        ScriptText spyAction = injectSceneList.get(1).script.get(1);
        doReturn("action1").when(spyAction).toCharSequence();

        Stream<CharSequence> actual = controller.getVrSceneScriptText();
        assertEquals("text1action1text2", actual.collect(Collectors.joining()));
    }
}