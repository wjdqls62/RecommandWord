package com.phillit.qa.recommendword.Common.KeyboardType;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;

public class Gboard extends SwiftKeyboard {
    private Device device;

    public Gboard(Device device) {
        super(device);
        this.device = device;
    }

    @Override
    public void changeLanguage(){
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME / 2);
        device.getUiDevice().click(Configuration.GBOARD_CHANGE_LANGUAGE_X, Configuration.GBOARD_CHANGE_LANGUAGE_Y);  // Gboard(G6) 언어변경 버튼
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME / 2);
    }

    @Override
    public void resetKeyboard() throws UiObjectNotFoundException {
        UiObject object;
        String captcha = "";
        device.launchApplication("Gboard");
        device.touchText("사전");
        device.touchText("학습한 단어 삭제");

        object = device.getUiDevice().findObject(new UiSelector().resourceId("com.google.android.inputmethod.latin:id/input"));
        if(object.waitForExists(5000)){
            captcha = device.getUiDevice().findObject(new UiSelector().resourceId("com.google.android.inputmethod.latin:id/captcha")).getText();
            object.setText(captcha);

            device.touchText("확인");
        }

        device.goToIdle();

    }
}
