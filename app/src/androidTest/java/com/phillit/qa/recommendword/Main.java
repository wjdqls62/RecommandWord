package com.phillit.qa.recommendword;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.KeyType.KOR_ENG.Qwerty;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;
import com.phillit.qa.recommendword.Common.TestCase.TC01_SwiftKeyboard_G6_ENG;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Main {
    private Context context;
    private Device device;
    private UiDevice uiDevice;

    @Before
    public void ReadyTest() throws IOException {
        uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        context = InstrumentationRegistry.getTargetContext();
        device = new Device(uiDevice, context, KeyboardType.G6_KEYBOARD_SWIFT, Configuration.KSR_CONVERSATION);

    }

    @Test
    public void Test() {
        TC01_SwiftKeyboard_G6_ENG TC01 = new TC01_SwiftKeyboard_G6_ENG(device);
        TC01.Start();
    }

    @After
    public void FinishTest(){

    }
}
