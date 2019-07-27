package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.AbstractInterface;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

public class StudentEnterExam extends AbstractInterface {
    private static String[] psfs={"ENTER EXAM","201207100301467","张庭旭","信息管理与信息系统","2014-2","<EID>","<ENAME>"};
    public static void main(String[] args) {
        //psfs=args;
        if(!psfs[0].equals("ENTER EXAM")){
            SundryFunction.Message("错误","非常规载入程序，请重试！",0);
            return;
        }
        StudentEnterExam see=new StudentEnterExam();
        see.setWindowDefaults(psfs[6]+" - 考生:"+psfs[2]);
        see.setUserInterface();
        see.setWindowAndShow();
    }
    @Override
    public void setUserInterface() {

    }
}
