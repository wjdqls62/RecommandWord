package com.phillit.qa.recommendword.Common.RecommendWordParse;

import com.phillit.qa.recommendword.Common.Key;

import java.io.File;

public abstract class Keyboard {
    abstract public boolean WindowCompresse();
    abstract public Key.keyCordinate getCordinate(String args);
    abstract public String getProperty(String searchProperty, String targetStr);
    public abstract File getheirarchyFile();
    public abstract Key.keyCordinate searchKeyboardView(String targetStr);
}
