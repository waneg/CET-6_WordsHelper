package main;

import frame.MainFrame;
import util.DataImport;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class Main {
    public static void main(String[] args) throws Exception {
        InitGlobalFont(new Font("alias", Font.PLAIN, 26));  //统一设置字体
        new MainFrame();
        try {
            DataImport.load();
        } catch (Exception ignored) {
        }
    }

    private static void InitGlobalFont(Font font) {
        FontUIResource fontRes = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements(); ) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontRes);
            }
        }
    }
}