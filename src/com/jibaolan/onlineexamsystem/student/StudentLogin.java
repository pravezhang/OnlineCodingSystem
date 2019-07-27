package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.Utils.DataBaseConnection;
import com.jibaolan.onlineexamsystem.Utils.Encrypt;
import com.jibaolan.onlineexamsystem.AbstractInterface;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;

import static com.jibaolan.onlineexamsystem.Utils.SundryFunction.RESOURCES_FOLDER;

public class StudentLogin extends AbstractInterface {

    public static void main(String[] args) {
        StudentLogin sl=new StudentLogin();
        sl.setWindowDefaults("在线考试系统 - 学生登陆");
        sl.setUserInterface();
        sl.setWindowAndShow();
    }

    @Override
    public void setUserInterface(){
        final JPanel layout0=new JPanel(/*new GridBagLayout()*/);//两部分，上面是图，下面是登陆界面
        final BoxLayout pL=new BoxLayout(layout0,BoxLayout.Y_AXIS);
        layout0.setLayout(pL);
        window.add(layout0);
        final JPanel boxJ=new JPanel();
        boxJ.setPreferredSize(new Dimension(400,350));
        final Container boxContainer=new Container();
        final BoxLayout bl=new BoxLayout(boxContainer,BoxLayout.Y_AXIS);
        boxContainer.setLayout(bl);
        boxJ.add(boxContainer);
        layout0.add(
                new JImage(RESOURCES_FOLDER +"/login_title_student.jpg",400,250).load()
        );
        layout0.add(boxJ);
        final JTextField studentidTEXT=new JTextField();
        studentidTEXT.setFont(new Font(null,Font.BOLD,15));
        studentidTEXT.setPreferredSize(new Dimension(200,40));
        final JPasswordField passwordTEXT=new JPasswordField();
        passwordTEXT.setFont(new Font(null,Font.BOLD,15));
        passwordTEXT.setPreferredSize(new Dimension(200,40));
        final JButton login=new JButton("登陆");
        login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                login.setEnabled(false);
                String studentid=studentidTEXT.getText();
                String password=new String(passwordTEXT.getPassword());
                if(studentid.length()<1 || password.length()<1){
                    JOptionPane.showMessageDialog(null, "学号或密码格式不正确！", "错误",JOptionPane.ERROR_MESSAGE);
                    login.setEnabled(true);
                    return;
                }
                Thread net=new Thread(() -> {
                    String sql="SELECT * FROM STUDENTS WHERE SID='"+studentid+"' AND PWD='"+ Encrypt.MD5(password)+"'";
                    try {
                        DataBaseConnection dbc=new DataBaseConnection();
                        ResultSet rs=dbc.Query(sql);
                        if(!rs.next()){
                            JOptionPane.showMessageDialog(null, "", "",JOptionPane.ERROR_MESSAGE);
                            rs.close();
                            dbc.close();
                            login.setEnabled(true);
                        }else{
                            SundryFunction.Message("提示","验证成功！",1);
                            String[] userdata={"LOGIN SUCCESS",
                                    rs.getString("SID"),
                                    rs.getString("MAJOR"),
                                    rs.getString("CLAS"),
                                    rs.getString("NAME")};
                            StudentExamList.main(userdata);
                            window.dispose();
                            System.out.println(rs.getString("SID")+","+rs.getString("DEPT"));
                        }
                        rs.close();
                        dbc.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                });
                net.start();
            }
        });
        login.setFont(new Font(null,Font.BOLD,20));
        login.setMinimumSize(new Dimension(200,130));
        JPanel buttonP=new JPanel();
        buttonP.add(login);
        studentidTEXT.setToolTipText("学号");
        boxContainer.add(Box.createVerticalStrut(50));
        boxContainer.add(studentidTEXT);
        boxContainer.add(Box.createVerticalStrut(30));
        boxContainer.add(passwordTEXT);
        boxContainer.add(Box.createVerticalStrut(30));
        boxContainer.add(buttonP);
        try {
            UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ignored) {

        }
    }
}
