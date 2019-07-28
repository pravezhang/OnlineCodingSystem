package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.AbstractInterface;

public class Main extends AbstractInterface {
    public static void main(String[] args)  {
        Main m=new Main();
        m.setWindowDefaults("");
        m.setUserInterface();
        m.setWindowAndShow();
        StudentLogin.main(args);
        m.window.dispose();
    }

    @Override
    public void setUserInterface() {

    }
}
