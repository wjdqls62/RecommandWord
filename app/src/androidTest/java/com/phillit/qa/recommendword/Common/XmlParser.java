package com.phillit.qa.recommendword.Common;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;

import com.phillit.qa.recommendword.Common.KeyType.KeyType;
import com.phillit.qa.recommendword.Common.KeyboardType.KeyboardType;
import com.phillit.qa.recommendword.R;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.HashMap;

public class XmlParser {
    private XmlResourceParser parser;
    private Key key;
    private HashMap<String, Key> keyList;
    private HashMap<String, Key> specialKeyList;
    private Context context;
    private Resources resource;
    private Device device;
    private int parsingMode, language, testType = 0;
    private String deviceModelName;


    public XmlParser(Context context, int parsingMode, int language, Device device) {
        this.context = context;
        this.parsingMode = parsingMode;
        resource = context.getResources();
        this.device = device;
        this.language = language;
        deviceModelName = device.getDeviceModelName();
        testType = device.getKeyboardType();
        try {
            parseXML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public XmlParser(Context context, int parsingMode) {
        this.context = context;
        this.parsingMode = parsingMode;
        resource = context.getResources();
        try {
            parseXML();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Key> getKeyList() {
        return keyList;
    }

    public HashMap<String, Key> getSpecialKeyList() {
        return specialKeyList;
    }

    private void parseXML() throws IOException, XmlPullParserException {

        if (testType == KeyboardType.G6_KEYBOARD_SWIFT) {
            // 일반문자
            if (parsingMode == KeyType.PORTRAIT) {
                if (language == KeyType.KOR_QWERTY) {
                    parser = resource.getXml(R.xml.swiftkey_g6_kor_qwerty_portrait);
                } else if (language == KeyType.ENG_QWERTY) {
                    parser = resource.getXml(R.xml.swiftkey_g6_eng_qwerty_portrait);
                }
            }
        }else if(testType == KeyboardType.S10P_KEYBOARD_SAMSUNG){
            if (language == KeyType.KOR_QWERTY) {
                parser = resource.getXml(R.xml.samsung_gs10p_kor_qwerty_portrait);
            } else if (language == KeyType.ENG_QWERTY) {
                parser = resource.getXml(R.xml.samsung_gs10p_eng_qwerty_portrait);
            }
        }else if(testType == KeyboardType.G6_KEYBOARD_GBOARD){
            if(language == KeyType.KOR_QWERTY){
                parser = resource.getXml(R.xml.gboard_g6_kor_qwerty_portrait);
            }else if(language == KeyType.ENG_QWERTY){
                parser = resource.getXml(R.xml.gboard_g6_eng_qwerty_portrait);
            }
        }

        keyList = new HashMap<>();

        int eventType = parser.getEventType();

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String tagName = parser.getName();

            if (eventType == XmlResourceParser.START_TAG) {
                if (tagName.equals("Value")) {
                    key = new Key();
                    key.setKeyValue(parser.nextText());
                }
                else if(tagName.contains("Longclickable")){
                    key.setLongClickable(Boolean.parseBoolean(parser.nextText()));
                }
                else if (tagName.contains("X")) {
                    key.setX(Integer.parseInt(parser.nextText()));
                }
                else if (tagName.contains("Y")) {
                    key.setY(Integer.parseInt(parser.nextText()));
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
                if (tagName.equals("Key")) {
                    // Parsing된 Key 정보 출력
                    key.logKeyinfo();
                    keyList.put(key.keyValue, key);
                    key = null;
                }
            }
            eventType = parser.next();
        } // End while

        // 특수문자
        if (testType == KeyboardType.G6_KEYBOARD_SWIFT) {
            // SwiftKeyboard의 경우 기호롱탭의 배열이 한글과 영문이 상이하여 조건문 분기
            if (parsingMode == KeyType.PORTRAIT) {
                if(language == KeyType.KOR_QWERTY){
                    parser = resource.getXml(R.xml.swiftkey_g6_kor_special_character_portrait);
                }else if(language == KeyType.ENG_QWERTY){
                    parser = resource.getXml(R.xml.swiftkey_g6_eng_special_character_portrait);
                }
            }
        }else if(testType == KeyboardType.S10P_KEYBOARD_SAMSUNG){
            if (parsingMode == KeyType.PORTRAIT) {
                parser = resource.getXml(R.xml.samsung_gs10p_common_special_character_portrait);
            }
        }else if(testType == KeyboardType.G6_KEYBOARD_GBOARD){
            if (parsingMode == KeyType.PORTRAIT) {
                if(language == KeyType.KOR_QWERTY){
                    parser = resource.getXml(R.xml.gboard_g6_kor_special_character_portrait);
                }else if(language == KeyType.ENG_QWERTY){
                    parser = resource.getXml(R.xml.gboard_g6_eng_special_character_portrait);
                }
            }
        }

        specialKeyList = new HashMap<>();

        eventType = parser.getEventType();

        while (eventType != XmlResourceParser.END_DOCUMENT) {
            String tagName = parser.getName();

            if (eventType == XmlResourceParser.START_TAG) {
                if (tagName.equals("Value")) {
                    key = new Key();
                    key.setKeyValue(parser.nextText());
                } else if (tagName.contains("X")) {
                    key.setX(Integer.parseInt(parser.nextText()));
                } else if (tagName.contains("Y")) {
                    key.setY(Integer.parseInt(parser.nextText()));
                }else if(tagName.contains("Longclickable")){
                    key.setLongClickable(Boolean.parseBoolean(parser.nextText()));
                }
            } else if (eventType == XmlResourceParser.END_TAG) {
                if (tagName.equals("Key")) {
                    // Parsing된 Key 정보 출력
                    key.logKeyinfo();
                    specialKeyList.put(key.keyValue, key);
                    key = null;
                }
            }
            eventType = parser.next();
        } // End while

    }
}
