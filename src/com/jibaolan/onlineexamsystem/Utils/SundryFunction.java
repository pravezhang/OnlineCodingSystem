package com.jibaolan.onlineexamsystem.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class SundryFunction {
    public static final String RESOURCES_FOLDER= "F:/Documents/姬保兰毕设材料/resources";
    //@param typeFlag : -1:plain  0:error  1:information  2:warning  3:question
    public static void Message(String title,String message,int typeFlag){
        JOptionPane.showMessageDialog(null,message,title, typeFlag);
    }
    public static String  getTime(){
        final String[] weekdays={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar now=Calendar.getInstance();
        StringBuilder sb=new StringBuilder();
        sb.append(now.get(Calendar.YEAR)).append("-");
        sb.append(now.get(Calendar.MONTH)>8?""+(now.get(Calendar.MONTH)+1):"0"+(now.get(Calendar.MONTH)+1)).append("-");
        sb.append(now.get(Calendar.DAY_OF_MONTH)>9?""+now.get(Calendar.DAY_OF_MONTH):"0"+now.get(Calendar.DAY_OF_MONTH)).append(" ");
        //sb.append(weekdays[now.get(Calendar.DAY_OF_WEEK)-1]).append(" ");
        sb.append(now.get(Calendar.HOUR_OF_DAY)>9?""+now.get(Calendar.HOUR_OF_DAY):"0"+now.get(Calendar.HOUR_OF_DAY)).append(":");
        sb.append(now.get(Calendar.MINUTE)>9?""+now.get(Calendar.MINUTE):"0"+now.get(Calendar.MINUTE)).append(":");
        sb.append(now.get(Calendar.SECOND)>9?""+now.get(Calendar.SECOND):"0"+now.get(Calendar.SECOND));
        return sb.toString();
    }
}
