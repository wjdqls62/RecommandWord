package com.phillit.qa.recommendword.Common.KeyType;

public abstract class KeyType {
    // Screen Orientation & KeyType
    public static final int PORTRAIT = 0;
    public static final int LANDSCAPE = 1;

    public static final int KOR_QWERTY = 100;
    public static final int ENG_QWERTY = 101;
    public static final int KOR_CHUNJIIN = 102;
    public static final int KOR_SKY = 103;
    public static final int KOR_NARAGUL = 104;
    public static final int KOR_DANMOUM = 105;

    abstract public void input(String args);
    abstract public void input(StringBuffer args);
}