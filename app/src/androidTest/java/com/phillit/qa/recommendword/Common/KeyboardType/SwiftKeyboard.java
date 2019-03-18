package com.phillit.qa.recommendword.Common.KeyboardType;
import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.Common.Device;
import com.phillit.qa.recommendword.Common.Key;
import com.phillit.qa.recommendword.Common.RecommendWordParse.Keyboard;
import com.phillit.qa.recommendword.Common.RecommendWordParse.RecommendWordParse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SwiftKeyboard extends Keyboard {
    private Device device;
    private File heirarchyFile;
    private final int END_PROPERTY = 2;
    private RecommendWordParse recommendWordParser;

    public SwiftKeyboard(Device device){
        this.device = device;
        recommendWordParser = new RecommendWordParse(this, device);
    }

    @Override
    public Key.keyCordinate searchKeyboardView(String targetStr){
        return recommendWordParser.searchKeyboardView(targetStr);
    }

    @Override
    public File getheirarchyFile() {
        return heirarchyFile;
    }

    @Override
    public boolean WindowCompresse() {
        File dir;
        boolean result = false;

        try {
            dir = new File(Configuration.heirarchyFilePath);
            if(!dir.exists()){
                dir.mkdir();
            }
            heirarchyFile = new File(Configuration.heirarchyFilePath + Configuration.heirarchyFileName);
            device.getUiDevice().dumpWindowHierarchy(heirarchyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(heirarchyFile.exists()){
                result = true;
            }
        }
        //Log.i("@@@", "WindowDump() : " + result);
        return result;
    }

    @Override
    public Key.keyCordinate getCordinate(String args) {
        Key.keyCordinate btnCord = new Key.keyCordinate();
        ArrayList<String> tempCordi = new ArrayList<>();
        String temp = args;

        // 특수문자 Replace 후 쉼표구분
        temp = temp.replace("[", ",").replace("]", ",");

        String[] temp2 = temp.split(",");

        // null값 구분 후 ArrayList 삽입
        for(int i=0; i<temp2.length; i++){
            if(!temp2[i].isEmpty()){
                //Log.i("@@@", "temnp2[" + i + "]" + temp2[i]);
                tempCordi.add(temp2[i]);
            }
        }

        // 좌표값 계산
        btnCord.x = (Integer.parseInt(tempCordi.get(0)) + Integer.parseInt(tempCordi.get(2))) / 2;
        btnCord.y = (Integer.parseInt(tempCordi.get(1)) + Integer.parseInt(tempCordi.get(3))) / 2;


        //Log.i("@@@", "btnCord.x : " + btnCord.x + " / btnCord.y : " + btnCord.y);

        return btnCord;
    }

    @Override
    public String getProperty(String searchProperty, String targetStr) {
        String temp = "";

        int start = targetStr.lastIndexOf(searchProperty);
        int cnt = 0;
        if(start != -1){
            for(int i=start; i < targetStr.length(); i++){
                if(targetStr.charAt(i) == 34){
                    cnt++;
                }

                if(cnt >= 1){
                    if(targetStr.charAt(i) != 34){
                        temp += targetStr.charAt(i);
                    }
                }

                if(cnt == END_PROPERTY){
                    break;
                }
            }
        }
        return temp;
    }
}