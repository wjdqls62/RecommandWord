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
import com.phillit.qa.recommendword.Common.KeyboardType.Gboard;
import com.phillit.qa.recommendword.Common.RecommendWordParse.Keyboard;
import com.phillit.qa.recommendword.Common.SeparateKorean;
import com.phillit.qa.recommendword.Common.TestCaseParser;

public class TC06_Gboard_G6_KOR extends BaseTestCase {
    private Device device;
    private Keyboard gboard;
    private TestCaseParser testCaseParser;
    private Qwerty qwerty_kor;
    private SeparateKorean separater;
    private StringBuffer word;
    private String testName, touchCount = "";

    public TC06_Gboard_G6_KOR(Device device){
        this.device = device;
        testCaseParser = new TestCaseParser("KOR", device);
        gboard = new Gboard(device);
        qwerty_kor = new Qwerty(device, gboard, KeyType.PORTRAIT, KeyType.KOR_QWERTY);
        separater = new SeparateKorean();
        testName = device.getDeviceModelName() + "_" + "KOR_" + device.getKeyboardTypeToString() + "_" + device.getTestTypeToString();
    }

    @Override
    public void ReadyTest() throws UiObjectNotFoundException {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "ReadyTest()");
        }

        // Monkey Input 실행 후 키보드 Visible
        device.launchMonkeyInput(testName);

        // 한국어 -> 영어
        gboard.changeLanguage();
        // 5초 대기
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME);
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
                    qwerty_kor.currentWord = String.valueOf(word);
                    device.inputMethod(String.valueOf(word), qwerty_kor);
                    qwerty_kor.prevWord = String.valueOf(word);
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

        gboard.changeLanguage();   // 한국어 -> 영어

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
            gboard.resetKeyboard();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }
}