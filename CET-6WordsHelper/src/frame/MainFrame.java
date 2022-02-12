package frame;

import util.GUItil;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    private FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,24,24);
    private JPanel jpl = new JPanel();
    private JButton bt1 = new JButton("“英-中”挑战模式");
    private JButton bt2 = new JButton("“中-英”挑战模式");
    private ImageIcon wel = new ImageIcon("Welcome.jpg");
    private Image img = Toolkit.getDefaultToolkit().createImage("Icon.jpg");
    private JLabel lbwel = new JLabel(wel);
    Font font = new Font("微软雅黑", Font.PLAIN,29);
    public MainFrame(){
        this.setSize(800,600);
        this.setTitle("六级词汇助记");
        this.add(lbwel,BorderLayout.NORTH);
       this.setIconImage(img);
        bt1.setFont(font);bt2.setFont(font);
        bt1.addActionListener(this);
        bt2.addActionListener(this);
        bt1.setBackground(new Color(181,230,29));
        bt2.setBackground(new Color(181,230,29));
        jpl.setLayout(flowLayout);
        jpl.add(bt1);jpl.add(bt2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(jpl, BorderLayout.CENTER);
        this.setVisible(true);
        GUItil.toCenter(this);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==bt1){                         //进入功能1
            new Fun1();
        }else if(e.getSource()==bt2){                   //进入功能2
            new Fun2();
        }
    }
}
