package com.phillit.qa.recommendword.Common.Configuration;

public abstract class Configuration {
    // SwiftKeyboard Values
    public static final String SWIFTKEYBOARD_PARSE_KEYWORD1 = "android.view.View"; // Class명
    public static final String SWIFTKEYBOARD_PARSE_KEYWORD2 = "com.touchtype.swiftkey"; // Package명
    public static final int KSR_CONVERSATION = 1000000;
    public static final int KSR_LG = 1000001;
    public static final int[] SWIFTKEYBOARD_AREA = {136, 1554, 1304, 1712};   // 추천단어바 영역의 좌표








    // Common Values
    public static final String heirarchyFilePath = "/sdcard/QA/Temp/";
    public static final String heirarchyFileName = "temp.txt";
    public static final int DEFAULT_OBJECT_WAIT_TIME = 5000;
    public static final int BATTERY_MAX_VALUE = 40;
}