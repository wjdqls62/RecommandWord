package com.phillit.qa.recommendword.Common;

import android.content.Context;
import android.util.Log;

import com.phillit.qa.recommendword.Common.Configuration.Configuration;
import com.phillit.qa.recommendword.R;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TestCaseParser {
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private StringBuffer word;
    private String filePath;
    private FileInputStream fis;
    private ArrayList<String> wordList;
    private Device device;
    private int getContents = 0; // 행에서 몇번째 데이터를 갖고오는지?

    public TestCaseParser(String mode, Device device){
        //int rowLength = 0;
        filePath = device.getContext().getResources().getString(R.string.path_TestCase);
        word = new StringBuffer();

        try {
            wordList = new ArrayList<>();
            fis = new FileInputStream(filePath);
            workbook = new HSSFWorkbook(fis);
            if(mode.equals("KOR")){
                sheet = workbook.getSheet("KOR");
                if(device.getTestType() == Configuration.KSR_CONVERSATION){
                    getContents = 1;
                }else if(device.getTestType() == Configuration.KSR_LG){
                    getContents = 5;
                }
            }else if(mode.equals("ENG")){
                sheet = workbook.getSheet("ENG");
                if(device.getTestType() == Configuration.KSR_CONVERSATION){
                    getContents = 1;
                }else if(device.getTestType() == Configuration.KSR_LG){
                    getContents = 5;
                }
            }else if(mode.equals("ENV")){
                sheet = workbook.getSheet("ENV");
                getContents = 1;
        }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("@@@", getClass().getName() + " : FileNotFoundException...");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("@@@", getClass().getName() + "IO Exception");
        }
    }

    public StringBuffer getWord(int index){
        try{
            word.setLength(0);
            word.append(sheet.getRow(index).getCell(getContents).getStringCellValue());
        }catch (NullPointerException e){
            return null;
        }
        return word;
    }

    //public boolean getTestPlan(int keyType, int screenType){
    //    if(screenType == KeyType.PORTRAIT){
    //        if(keyType == KeyType.KOR_QWERTY){
    //            if(sheet.getRow(4).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_QWERTY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.ENG_QWERTY){
    //            if(sheet.getRow(17).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "ENG_QWERTY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_CHUNJIIN){
    //            if(sheet.getRow(6).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_CHUNJIIN : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_SKY){
    //            if(sheet.getRow(8).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_SKY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_NARAGUL){
    //            if(sheet.getRow(10).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_NARAGUL : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_DANMOUM){
    //            if(sheet.getRow(12).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_DANMOUM : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }
    //    }else if(screenType == KeyType.LANDSCAPE){
    //        if(keyType == KeyType.KOR_QWERTY){
    //            if(sheet.getRow(5).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_QWERTY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.ENG_QWERTY){
    //            if(sheet.getRow(18).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "ENG_QWERTY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_CHUNJIIN){
    //            if(sheet.getRow(7).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_CHUNJIIN : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_SKY){
    //            if(sheet.getRow(9).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_SKY : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_NARAGUL){
    //            if(sheet.getRow(11).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_NARAGUL : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }else if(keyType == KeyType.KOR_DANMOUM){
    //            if(sheet.getRow(13).getCell(getContents).getStringCellValue().equals("Y")){
    //                Log.i("@@@", "KOR_DANMOUM : " + true + " / ScreenType : " + screenType);
    //                return true;
    //            }
    //        }
    //    }
    //    return false;
    //}

    public TestPlan getTestPlan(TestPlan testPlan){
        // InternalTest Check
        if(sheet.getRow(0).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.isInternalTest = true;
            Log.i("@@@", "Internal Test : " + testPlan.isInternalTest);
        }
        // 한국어 QWERTY(세로) 테스트 여부 확인
        if(sheet.getRow(4).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_QWERTY_PORTRAIT = true;
            Log.i("@@@", "한국어 QWERTY(세로) 테스트 여부 확인 : " + testPlan.KOR_QWERTY_PORTRAIT);
        }
        // 한국어 QWERTY(가로) 테스트 여부 확인
        if(sheet.getRow(5).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_QWERTY_LANDSCAPE = true;
            Log.i("@@@", "한국어 QWERTY(가로) 테스트 여부 확인 : " + testPlan.KOR_QWERTY_LANDSCAPE);
        }
        // 한국어 천지인(세로) 테스트 여부 확인
        if(sheet.getRow(6).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_CHUNJIIN_PORTRAIT = true;
            Log.i("@@@", "한국어 천지인(세로) 테스트 여부 확인 : " + testPlan.KOR_CHUNJIIN_PORTRAIT);
        }
        // 한국어 천지인(가로) 테스트 여부 확인
        if(sheet.getRow(7).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_CHUNJIIN_LANDSCAPE = true;
            Log.i("@@@", "한국어 천지인(가로) 테스트 여부 확인 : " + testPlan.KOR_CHUNJIIN_LANDSCAPE);
        }
        // 한국어 스카이(세로) 테스트 여부 확인
        if(sheet.getRow(8).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_SKY_PORTRAIT = true;
            Log.i("@@@", "한국어 스카이(세로) 테스트 여부 확인 : " + testPlan.KOR_SKY_PORTRAIT);
        }
        // 한국어 스카이(가로) 테스트 여부 확인
        if(sheet.getRow(9).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_SKY_LANDSCAPE = true;
            Log.i("@@@", "한국어 스카이(가로) 테스트 여부 확인 : " + testPlan.KOR_SKY_LANDSCAPE);
        }
        // 한국어 나랏글(세로) 테스트 여부 확인
        if(sheet.getRow(10).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_NARAGUL_PORTRAIT = true;
            Log.i("@@@", "한국어 나랏글(세로) 테스트 여부 확인 : " + testPlan.KOR_NARAGUL_PORTRAIT);
        }
        // 한국어 나랏글(가로) 테스트 여부 확인
        if(sheet.getRow(11).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_NARAGUL_LANDSCAPE = true;
            Log.i("@@@", "한국어 나랏글(가로) 테스트 여부 확인 : " + testPlan.KOR_NARAGUL_LANDSCAPE);
        }
        // 한국어 단모음(세로) 테스트 여부 확인
        if(sheet.getRow(12).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_DANMOUM_PORTRAIT = true;
            Log.i("@@@", "한국어 단모음(세로) 테스트 여부 확인 : " + testPlan.KOR_DANMOUM_PORTRAIT);
        }
        // 한국어 단모음(가로) 테스트 여부 확인
        if(sheet.getRow(13).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.KOR_DANMOUM_LANDSCAPE = true;
            Log.i("@@@", "한국어 단모음(가로) 테스트 여부 확인 : " + testPlan.KOR_DANMOUM_LANDSCAPE);
        }
        // 영어 QWERTY(세로) 테스트 여부 확인
        if(sheet.getRow(17).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.ENG_QWERTY_PORTRAIT = true;
            Log.i("@@@", "영어 QWERTY(세로) 테스트 여부 확인 : " + testPlan.ENG_QWERTY_PORTRAIT);
        }
        // 영어 QWERTY(가로) 테스트 여부 확인
        if(sheet.getRow(18).getCell(getContents).getStringCellValue().equals("Y")){
            testPlan.ENG_QWERTY_LANDSCAPE = true;
            Log.i("@@@", "영어 QWERTY(가로) 테스트 여부 확인 : " + testPlan.ENG_QWERTY_LANDSCAPE);
        }
        return testPlan;
    }

    public ArrayList<String> getWordList(){
        return wordList;
    }
}
