package com.phillit.qa.recommendword.Common.KeyboardType;

import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;

public class SamsungKeyboard extends SwiftKeyboard{
    private Device device;
    public SamsungKeyboard(Device device) {
        super(device);
        this.device = device;
    }

    @Override
    public void changeLanguage(){
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME / 2);
        device.getUiDevice().click(225, 2040);  // GS10+ SamsungKeyboard 언어변경 버튼
        device.userWait(Configuration.DEFAULT_OBJECT_WAIT_TIME / 2);
    }

    @Override
    public void resetKeyboard() throws UiObjectNotFoundException {
        UiObject object;
        UiScrollable listView;

        device.getUiDevice().openNotification();
        device.userWait(1000);
        object = new UiObject(new UiSelector().resourceId("com.android.systemui:id/settings_button"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        listView = new UiScrollable(new UiSelector().scrollable(true));
        listView.scrollIntoView(new UiSelector().text("일반"));
        object = new UiObject(new UiSelector().text("일반"));
        device.touchObject(object);

        object = new UiObject(new UiSelector().text("언어 및 입력 방식"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        object = new UiObject(new UiSelector().text("스크린 키보드"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        object = new UiObject(new UiSelector().text("삼성 키보드"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        object = new UiObject(new UiSelector().text("설정 초기화"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        object = new UiObject(new UiSelector().text("개인 입력 데이터 삭제"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        object = new UiObject(new UiSelector().text("삭제"));
        if(object.waitForExists(Configuration.DEFAULT_OBJECT_WAIT_TIME)){
            object.click();
        }

        device.userWait(3000);

        device.goToIdle();
    }

}
