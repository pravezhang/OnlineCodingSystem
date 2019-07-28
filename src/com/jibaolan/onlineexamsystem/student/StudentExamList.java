package com.jibaolan.onlineexamsystem.student;

import com.jibaolan.onlineexamsystem.AbstractInterface;
import com.jibaolan.onlineexamsystem.Utils.DataBaseConnection;
import com.jibaolan.onlineexamsystem.Utils.SundryFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
        // panel0 作为window的唯一组件，承接所有内容组件，布局均为BoxLayout。
        // panel0 里按需要设置为竖向布局或者横向 【一般为竖向】
        // 在panel0里放置所有其他元素
        final JPanel panel0=new JPanel();
        final BoxLayout layout0=new BoxLayout(panel0,BoxLayout.Y_AXIS);
        panel0.setLayout(layout0);

        JPanel topTimePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,30,10));
        JLabel time=new JLabel();
        topTimePanel.add(time);
        Timer updateTime=new Timer();
        updateTime.schedule(new TimerTask() {
            @Override
            public void run() {
                time.setText(SundryFunction.getTime());
            }
        },0,1000);

        JPanel tellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,80,10));
        JLabel tellWord=new JLabel("选择考试及场次：");
        tellWord.setFont(new MSYaHeiFont(Font.BOLD,25));
        tellPanel.add(tellWord);

        JPanel examPanel = new JPanel();
        BoxLayout examLayout=new BoxLayout(examPanel,BoxLayout.Y_AXIS);
        examPanel.setLayout(examLayout);
        examPanel.add(new ExamListItemPanel());
        String sql="SELECT * FROM EXAMS WHERE STATUS=1 AND MAJOR='"+psfs[2]+"'";
        try {
            DataBaseConnection dbc=new DataBaseConnection();
            ResultSet rs=dbc.Query(sql);
            while(rs.next()){
                String[] examData={
                        rs.getString("EID"),
                        rs.getString("NAME"),
                        rs.getString("STIME"),
                        rs.getString("DURATION")
                };
                examPanel.add(new ExamListItemPanel(examData));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //examPanel.add();








        // 向 panel0 添加从上到下的基本元素。
        panel0.add(topTimePanel);
        panel0.add(tellPanel);
        panel0.add(examPanel);
        // 添加总panel0
        window.add(panel0);
    }

    private class ExamListItemPanel extends JPanel{
        private final int[] dss={80,300,200,60,130};
        private final Color[] colors={Color.green,Color.magenta,Color.pink,Color.yellow,Color.cyan};
        // examData :
        // EID , Ename , Stime , Duration
        ExamListItemPanel(String[] examData) {
            BoxLayout layout=new BoxLayout(this,BoxLayout.X_AXIS);
            this.setLayout(layout);
            this.add(Box.createHorizontalStrut(30));
            for (int i = 0; i < 4; i++) {
                JLabel j=new JLabel(examData[i]);
                j.setBackground(colors[i]);
                j.setOpaque(true);
                this.add(j);
                this.add(Box.createHorizontalStrut(dss[i]-spaceCount(j.getText())));
            }


            JButton enter=new JButton("时间未到");
            enter.setEnabled(false);
            enter.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String[] args = {"ENTERING",psfs[1],psfs[2],psfs[3],psfs[4],examData[0],SundryFunction.getTime()};
                    StudentExaming.main(args);
                    window.dispose();
                }
            });
            this.add(enter);
            this.add(Box.createHorizontalStrut(30));
            Timer tik=new Timer();
            tik.schedule(new TimerTask() {
                @Override
                public void run() {
                    SimpleDateFormat nowFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date now=nowFormat.parse(SundryFunction.getTime());
                        Date target=nowFormat.parse(examData[2]);
                        if(now.compareTo(target)>=0) {
                            enter.setText("　选择　");
                            enter.setEnabled(true);
                            tik.cancel();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            },0,1000);

        }
        public ExamListItemPanel(){
            final String[] titles={"编号","考试名","开始时间","持续时间","确定"};
            // Title
            BoxLayout layout=new BoxLayout(this,BoxLayout.X_AXIS);
            this.setLayout(layout);
            this.add(Box.createHorizontalStrut(30));
            for (int i = 0; i < 5; i++) {
                JLabel j=new JLabel(titles[i]);
                this.add(j);
                this.add(Box.createHorizontalStrut(dss[i]-spaceCount(j.getText())));
                j.setOpaque(true);
                j.setBackground(colors[i]);
            }
            this.add(Box.createHorizontalStrut(30));
        }

        private int spaceCount(String s){
            char[] cs=s.toCharArray();
            int ans=0;
            for (char c:cs){
                if(c==' ')
                    ans+=3;
                else if(c>127)
                    ans+=13;
                else
                    ans+=7;
            }
            return ans;
        }
    }

}
