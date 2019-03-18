package com.phillit.qa.recommendword.Common.TestCase;

import android.util.Log;
import com.phillit.qa.recommendword.BuildConfig;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.KeyType.KOR_ENG.Qwerty;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.SwiftKeyboard;
import com.phillit.qa.recommendword.Common.TestCaseParser;

public class TC01_SwiftKeyboard_G6_ENG extends BaseTestCase {
    private Device device;
    private SwiftKeyboard swiftKeyboard;
    private TestCaseParser testCaseParser;
    private Qwerty qwerty_eng;
    private StringBuffer word;

    public TC01_SwiftKeyboard_G6_ENG(Device device){
        this.device = device;
        testCaseParser = new TestCaseParser("ENG", device);
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
    public void FinishTest() {
        if(BuildConfig.DEBUG){
            Log.i("@@@", "FinishTest()");
        }
    }
}