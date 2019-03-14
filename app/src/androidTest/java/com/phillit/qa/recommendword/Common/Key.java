package com.phillit.qa.recommendword.Common;

import android.util.Log;

import java.util.ArrayList;

public class Key {
    public String keyValue;
    public Boolean Longclickable;
    public ArrayList<keyCordinate> keyCordinates;
    private keyCordinate tempCordinate;

    public Key(){
        keyCordinates = new ArrayList<>();
    }

    public ArrayList<keyCordinate> getkeyList(){
        return keyCordinates;
    }

    public void setX(int x){
        tempCordinate = new keyCordinate();
        tempCordinate.x = x;
        //Log.i("@@@", "setX() : " + x);
    }

    public void setY(int y){
        tempCordinate.y = y;
        keyCordinates.add(tempCordinate);
        tempCordinate = null;
        //Log.i("@@@", "setY() : " + y);
    }

    public void setLongClickable(Boolean LongClickable){
        this.Longclickable = LongClickable;
    }

    public void setKeyValue(String keyValue){
        this.keyValue = keyValue;
    }

    public void logKeyinfo(){
        Log.i("@@@", "=====================================");
        Log.i("@@@", "value : " + keyValue);
        Log.i("@@@", "Cordinate count : " + keyCordinates.size() + " / LongClickable : " + Longclickable);
        for(int i=0; i<keyCordinates.size(); i++){
            Log.i("@@@", "X:"+keyCordinates.get(i).x + " / Y:"+keyCordinates.get(i).y);
        }
    }

    public static class keyCordinate{
        public int x, y;

        public void reset(){
            x = 0;
            y = 0;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }
    }
}
