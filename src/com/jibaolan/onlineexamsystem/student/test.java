package com.jibaolan.onlineexamsystem.student;


import com.jibaolan.onlineexamsystem.Utils.DataBaseConnection;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test {
    public static void main(String[] args) throws Exception {
        //for(Font f:GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts())
          //  System.out.println(f.getName());
        DataBaseConnection dataBaseConnection=new DataBaseConnection();
        int t=0;
        char[] abcd={'A','B','C','D'};
        for (int i = 0; i <1000 ; i++) {
            String sql = "INSERT INTO `questions`( `TYPE`, `COURSE`, `TITLE`, `ANSWER`, `HARDNESS`, `SCORE`) VALUES ("+
                    2+","+
                    "'C语言程序设计'"+","+
                    "'填空题 题目~~["+i+"]'"+","+
                    "'填空题 答案~~["+randBetween(0,1)+"]'"+","+
                    "1"+","+
                    "5"+
                    ")";
        t+=dataBaseConnection.Update(sql);
        }
        System.out.println(t);

    }
    private static int randBetween(int a, int b){
        return (int)(Math.random()*Math.abs((b-a+1)));
    }

}
