package com.example.mobilevrlab.scenes.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.example.mobilevrlab.screens.controller.VrExperienceController;
import com.example.mobilevrlab.script.ScriptSingleton;
import com.example.mobilevrlab.script.data.VrExperience;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class VrExperienceControllerTest {

    @Mock
    VrExperience mockScript;

    @Before
    public void setup() {
        ScriptSingleton.getInstance().setVrExperience(mockScript);
    }

    @Test
    public void vrExpController_instantiation_accessesScript() {
        VrExperienceController controller = new VrExperienceController();
        assertNotNull(controller.getScript());
        assertEquals(controller.getScript(), mockScript);
    }
}
