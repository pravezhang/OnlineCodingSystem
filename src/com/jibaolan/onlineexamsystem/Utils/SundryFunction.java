package com.jibaolan.onlineexamsystem.Utils;

import javax.swing.*;

public class SundryFunction {
    public static final String RESOURCES_FOLDER= "F:/Documents/姬保兰毕设材料/resources";
    //@param typeFlag : -1:plain  0:error  1:information  2:warning  3:question
    public static void Message(String title,String message,int typeFlag){
        JOptionPane.showMessageDialog(null,message,title, typeFlag);

    }
}
