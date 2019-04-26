package com.phillit.qa.recommendword.Common.TestCase;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import com.phillit.qa.recommendword.BuildConfig;
import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.KeyType.KOR_ENG.Qwerty;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.SamsungKeyboard;
import com.phillit.qa.recommendword.Common.TestCaseParser;

public class TC03_SamsungKeyboard_S10P_ENG extends BaseTestCase {
    private Device device;
    private SamsungKeyboard samsungKeyboard;
    private TestCaseParser testCaseParser;
    private Qwerty qwerty_eng;
    private StringBuffer word;
    private String testName = "";
    private String toucchCount, touchCount = "";

    public TC03_SamsungKeyboard_S10P_ENG(Device device){
        this.device = device;
        testCaseParser = new TestCaseParser("ENG", device);
        samsungKeyboard = new SamsungKeyboard(device);
        qwerty_eng = new Qwerty(device, samsungKeyboard, KeyType.PORTRAIT, KeyType.ENG_QWERTY);
        testName = device.getDeviceModelName() + "_" + "ENG_" + device.getKeyboardTypeToString() + "_" + device.getTestTypeToString();
    }

    @Override
    public void ReadyTest() throws UiObjectNotFoundException {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "ReadyTest()");
        }

        // Monkey Input 실행 후 키보드 Visible
        device.launchMonkeyInput(testName);
    }

    @Override
    public void Test() {
        int i = 3;
        if(BuildConfig.DEBUG){
            Log.i("@@@", "Test()");
        }
        try{
            while(true){
                word = testCaseParser.getWord(i);
                if(word == null){
                    break;
                }else{
                    qwerty_eng.currentWord = String.valueOf(word);
                    device.inputMethod(String.valueOf(word), qwerty_eng);
                    qwerty_eng.prevWord = String.valueOf(word);
                    touchCount += device.typing_count + " ";
                    Log.i("@@@", "Num : "+ (i-2) + " / Input Word : " + word + " / Touch Cnt : " + device.typing_count);
                    device.typing_count = 0;
                }
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void FinishTest() throws UiObjectNotFoundException {
        UiObject object;
        if(BuildConfig.DEBUG){
            Log.i("@@@", "FinishTest()");
        }

        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME);
        device.touchObject("com.phillit.qa.monkeyinput:id/btn_save");
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME);

        object = new UiObject(new UiSelector().resourceId("com.phillit.qa.monkeyinput:id/edt_input"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click(); device.userWait(1000);
        }

        object.setText(touchCount); device.userWait(1000);
        device.touchObject("com.phillit.qa.monkeyinput:id/btn_save");

        device.goToIdle();

        try {
            samsungKeyboard.resetKeyboard();
        } catch (UiObjectNotFoundException e) {
            Log.d("@@@", "resetSamsungKeyboars is Fail...");
            e.printStackTrace();
        }
    }
}
