package com.phillit.qa.recommendword;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObjectNotFoundException;
import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;
import com.phillit.qa.recommendword.Common.TestCase.TC05_Gboard_G6_ENG;
import com.phillit.qa.recommendword.Common.TestCase.TC01_SwiftKeyboard_G6_ENG;
import com.phillit.qa.recommendword.Common.TestCase.TC02_SwiftKeyboard_G6_KOR;
import com.phillit.qa.recommendword.Common.TestCase.TC03_SamsungKeyboard_S10P_ENG;
import com.phillit.qa.recommendword.Common.TestCase.TC04_SamsungKeyboard_S10P_KOR;
import com.phillit.qa.recommendword.Common.TestCase.TC06_Gboard_G6_KOR;
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
        device = new Device(uiDevice, context);
    }

    @Test
    public void Test() throws UiObjectNotFoundException {
        device.setKeyboard_G6(KeyboardType.G6_KEYBOARD_NAVER);

        //Start_GBoard_G6();
        //Start_SwiftKeyboard_G6();
        //Start_SamsungKeyboard_GS10P();
    }

    @After
    public void FinishTest() throws IOException {
        device.userWait(10000);
        //device.PowerOff();
    }

    private void Start_SwiftKeyboard_G6() throws UiObjectNotFoundException {
        // TestCase01
        device.setKeyboardType(KeyboardType.G6_KEYBOARD_SWIFT);
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC01_SwiftKeyboard_G6_ENG TC01 = new TC01_SwiftKeyboard_G6_ENG(device);
        TC01.Start();
        device.setTestType(Configuration.KSR_LG);
        TC01 = new TC01_SwiftKeyboard_G6_ENG(device);
        TC01.Start();

        // TestCase02
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC02_SwiftKeyboard_G6_KOR TC02 = new TC02_SwiftKeyboard_G6_KOR(device);
        TC02.Start();
        device.setTestType(Configuration.KSR_LG);
        TC02 = new TC02_SwiftKeyboard_G6_KOR(device);
        TC02.Start();
    }

    private void Start_SamsungKeyboard_GS10P() throws UiObjectNotFoundException {
        // TestCase03
        device.setKeyboardType(KeyboardType.S10P_KEYBOARD_SAMSUNG);
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC03_SamsungKeyboard_S10P_ENG TC03 = new TC03_SamsungKeyboard_S10P_ENG(device);
        TC03.Start();
        device.setTestType(Configuration.KSR_LG);
        TC03 = new TC03_SamsungKeyboard_S10P_ENG(device);
        TC03.Start();

        // TestCase04
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC04_SamsungKeyboard_S10P_KOR TC04 = new TC04_SamsungKeyboard_S10P_KOR(device);
        TC04.Start();
        device.setTestType(Configuration.KSR_LG);
        TC04 = new TC04_SamsungKeyboard_S10P_KOR(device);
        TC04.Start();
    }

    private void Start_GBoard_G6() throws UiObjectNotFoundException {
        // TestCase05
        device.setKeyboardType(KeyboardType.G6_KEYBOARD_GBOARD);
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC05_Gboard_G6_ENG TC05 = new TC05_Gboard_G6_ENG(device);
        TC05.Start();
        device.setTestType(Configuration.KSR_LG);
        TC05 = new TC05_Gboard_G6_ENG(device);
        TC05.Start();

        // TestCase06
        device.setTestType(Configuration.KSR_CONVERSATION);
        TC06_Gboard_G6_KOR TC06 = new TC06_Gboard_G6_KOR(device);
        TC06.Start();
        device.setTestType(Configuration.KSR_LG);
        TC06 = new TC06_Gboard_G6_KOR(device);
        TC06.Start();
    }
}