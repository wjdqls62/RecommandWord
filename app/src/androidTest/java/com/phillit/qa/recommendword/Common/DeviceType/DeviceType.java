package com.phillit.qa.recommendword.Common.DeviceType;

/**
 * 각 Class에서 진행 모델명을 구분할때 사용한다.
 * adb shell getprop [ro.product.model] 의 String을 변수값으로 정의한다.
 */

public abstract class DeviceType {
    public static final String G6 = "LGM-G600S";
}