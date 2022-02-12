package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataImport {
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<String> meanings = new ArrayList<String>();
    public static void load() throws Exception{
       FileInputStream fis = new FileInputStream("CET6.txt");
       InputStreamReader isr = new InputStreamReader(fis);
       BufferedReader br = new BufferedReader(isr);
       while(true){
           String str = br.readLine();
           if(str == null){
               break;
           }
           int idx = str.indexOf(" ", 3);
           String str1 = str.substring(3,idx).trim();
           String str2 = str.substring(idx+1).trim();
           words.add(str1);
           meanings.add(str2);
        }
    }
}
