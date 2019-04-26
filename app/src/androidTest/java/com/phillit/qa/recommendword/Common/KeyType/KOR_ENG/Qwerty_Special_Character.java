package com.phillit.qa.recommendword.Common.KeyType.KOR_ENG;

import android.content.Context;

import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;

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
        //Nexus5_specialKeyList_Btn(screenOrientation, language);
        key = specialKeyList.get(targetChar);
        if(key != null){
            if(!key.Longclickable){
                for(int k=0; k<key.keyCordinates.size(); k++){
                    device.clickAndCount(key.keyCordinates.get(k).x, key.keyCordinates.get(k).y);
                }
            }else{
                device.swipeAndCount(key.keyCordinates.get(0).x, key.keyCordinates.get(0).y, key.keyCordinates.get(1).x, key.keyCordinates.get(1).y);
            }
        }
        device.userWait(50);
    }

    @Override
    public void input(StringBuffer args) {

    }
}