package com.phillit.qa.recommendword.Common.TestCase;

public abstract class BaseTestCase {

    public abstract void ReadyTest();
    public abstract void Test();
    public abstract void FinishTest();
    public void Start(){
        ReadyTest();
        Test();
        FinishTest();
    }
}
