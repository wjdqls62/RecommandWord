package com.phillit.qa.recommendword.Common.RecommendWordParse;
import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RecommendWordParse {
    private Keyboard keyboardType;
    private Device device;
    private final int[] SWIFTKEYBOARD_AREA = Configuration.SWIFTKEYBOARD_AREA;

    public RecommendWordParse(Keyboard keyboard, Device device){
        this.keyboardType = keyboard;
        this.device = device;
    }

    // SwiftKeyboard는 id값이 존재하지 않기 때문에 화면상 좌표값으로 추천단어 영역에 포함되는지 확인한다.
    private boolean isContainArea(Key.keyCordinate target){
        boolean result = false;

        if(device.getKeyboardType() == KeyboardType.G6_KEYBOARD_SWIFT){
            if(target.x  >= SWIFTKEYBOARD_AREA[0] && target.x <= SWIFTKEYBOARD_AREA[2]){
                if(target.y >= SWIFTKEYBOARD_AREA[1] && target.y <= SWIFTKEYBOARD_AREA[3]){
                    result = true;
                }
            }
        }
        return result;
    }

    // 추천단어가 영역에 표시되는지 확인하고 bounds값을 계산하여 좌표값을 Return한다.
    public Key.keyCordinate searchKeyboardView(String targetStr){
        Key.keyCordinate btnCordinate = null;
        if(keyboardType.WindowCompresse()){
            try {
                FileReader fileReader = new FileReader(keyboardType.getheirarchyFile());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";

                while((line = bufferedReader.readLine()) != null){
                    if(device.getKeyboardType() == KeyboardType.G6_KEYBOARD_SWIFT){
                        // SwiftKeyboard의 경우 Class 명과 Package명이 일치하는 Line을 찾는다.
                        if(line.contains(Configuration.SWIFTKEYBOARD_PARSE_KEYWORD1) && line.contains(Configuration.SWIFTKEYBOARD_PARSE_KEYWORD2)){
                            // content-desc의 값이 일치하는지 확인한다.
                            if(targetStr.equals(keyboardType.getProperty("content-desc=", line))){
                                Key.keyCordinate tempCordinate =  keyboardType.getCordinate(keyboardType.getProperty("bounds=", line));
                                // 추천단어바 영역내에 존재하는지 확인한다.
                                if(isContainArea(tempCordinate)){
                                    //Log.i("@@@", "영역내에 존재 / x : " + tempCordinate.x + " y : " + tempCordinate.y);
                                    //device.clickAndCount(btnCordinate.x, btnCordinate.y);
                                    btnCordinate = tempCordinate;
                                    break;
                                }
                            }
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return btnCordinate;
    }
}
