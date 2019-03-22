package com.phillit.qa.recommendword.Common.Configuration;

public abstract class Configuration {
    // SwiftKeyboard Values
    public static final int[] SWIFT_KEYBOARD_AREA = {136, 1554, 1304, 1712};   // Swift Keyboard 추천단어바 영역의 좌표 (LG G6)
    public static final String SWIFTKEYBOARD_PARSE_KEYWORD1 = "android.view.View"; // Class명
    public static final String SWIFTKEYBOARD_PARSE_KEYWORD2 = "com.touchtype.swiftkey"; // Package명

    // Samsung Keyboard Values
    public static final int[] SAMSUNG_KEYBOARD_AREA = {0, 1298, 968, 1406};   // Samsung Keyboard 추천단어바 영역의 좌표 (Samsung Galaxy S10+)
    public static final String SAMSUNG_PARSE_KEYWORD1 = "android.widget.TextView"; // Class명
    public static final String SAMSUNG_PARSE_KEYWORD2 = "com.sec.android.inputmethod"; // Package명

    // Common Values
    public static final String heirarchyFilePath = "/sdcard/QA/Temp/";
    public static final String heirarchyFileName = "temp.txt";
    public static final int KSR_CONVERSATION = 1000000;
    public static final int KSR_LG = 1000001;
    public static final int DEFAULT_OBJECT_WAIT_TIME = 5000;
    public static final int BATTERY_MAX_VALUE = 40;
}