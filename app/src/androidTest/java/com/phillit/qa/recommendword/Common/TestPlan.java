package com.phillit.qa.recommendword.Common;
/*
엑셀의 환경설정탭에서 테스트 여부를 TestPlan class에 저장하여 Main함수에서 함수 호출여부를 결정한다.
 */

public class TestPlan {
    public boolean isInternalTest = false;

    // 한글 QWERTY
    public static boolean KOR_QWERTY_PORTRAIT = false;
    public static boolean KOR_QWERTY_LANDSCAPE = false;

    // 한글 천지인
    public static boolean KOR_CHUNJIIN_PORTRAIT = false;
    public static boolean KOR_CHUNJIIN_LANDSCAPE = false;

    // 한글 스카이
    public static boolean KOR_SKY_PORTRAIT = false;
    public static boolean KOR_SKY_LANDSCAPE = false;

    // 한글 나랏글
    public static boolean KOR_NARAGUL_PORTRAIT = false;
    public static boolean KOR_NARAGUL_LANDSCAPE = false;

    // 한글 단모음
    public static boolean KOR_DANMOUM_PORTRAIT = false;
    public static boolean KOR_DANMOUM_LANDSCAPE = false;

    // 영문 키타입
    public static boolean ENG_QWERTY_PORTRAIT = false;
    public static boolean ENG_QWERTY_LANDSCAPE = false;
}
