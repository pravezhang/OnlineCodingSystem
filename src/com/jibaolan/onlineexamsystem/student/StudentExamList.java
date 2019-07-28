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
    private int cc=0;
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
                time.setText("系统时间："+SundryFunction.getTime());
            }
        },0,1000);

        JPanel tellPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,80,10));
        JLabel tellWord=new JLabel("选择考试及场次：");
        tellWord.setFont(new MSYaHeiFont(Font.BOLD,32));
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
                cc++;
                String[] examData={
                        rs.getString("EID"),
                        rs.getString("NAME"),
                        rs.getString("STIME"),
                        rs.getString("DURATION")+" 分钟"
                };
                examPanel.add(new ExamListItemPanel(examData));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        bottomPanel.add(new JLabel("   姓名："+psfs[4]+"    |    学号："+psfs[1]+"    |    专业班级："+psfs[2]+" "+psfs[3]+"   "));



        // 向 panel0 添加从上到下的基本元素。
        panel0.add(topTimePanel);
        panel0.add(tellPanel);
        panel0.add(examPanel);
        panel0.add(Box.createVerticalStrut(15));
        panel0.add(bottomPanel);
        // 添加总panel0
        window.add(panel0);
    }

    private class ExamListItemPanel extends JPanel{
        private final int[] dss={60,380,220,100,140};
        private final Color[] colors={Color.green,Color.magenta,Color.pink,Color.yellow,Color.cyan};
        // examData :
        // EID , Ename , Stime , Duration
        ExamListItemPanel(String[] examData) {
            this.setLayout(new FlowLayout(FlowLayout.LEFT,15,0));
            this.add(Box.createHorizontalStrut(30));
            for (int i = 0; i < 4; i++) {
                JLabel j=new JLabel(examData[i]);
                if (i==0)
                    j.setHorizontalAlignment(SwingConstants.CENTER);
                else
                    j.setText("  " + j.getText());

                j.setFont(new MSYaHeiFont(Font.PLAIN,18));
                if(cc%2==0)
                    j.setBackground(new Color(0xd5e8c1));

                else
                    j.setBackground(new Color(0xfefada));
                j.setOpaque(true);
                this.add(j);
                j.setPreferredSize(new Dimension(dss[i],45));
            }
            JButton enter=new JButton("时间未到");
            enter.setEnabled(false);
            final MouseAdapter ma=new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int res=JOptionPane.showConfirmDialog(window,"你选择了 \n"+examData[1]+" \n是否确定进入考试？进入即开始计时。","提示",JOptionPane.YES_NO_OPTION);
                    if(res==0){
                        String[] args = {"ENTERING", psfs[1], psfs[2], psfs[3], psfs[4], examData[0], SundryFunction.getTime()};
                        StudentExaming.main(args);
                        window.dispose();
                    }
                }
            };
            enter.addMouseListener(null);
            JPanel enterPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
            enterPanel.add(enter);
            this.add(enterPanel);
            enterPanel.setPreferredSize(new Dimension(dss[4],45));
            enter.setFocusable(false);
            enter.setFont(new MSYaHeiFont(Font.PLAIN,24));
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
                            enter.addMouseListener(ma);
                            tik.cancel();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            },0,1000);

        }
        public ExamListItemPanel(){
            final String[] titles={"编号","考试名","开始时间","持续时间","选择考试"};
            // Title
            /*
            BoxLayout layout=new BoxLayout(this,BoxLayout.X_AXIS);
            this.setLayout(layout);
            */
            this.setLayout(new FlowLayout(FlowLayout.LEFT,15,5));
            this.add(Box.createHorizontalStrut(30));
            for (int i = 0; i < 5; i++) {
                JLabel j=new JLabel(titles[i]);
                this.add(j);
                j.setHorizontalAlignment(SwingConstants.CENTER);
                j.setFont(new MSYaHeiFont(Font.BOLD,22));
                j.setPreferredSize(new Dimension(dss[i],45));
                j.setOpaque(true);
                j.setBackground(new Color(0xb8fdf9));
            }
            this.add(Box.createHorizontalStrut(30));
        }

    }

}
