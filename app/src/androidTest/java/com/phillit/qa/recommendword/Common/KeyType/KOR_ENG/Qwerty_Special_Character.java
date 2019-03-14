package com.phillit.qa.recommendword.Common.KeyType.KOR_ENG;

import android.content.Context;

import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;

import java.util.HashMap;

public class Qwerty_Special_Character extends KeyType {
    private Device device;
    private Context context;
    private int screenOrientation, language;
    private HashMap<String, Key> specialKeyList;
    private Key key;

    public Qwerty_Special_Character(Device device, Context context, int screenOrientation, int language, HashMap<String, Key> specialKeyList){
        this.device = device;
        this.context = context;
        this.screenOrientation = screenOrientation;
        this.language = language;
        this.specialKeyList = specialKeyList;
    }

    @Override
    public void input(String args) {
        String targetChar = args;
        Nexus5_specialKeyList_Btn(screenOrientation, language);
        key = specialKeyList.get(targetChar);
        if(key != null){
            if(!key.Longclickable){
                for(int k=0; k<key.keyCordinates.size(); k++){
                    device.clickAndCount(key.keyCordinates.get(k).x, key.keyCordinates.get(k).y);
                }
            }else{
                device.swipeAndCount(key.keyCordinates.get(0).x, key.keyCordinates.get(0).y, key.keyCordinates.get(1).x, key.keyCordinates.get(1).y);
            }
        }else{
            //넥서스5 하드코딩
            Nexus5(targetChar);
        }
        device.userWait(50);
    }

    @Override
    public void input(StringBuffer args) {

    }

    // 넥서스5 특수문자 페이지 버튼튼
   private void Nexus5_specialKeyList_Btn(int screenOrientation, int keyType){
        // 세로모드
        if(screenOrientation == KeyType.PORTRAIT){
            if(keyType == KeyType.KOR_SKY){
                device.getUiDevice().click(865, 1500);
            }else{
                device.getUiDevice().click(70, 1690);
            }
        }
        // 가로모드
        else{
            if(keyType == KeyType.KOR_SKY){
                device.getUiDevice().click(1450,850);
            }else{
                device.getUiDevice().click(120,1015);
            }

        }
    }

    // 넥서스5 XML예약어 특수문자 수동삽입
    private void Nexus5(String targetChar){
        // 세로모드
        if(screenOrientation == KeyType.PORTRAIT){
            // &
            if(targetChar.equals("&")){
                device.getUiDevice().click(375, 1400);
                device.getUiDevice().click(70, 1690);
            // ￦
            }else if(targetChar.equals("￦")){
                // 원화, 달러표기의 경우 현재 자판의 언어에 따라 표기페이지가 상이하여 조건문 분기
                if(language == KeyType.KOR_QWERTY || language == KeyType.KOR_CHUNJIIN || language == KeyType.KOR_SKY || language == KeyType.KOR_NARAGUL || language == KeyType.KOR_DANMOUM){
                    device.getUiDevice().click(1015, 1260);
                    device.getUiDevice().click(70, 1690);
                }else if(language == KeyType.ENG_QWERTY){
                    device.getUiDevice().click(70,1545);
                    device.getUiDevice().click(650,1545);
                    device.getUiDevice().click(70, 1690);
                }
            }else if(targetChar.equals("$")){
                if(language == KeyType.KOR_QWERTY || language == KeyType.KOR_CHUNJIIN || language == KeyType.KOR_SKY || language == KeyType.KOR_NARAGUL || language == KeyType.KOR_DANMOUM){
                    device.getUiDevice().click(70,1545);
                    device.getUiDevice().click(650,1545);
                    device.getUiDevice().click(70, 1690);
                }else if(language == KeyType.ENG_QWERTY){
                    device.getUiDevice().click(275, 1400);
                    device.getUiDevice().click(70, 1690);
                }
            // <
            }else if(targetChar.equals("<")){
                device.getUiDevice().click(70,1545);
                device.getUiDevice().click(60,1400);
                device.getUiDevice().click(70, 1690);
            }
            // >
            else if(targetChar.equals(">")){
                device.getUiDevice().click(70,1545);
                device.getUiDevice().click(165,1400);
                device.getUiDevice().click(70, 1690);
            }
        }else if(screenOrientation == KeyType.LANDSCAPE){
            // &
            if(targetChar.equals("&")){
                device.getUiDevice().click(625, 780);
                device.getUiDevice().click(120, 1020);
                // ￦
            }else if(targetChar.equals("￦")){
                // 원화, 달러표기의 경우 현재 자판의 언어에 따라 표기페이지가 상이하여 조건문 분기
                if(language == KeyType.KOR_QWERTY || language == KeyType.KOR_CHUNJIIN || language == KeyType.KOR_SKY || language == KeyType.KOR_NARAGUL || language == KeyType.KOR_DANMOUM){
                    device.getUiDevice().click(450, 780);
                    device.getUiDevice().click(120, 1020);
                }else if(language == KeyType.ENG_QWERTY){
                    device.getUiDevice().click(120,900);
                    device.getUiDevice().click(1075,900);
                    device.getUiDevice().click(120, 1020);
                }
            }else if(targetChar.equals("$")){
                if(language == KeyType.KOR_QWERTY || language == KeyType.KOR_CHUNJIIN || language == KeyType.KOR_SKY || language == KeyType.KOR_NARAGUL || language == KeyType.KOR_DANMOUM){
                    device.getUiDevice().click(120,900);
                    device.getUiDevice().click(1075,900);
                    device.getUiDevice().click(120, 1020);
                }else if(language == KeyType.ENG_QWERTY){
                    device.getUiDevice().click(450, 780);
                    device.getUiDevice().click(120, 1020);
                }
                // <
            }else if(targetChar.equals("<")){
                device.getUiDevice().click(120,900);
                device.getUiDevice().click(95,785);
                device.getUiDevice().click(120, 1020);
            }
            // >
            else if(targetChar.equals(">")){
                device.getUiDevice().click(120,900);
                device.getUiDevice().click(275,785);
                device.getUiDevice().click(120, 1020);
            }
        }
    }
}
