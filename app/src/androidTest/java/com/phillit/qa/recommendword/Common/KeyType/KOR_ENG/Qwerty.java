package com.phillit.qa.recommendword.Common.KeyType.KOR_ENG;

import android.util.Log;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;
import com.phillit.qa.recommendword.Common.RecommendWordParse.Keyboard;
import com.phillit.qa.recommendword.Common.SeparateKorean;
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
    private SeparateKorean separater;
    private int testType;

    public Qwerty(Device device, Keyboard keyboard, int screenOrientation, int language){
        this.device = device;
        this.language = language;
        parser = new XmlParser(device.getContext(), screenOrientation, language, device);
        buffer = new StringBuffer();
        testType = device.getKeyboardType();
        this.keyboard = keyboard;

        // 문자키, 특수문자키를 XML로부터 읽어온다
        normalKeyList = parser.getKeyList();
        specialKeyList = parser.getSpecialKeyList();
        specialCharacter = new Qwerty_Special_Character(device, device.getContext(), screenOrientation, language, specialKeyList);

        // 단어입력 후 띄어쓰기를 위해 Spacebar의 좌표를 Preload
        spacebar_x = normalKeyList.get("^").keyCordinates.get(0).x;
        spacebar_y = normalKeyList.get("^").keyCordinates.get(0).y;

        // 영문의 경우 대문자 입력을 위해 Shift의 좌표를 Preload
        shift_x = normalKeyList.get("↑").keyCordinates.get(0).x;
        shift_y = normalKeyList.get("↑").keyCordinates.get(0).y;


        if(language == KeyType.KOR_QWERTY){
            separater = new SeparateKorean();
        }
    }

    @Override
    public void input(String args) {
        arrChar = args.toCharArray();
        typingKeyboard(arrChar);
    }

    @Override
    public void input(StringBuffer args) {
        typingKeyboard(args);
        device.clickAndCount(spacebar_x, spacebar_y);
    }

    private void typingKeyboard(char[] arrChar){
        boolean isCurrentWordContainsEndString= false;
        boolean isChangeLanguage = false;   // 한글TC인데 영문이 포함될 경우
        int lastIndex = 0;
        Key key = null;
        Key.keyCordinate recommendKey = null;

        if(language == KeyType.KOR_QWERTY){
            arrChar = separater.Convert(String.valueOf(arrChar)).toCharArray();
        }

        if((lastIndex = isCurWordLastIndexEndString(arrChar)) != -1){
            isCurrentWordContainsEndString = true;
        }

        for(int i=0; i<arrChar.length; i++){
            String targetChar = String.valueOf(arrChar[i]);

            isChangeLanguage = false;
            isSpecialChar = device.isSpecialCharacter(targetChar);
            Log.i("@@@", "TargetChar : " + targetChar + " / isSpecialChar : " + isSpecialChar);

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
                                device.userWait(1000);   // 간헐적으로 Shift가 씹히는현상으로 Delay 추가
                            }else{
                                key = normalKeyList.get(targetChar.toLowerCase());
                            }
                        }
                    }
                }else{
                    // 한글 KSR_LG 473단어('g6') 로 인해 영문자 입력에 대한 하드코딩
                    if(language == KOR_QWERTY && targetChar.equals("g")){
                        isChangeLanguage = true;
                        key = normalKeyList.get("ㅎ");
                    }else if(language == KOR_QWERTY && targetChar.equals("A")){
                        isChangeLanguage = true;
                        key = normalKeyList.get("ㅁ");
                    }else if(language == KOR_QWERTY && targetChar.equals("I")){
                        isChangeLanguage = true;
                        key = normalKeyList.get("ㅑ");
                    }else if(language == KOR_QWERTY && targetChar.equals("N")){
                        isChangeLanguage = true;
                        key = normalKeyList.get("ㅜ");
                    }else{
                        Log.i("@@@", "asdfasdfasdfasdf");
                        key = normalKeyList.get(targetChar);
                    }
                }

                // 터치부문
                if(!key.Longclickable){
                    for(int j=0; j<key.keyCordinates.size(); j++){
                        device.userWait(500);
                        try{
                            recommendKey = keyboard.searchKeyboardView(currentWord);
                            if(recommendKey != null && !isCurrentWordContainsEndString){
                                device.clickAndCount(recommendKey.x, recommendKey.y);
                                return;
                            }else if(isCurrentWordContainsEndString && recommendKey == null){
                                // 단어 마지막에 끝맺음 문자가 포함될 경우 끝맺음 문자와 단어를 분리한다.
                                // 분리 후 단어만 추천단어바에서 검색한다. 추천단어에서 입력됬을 경우 반복문의 count를 끝맺음 문자부터 재시작한다.
                                String delSpecialChar = "";
                                if(language == KeyType.ENG_QWERTY){
                                    delSpecialChar = String.valueOf(arrChar).substring(0,lastIndex);
                                    recommendKey = keyboard.searchKeyboardView(delSpecialChar);
                                }else if(language == KeyType.KOR_QWERTY){
                                    delSpecialChar = currentWord.substring(0, isCurWordLastIndexEndString(currentWord.toCharArray()));
                                    recommendKey = keyboard.searchKeyboardView(delSpecialChar);
                                    Log.i("@@@", delSpecialChar);
                                }
                                if(recommendKey != null){
                                    device.clickAndCount(recommendKey.x, recommendKey.y);
                                    device.userWait(1000);
                                    i = lastIndex-1;
                                    break;
                                }
                            }
                        }catch (NullPointerException e){
                            Log.i("@@@", "recommendKey is null...");
                            e.printStackTrace();
                        }

                        // 한글 TestCase에 영어가 있을경우 언어변경 후 입력한다. 이후 다시 한글 자판으로 복귀한다.
                        if(isChangeLanguage){
                            // 전단어에 끝맺음 문자가 포함되지 않고 입력할 문자가 대문자가 아닐 경우
                            if(!isPrevWordContainsEndString() && !isUpper(targetChar)){
                                //Log.i("@@@", "전단어에 끝맺음 문자가 포함되지 않고 입력할 문자가 대문자가 아닐 경우" + " / x:" + key.keyCordinates.get(j).x+ " / y:" + key.keyCordinates.get(j).y);
                                keyboard.changeLanguage();
                                device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y);
                                keyboard.changeLanguage();
                            }
                            // 전 단어에 끝맺음 문자가 포함되지 않고 대문자의 경우
                            else if(!isPrevWordContainsEndString() && isUpper(targetChar)){
                                //Log.i("@@@", "전 단어에 끝맺음 문자가 포함되지 않고 대문자의 경우" + " / x:" + key.keyCordinates.get(j).x+ " / y:" + key.keyCordinates.get(j).y);
                                keyboard.changeLanguage();
                                device.clickAndCount(shift_x, shift_y); device.userWait(500);
                                device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y); device.userWait(500);
                                keyboard.changeLanguage();
                            }
                            // 전 단어에 끝맺음 문자가 포함되지만 첫글자가 아닐때
                            else if(isPrevWordContainsEndString() && i != 0){
                                //Log.i("@@@", "전 단어에 끝맺음 문자가 포함되지만 첫글자가 아닐때" + " / x:" + key.keyCordinates.get(j).x+ " / y:" + key.keyCordinates.get(j).y);
                                keyboard.changeLanguage();
                                device.clickAndCount(shift_x, shift_y); device.userWait(500);
                                device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y); device.userWait(500);
                                keyboard.changeLanguage();
                            }else if(isPrevWordContainsEndString() && i==0){
                                keyboard.changeLanguage();
                                device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y); device.userWait(500);
                                keyboard.changeLanguage();
                            }
                        }else{
                            device.clickAndCount(key.keyCordinates.get(j).x, key.keyCordinates.get(j).y);
                        }
                        device.userWait(500);
                    }
                }else{
                    device.swipeAndCount(key.keyCordinates.get(0).x, key.keyCordinates.get(0).y, key.keyCordinates.get(1).x, key.keyCordinates.get(1).y);
                }
            }
            // 특수문자의 경우
            else{
                device.userWait(500);
                specialCharacter.input(targetChar);
            }
        }
        // 삼성 키보드 :: 추천단어를 누르지 못하고 풀타로 입력했을 경우 스페이스바를 수동으로 입력한다.
        if((device.getKeyboardType() == KeyboardType.S10P_KEYBOARD_SAMSUNG && recommendKey == null)){
            device.userWait(500);
            device.clickAndCount(spacebar_x, spacebar_y);
            device.userWait(500);
        }
        // SwiftKeyboard :: 추천단어를 누르지 못하고 풀타로 입력했고 끝맺음 문자가 없을경우 스페이스바를 누른다.
        // 끝맺음 문자가 포함될 경우 스페이스바를 누르지 않는다
        else if(device.getKeyboardType() == KeyboardType.G6_KEYBOARD_SWIFT){
            if(!isCurrentWordContainsEndString){
                device.clickAndCount(spacebar_x, spacebar_y);
                device.userWait(500);
            }
        }
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
                    || String.valueOf(prevWord.charAt(prevWord.length()-1)).equals("!")
                    || String.valueOf(prevWord.charAt(prevWord.length()-1)).equals("…")){
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

    // 끝맺음 문자가 몇번쨰 Index인지 Return한다.
    public int isCurWordLastIndexEndString(char[] str){
        String temp = String.valueOf(str);
        boolean isLast1 = false, isLast2 = false;
        int index = -1;

        try{
            String last2 =  String.valueOf(temp.charAt(temp.length()-2));
            String last1 =  String.valueOf(temp.charAt(temp.length()-1));

            if(last1.equals(",") || last1.equals(".") || last1.equals("?") || last1.equals("?") || last1.equals("!")){
                isLast1 = true;
            }else if(last2.equals(",") || last2.equals(".") || last2.equals("?") || last2.equals("?") || last2.equals("!")){
                isLast2 = true;
            }

            if(isLast1 && isLast2){
                index =  temp.length()-2;
            }else if(isLast1){
                index = temp.length()-1;
            }
        }catch (StringIndexOutOfBoundsException e){

        }
        return index;
    }
}