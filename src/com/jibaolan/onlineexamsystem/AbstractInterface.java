package com.jibaolan.onlineexamsystem;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractInterface {
    public JFrame window=new JFrame();
    public void setWindowDefaults(String title){
        window.setTitle(title);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new GridLayout(1,1));
        window.setResizable(false);
    }
    public abstract void setUserInterface();
    public void setWindowAndShow(){
        //完毕，打包
        window.pack();
        //设置居中显示
        Dimension windowSize=window.getSize();
        Dimension screen=java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screen.width/2-windowSize.width/2,screen.height/2-windowSize.height/2);
        window.setVisible(true);
    }
    public class JColorPanel extends JPanel{
        public JColorPanel(Color color,int minx,int miny) {
            super();
            this.setBackground(color);
            this.setPreferredSize(new Dimension(minx,miny));
        }
    }
    public class MSYaHeiFont extends Font{
        public MSYaHeiFont(int style, int size) {
            super("Microsoft Yahei UI", style, size);
        }
    }
    public class JImage extends JPanel{
        private String imageUrl;
        public JImage(String imageUrl,int width,int height) {
            super();
            this.imageUrl=imageUrl;
            setPreferredSize(new Dimension(width,height));
        }
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Dimension d=this.getPreferredSize();
            // 按照空间preferredSize大小自动缩放
            g.drawImage(new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(d.width,d.height,Image.SCALE_DEFAULT)).getImage(),0,0,this);
        }
        public JImage load(){
            this.repaint();
            return this;
        }
    }
}
