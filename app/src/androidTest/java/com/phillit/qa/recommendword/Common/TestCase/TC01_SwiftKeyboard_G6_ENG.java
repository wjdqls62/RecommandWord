package com.phillit.qa.recommendword.Common.TestCase;

import android.util.Log;
import com.phillit.qa.recommendword.BuildConfig;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.KeyType.KOR_ENG.Qwerty;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.SwiftKeyboard;

public class TC01_SwiftKeyboard_G6_ENG extends BaseTestCase {
    private Device device;
    private SwiftKeyboard swiftKeyboard;
    private Qwerty qwerty_eng;

    public TC01_SwiftKeyboard_G6_ENG(Device device){
        this.device = device;
        swiftKeyboard = new SwiftKeyboard(device);
        qwerty_eng = new Qwerty(device, swiftKeyboard, KeyType.PORTRAIT, KeyType.ENG_QWERTY);
    }

    @Override
    public void ReadyTest() {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "ReadyTest()");
        }
    }

    @Override
    public void Test() {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "Test()");
        }
        String[] targetStr = {"I", "love", "you.", "This", "is", "a", "boy?", "We'll", "the", "Change", "World!"};

        for(int i=0; i<targetStr.length; i++){
            qwerty_eng.currentWord = targetStr[i];
            device.inputMethod(targetStr[i], qwerty_eng);
            qwerty_eng.prevWord = targetStr[i];
            Log.i("@@@", "Input Word : " + targetStr[i] + " / Touch Cnt : " + device.typing_count);
            device.typing_count = 0;
        }
    }

    @Override
    public void FinishTest() {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "FinishTest()");
        }
    }
}