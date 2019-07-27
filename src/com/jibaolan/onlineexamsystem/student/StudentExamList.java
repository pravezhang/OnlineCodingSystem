package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.AbstractInterface;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

public class StudentExamList extends AbstractInterface {
    private static String[] psfs={"LOGIN SUCCESS","201207100301467","信息管理与信息系统","2014-2","张庭旭"};
    public static void main(String[] args) {
        //psfs=args;
        if(!psfs[0].equals("LOGIN SUCCESS")) {
            SundryFunction.Message("错误", "非常规载入程序，请重试！", 0);
            return;
        }
        StudentExamList sel=new StudentExamList();
        sel.setWindowDefaults("选择考试 - "+psfs[4]);
        sel.setUserInterface();
        sel.setWindowAndShow();
    }

    @Override
    public void setUserInterface() {

    }
}
