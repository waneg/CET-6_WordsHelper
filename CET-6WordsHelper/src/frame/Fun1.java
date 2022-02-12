package frame;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Fun1 extends JDialog implements ActionListener, Runnable{
    private JButton btA = new JButton();                                                                                //4个中文选项
    private JButton btB = new JButton();
    private JButton btC = new JButton();
    private JButton btD = new JButton();
    private JPanel jpl1 = new JPanel();
    private JPanel jpl2 = new JPanel();
    private JLabel jlb = new JLabel();                                                                                  //显示倒计时时间
    private JLabel lbWord = new JLabel();                                                                               //显示英语单词
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,150,30);
    private Font font1 = new Font("楷体",Font.ROMAN_BASELINE,23);
    private long t1 = System.currentTimeMillis()/1000;
    private long dt =0;
    private int t;                                                                                                          //记录正确选项
    int FalseTimes = 0;
    private int testNum;                                            //记录题数
    public Fun1() {
        this.setTitle("请选择相应的中文选项");
        this.setSize(800, 600);
        new GUItil().toCenter(this);
        this.setLocation(getX()+16,getY()+12);
        this.setLayout(null);
        jpl1.setLayout(null);
        jpl1.setBounds(0,0,800,180);
        jpl2.setBounds(100,200,600,400);
        jlb.setBounds(600,0,180,80);
        lbWord.setBounds(0,100,800,80);
        lbWord.setHorizontalAlignment(SwingConstants.CENTER);
        lbWord.setOpaque(true);
        lbWord.setBackground(Color.pink);
        jpl1.add(jlb);
        jpl1.add(lbWord);
        jpl2.add(btA);
        jpl2.add(btB);
        jpl2.add(btC);
        jpl2.add(btD);
        jpl2.setLayout(flowLayout);
        this.add(jpl1);
        this.add(jpl2);
        this.setVisible(true);
        Thread thr = new Thread(this);
        thr.start();
        GetChoices.get4Choices();
        this.set4Choices();
        jlb.setFont(font1);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        btA.addActionListener(this);
        btB.addActionListener(this);
        btC.addActionListener(this);
        btD.addActionListener(this);
    }
    public void run()  {                                                                                                //计时器
        while (true) {
            long t2 = System.currentTimeMillis()/1000;
            dt = 300-t2+t1;
            jlb.setText("剩余时间"+String.valueOf(dt)+"秒");
            try {
                Thread.sleep(100);
            } catch (Exception ex) {}
            if(dt<0){break;}
        }
    }
    public void set4Choices(){                                                                                          //设置4个选项的中文
        t=(int)(Math.random()*4);
        lbWord.setText(Conf.eng[t]);
        btA.setText(Conf.chi[0]);
        btB.setText(Conf.chi[1]);
        btC.setText(Conf.chi[2]);
        btD.setText(Conf.chi[3]);
    }
    public void actionPerformed(ActionEvent e)  {
        if(((JButton)(e.getSource())).getText().equals(Conf.chi[t])){
            Memory.hsp1.put(Conf.eng[t],1);
            if(Memory.hsp1.containsKey(Conf.eng[0])&&Memory.hsp2.containsKey(Conf.eng[0])){
                DataImport.words.remove(Conf.eng[t]);                                                                   //移除正确两次的单词
                DataImport.meanings.remove(Conf.chi[t]);
            }
        }
        else {
            FalseTimes++;
            Memory.pps2.put(Conf.eng[t], Conf.chi[t]);
        }
        if(FalseTimes==2||dt<0){
            JOptionPane.showMessageDialog(null,"测试失败");
            try{
                Memory.pps2.list(new PrintStream(new FileOutputStream("UnhandledWords.txt",true)));
            }catch (Exception ex){}
            this.dispose();
        }
        testNum++;
        GetChoices.get4Choices();
        this.set4Choices();
        if(testNum == 20){
            JOptionPane.showMessageDialog(null,"恭喜！挑战完成");
            try{
                Memory.pps2.list(new PrintStream(new FileOutputStream("UnhandledWords.txt",true)));
            }catch (Exception ex){}
            this.dispose();
        }
    }
}
