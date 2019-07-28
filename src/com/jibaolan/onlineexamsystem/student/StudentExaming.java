package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.AbstractInterface;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class StudentExaming extends AbstractInterface {

    private static String[] psfs={"ENTERING","201207100301467","信息管理与信息系统","2014-2","张庭旭","<EID>","STARTTIME"};
    private static int currentQuestion=0;
    public static void main(String[] args) {
        psfs=args;
        if(!psfs[0].equals("ENTERING")) {
            SundryFunction.Message("错误", "非常规载入程序，请重试！", 0);
            return;
        }
        for (String s:psfs)
            System.out.println(s);
        StudentExaming se=new StudentExaming();
        se.setWindowDefaults(psfs[6]+" - 考生:"+psfs[2]+" - "+"第 "+currentQuestion+" 题");
        se.setUserInterface();
        se.setWindowAndShow();
        Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                se.nextQuestion(currentQuestion+2);
            }
        },1000,1000);
    }
    @Override
    public void setUserInterface() {
        window.setPreferredSize(new Dimension(800,300));
    }
    public void nextQuestion(int questionID){
        currentQuestion=questionID;
        this.window.setTitle(psfs[6]+" - 考生:"+psfs[2]+" - "+"第 "+currentQuestion+" 题");
    }
}
