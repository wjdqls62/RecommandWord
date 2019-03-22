package com.phillit.qa.recommendword.Common.TestCase;

import android.support.test.uiautomator.UiObjectNotFoundException;

public abstract class BaseTestCase {

    public abstract void ReadyTest() throws UiObjectNotFoundException;
    public abstract void Test();
    public abstract void FinishTest() throws UiObjectNotFoundException;
    public void Start() throws UiObjectNotFoundException {
        ReadyTest();
        Test();
        FinishTest();
    }
}
