package com.phillit.qa.recommendword.Common.KeyType.KOR_ENG;

import android.util.Log;

import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.SwiftKeyboard;
import com.phillit.qa.recommendword.Common.RecommendWordParse.Keyboard;
import com.phillit.qa.recommendword.Common.XmlParser;
import java.util.HashMap;

public class Qwerty extends KeyType {
    private Device device;
    private HashMap<String, Key> normalKeyList, specialKeyList;
    private XmlParser parser;
    private StringBuffer buffer;
    public String currentWord, prevWord = "";
    private Keyboard keyboard;
    private char[] arrChar;
    private Qwerty_Special_Character specialCharacter;
    private int spacebar_x, spacebar_y, language;
    private int shift_x, shift_y;
    boolean isSpecialChar = false;
    private int testType;

    public Qwerty(Device device, Keyboard keyboard, int screenOrientation, int language){
        this.device = device;
        this.language = language;
        parser = new XmlParser(device.getContext(), screenOrientation, language, device);
        buffer = new StringBuffer();
        testType = device.getTestType();
        this.keyboard = keyboard;

        // 문자키, 특수문자키를 XML로부터 읽어온다
        normalKeyList = parser.getKeyList();
        specialKeyList = parser.getSpecialKeyList();
        specialCharacter = new Qwerty_Special_Character(device, device.getContext(), screenOrientation, language, specialKeyList);

        // 단어입력 후 띄어쓰기를 위해 Spacebar의 좌표를 Preload
        spacebar_x = normalKeyList.get("^").keyCordinates.get(0).x;
        spacebar_y = normalKeyList.get("^").keyCordinates.get(0).y;

        // 영문의 경우 대문자 입력을 위해 Shift의 좌표를 Preload
        if(language == KeyType.ENG_QWERTY){
            shift_x = normalKeyList.get("↑").keyCordinates.get(0).x;
            shift_y = normalKeyList.get("↑").keyCordinates.get(0).y;
        }
    }

    @Override
    public void input(String args) {
        arrChar = args.toCharArray();
        typingKeyboard(arrChar);

        //device.getUiDevice().click(spacebar_x, spacebar_y);
    }

    @Override
    public void input(StringBuffer args) {
        typingKeyboard(args);
        device.clickAndCount(spacebar_x, spacebar_y);
        //device.getUiDevice().click(spacebar_x, spacebar_y);
    }

    private void typingKeyboard(char[] arrChar){
        boolean isCurrentWordContainsEndString= false;
        String separateEndStringWord = "";
        Key key = null;
        Key.keyCordinate recommendKey = null;


        for(int i=0; i<arrChar.length; i++){
            String targetChar = String.valueOf(arrChar[i]);

            isSpecialChar = device.isSpecialCharacter(targetChar);

            // Key값 삽입
            // 특수문자가 아닐경우
            if(!isSpecialChar){
                // 영문인데 대문자의 경우
                if(language == KeyType.ENG_QWERTY && isUpper(targetChar)){
                    // 대문자인데 단어의 첫문자 입력이 아닌경우
                    if(i != 0){
                        device.clickAndCount(shift_x, shift_y);
                        key = normalKeyList.get(targetChar.toLowerCase());
                    }else{
                        if(i == 0 && prevWord.equals("")){
                            key = normalKeyList.get(targetChar.toLowerCase());
                        }else{
                            // TestCase 첫 단어
                            if(!isPrevWordContainsEndString()){
                                device.clickAndCount(shift_x, shift_y);
                                key = normalKeyList.get(targetChar.toLowerCase());
                            }else{
                                key = normalKeyList.get(targetChar.toLowerCase());
                            }
                        }
                    }
                }else{
                    key = normalKeyList.get(targetChar);
                }


                // 터치부문
                if(!key.Longclickable){
                    for(int j=0; j<key.keyCordinates.size(); j++){
                        try{
                            recommendKey = keyboard.searchKeyboardView(currentWord);
                            if(recommendKey != null){
                                device.clickAndCount(recommendKey.x, recommendKey.y);
                                return;
                            }
                        }catch (NullPointerException e){
                            Log.i("@@@", "recommendKey is null...");
                            e.printStackTrace();
                        }
                        device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y);
                        device.userWait(1000);
                    }
                }else{
                    device.swipeAndCount(key.keyCordinates.get(0).x, key.keyCordinates.get(0).y, key.keyCordinates.get(1).x, key.keyCordinates.get(1).y);
                }
            }
            // 특수문자의 경우
            else{
                specialCharacter.input(targetChar);
            }
        }
        device.clickAndCount(spacebar_x, spacebar_y);
        device.userWait(500);
    }

    // 대문자 체크
    private boolean isUpper(String str){
        return str.matches("^[A-Z]*$");
    }

    private boolean isPrevWordContainsEndString(){
        Log.i("@@@", "isPrevWordContainsEndString()");
        boolean result = false;
        if(prevWord != ""){
            if(String.valueOf(prevWord.charAt(prevWord.length()-1)).equals(".")
                    || String.valueOf(prevWord.charAt(prevWord.length()-1)).equals(",")
                    || String.valueOf(prevWord.charAt(prevWord.length()-1)).equals("?")
                    || String.valueOf(prevWord.charAt(prevWord.length()-1)).equals("!")){
                Log.i("@@@", "이전단어에 문장마침표 있음");
                result = true;
            }
        }
        return result;
    }

    private void typingKeyboard(StringBuffer arrChar){
        Key key;
        String targetChar = "";

        for(int i=0; i<arrChar.length(); i++){
            targetChar = String.valueOf(arrChar.charAt(i));

            isSpecialChar = device.isSpecialCharacter(targetChar);

            // 특수문자가 아닐경우
            if(!isSpecialChar){
                // 영문인데 대문자의 경우
                if(language == KeyType.ENG_QWERTY && isUpper(targetChar)){
                    //device.getUiDevice().click(shift_x, shift_y);
                    device.clickAndCount(shift_x, shift_y);
                    key = normalKeyList.get(targetChar.toLowerCase());
                }else{
                    key = normalKeyList.get(targetChar);
                }

                if(!key.Longclickable){
                    for(int j=0; j<key.keyCordinates.size(); j++){
                        //device.getUiDevice().click(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y);
                        device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y);
                    }
                }else{
                    device.swipeAndCount(key.keyCordinates.get(0).x, key.keyCordinates.get(0).y, key.keyCordinates.get(1).x, key.keyCordinates.get(1).y);
                }
            }
            // 특수문자의 경우
            else{
                specialCharacter.input(targetChar);
            }
        }
        device.userWait(500);
    }

    public void isCurWordLastIndexEndString(char[] str){
        String temp = String.valueOf(str);

        String last2 =  String.valueOf(temp.charAt(temp.length()-2));
        String last1 =  String.valueOf(temp.charAt(temp.length()-1));

    }
}