package frame;

import sun.rmi.runtime.RuntimeUtil;
import util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Fun2 extends JFrame implements KeyListener, Runnable {
    private JPanel jpl = new JPanel();
    private JLabel chilb = new JLabel();
    private JLabel time = new JLabel();
    private JLabel tipslb = new JLabel();
    private TextField tfAns = new TextField(35);
    private JLabel judge = new JLabel();
    private int FalseTimes = 0;
    private int testNum;
    public Fun2(){
        new Thread(this).start();
        this.setTitle("请在文本框内输入完整的英语单词");
        this.setSize(800,600);
        GUItil.toCenter(this);
        this.setLocation(getX()+12,getY()+9);
        GetChoices.get4Choices();
        time.setPreferredSize(new Dimension(200,50));
        time.setOpaque(true);
        time.setBackground(Color.orange);
        time.setHorizontalAlignment(JLabel.CENTER);
        this.setChinese();
        this.getTips();
        setVisible(true);
        jpl.setLayout(new FlowLayout(FlowLayout.CENTER,50,50));
        jpl.add(time);
        jpl.add(chilb);
        jpl.add(tipslb);
        jpl.add(tfAns);
        jpl.add(judge);
        this.add(jpl);
        tfAns.addKeyListener(this);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    public void setChinese(){                                                                                              //设置4个选项的中文
        chilb.setText(Conf.chi[0]);
    }
    public void getTips() {                                                                                             //获取中文提示，如果单词曾经做错，不给提示
        String str = Conf.eng[0];
        if(Memory.pps2.contains(str)) {
            tipslb.setText("No tips for your mistake!");
        }
        else if (str.length() > 3) {
            tipslb.setText(str.charAt(0) + "__" + str.charAt(3) + "__");
        }
        else{
            tipslb.setText(str.charAt(0) + "__" + str.charAt(2));
        }
    }
    public void run()  {                                                                                                //计时器
        long t1 = System.currentTimeMillis()/1000;
        while (true) {
            long t2 = System.currentTimeMillis()/1000;
            long dt = 600-t2+t1;
            time.setText("剩余时间"+dt+"秒");
            try {
                Thread.sleep(100);
            } catch (Exception ex) {}
            if(dt<0){break;}
        }
    }

    public void keyTyped(KeyEvent e) {}                                                                                   //处理回车事件
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(tfAns.getText().equals(Conf.eng[0])){
                Memory.hsp2.put(Conf.eng[0],1);
                if(Memory.hsp1.containsKey(Conf.eng[0])&&Memory.hsp2.containsKey(Conf.eng[0])){
                    DataImport.words.remove(Conf.eng[0]);                                                               //移除正确两次的单词
                    DataImport.meanings.remove(Conf.chi[0]);                                                                                                        //移除已掌握单词
                }
                GetChoices.get4Choices();
                setChinese();
                getTips();
                tfAns.setText("");
                judge.setText("Last One is Correct！");
            }
            else{
                judge.setText("前一个答案为  "+Conf.eng[0]+"  "+ Conf.chi[0]+"  请自我纠错");
                tfAns.setText("");
                FalseTimes++;
                Memory.pps2.put(Conf.eng[0], Conf.chi[0]);
                GetChoices.get4Choices();
                setChinese();
                getTips();
                if(FalseTimes==2){
                    JOptionPane.showMessageDialog(null,"测试失败");
                    try{
                        Memory.pps2.list(new PrintStream(new FileOutputStream("UnhandledWords.txt",true)));
                    }catch (Exception ex){}
                    this.dispose();
                }
            }
            testNum++;
        }
        if(testNum == 20){
            JOptionPane.showMessageDialog(null,"恭喜！挑战完成");
            try{
                Memory.pps2.list(new PrintStream(new FileOutputStream("UnhandledWords.txt",true)));
            }catch (Exception ex){}
            this.dispose();
        }
    }
}
