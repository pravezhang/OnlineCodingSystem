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
        // panel0 作为window的唯一组件，承接所有内容组件，布局均为BoxLayout。
        // panel0 里按需要设置为竖向布局或者横向 【一般为竖向】
        // 在panel0里放置所有其他元素

        final JPanel panel0=new JPanel();//两部分，上面是图，下面是登陆界面
        final BoxLayout layout0=new BoxLayout(panel0,BoxLayout.Y_AXIS);
        panel0.setLayout(layout0);
        final JPanel boxJ=new JPanel();
        final Container boxContainer=new Container();
        final BoxLayout layout11=new BoxLayout(boxContainer,BoxLayout.Y_AXIS);
        boxContainer.setLayout(layout11);
        boxJ.add(boxContainer);
        panel0.add(
                new JImage(RESOURCES_FOLDER +"/login_title_student.jpg",400,250).load()
        );
        panel0.add(boxJ);
        final JTextField studentidTEXT=new JTextField();
        studentidTEXT.setFont(new Font(null,Font.BOLD,20));
        studentidTEXT.setPreferredSize(new Dimension(200,40));
        final JPasswordField passwordTEXT=new JPasswordField();
        passwordTEXT.setFont(new Font(null,Font.BOLD,20));
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
                        }
                        rs.close();
                        dbc.close();
                    } catch (Exception e1) {
                        SundryFunction.Message("错误","数据库连接失败，请重试！",0);
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
        boxContainer.add(Box.createVerticalStrut(50));

        // add panel0 to window .
        window.add(panel0);
    }
}
