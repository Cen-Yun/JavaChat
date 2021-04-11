package JFrame;

import javax.swing.*;
import java.awt.*;

public  class  MyFrame extends JFrame {
     public static final JTextField title =new JTextField(5);//标题
     public static final JTextArea message=new JTextArea();//消息记录框
     public static final JTextField inputMessage=new JTextField(20);;//信息输入框
     public static final JButton count=new JButton("在线人数");//在线人数
     public static final JTextArea number=new JTextArea();//人数显示框
     public static final JButton send=new JButton("发送");//发送按钮

     public MyFrame(){
        setBounds(50,100,400,350);
//        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        title.setText(JOptionPane.showInputDialog("请输入你的名字"));
        message.setEditable(false);
        number.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        add(userUI());
        setVisible(true);
    }
    //界面排版
    public JPanel userUI(){
        JPanel p=new JPanel();
        p.setLayout(new GridBagLayout());
        p.add(title,new GBC(0,0,1,1).setAnchor(GridBagConstraints.WEST).setInsets(0,0,10,0));
        p.add(new JScrollPane(message),new GBC(0,1,1,1).setIpad(200,200).setAnchor(GridBagConstraints.WEST));
        p.add(inputMessage,new GBC(0,2,1,1).setAnchor(GridBagConstraints.WEST).setInsets(10,0,0,0));
        p.add(send,new GBC(1,2).setAnchor(GridBagConstraints.WEST).setInsets(10,0,0,0));
        p.add(count,new GBC(1,0).setAnchor(GridBagConstraints.WEST));
        number.setLayout(new FlowLayout());
        p.add(new JScrollPane(number),new GBC(1,1).setIpad(100,200).setAnchor(GridBagConstraints.EAST));
        return p;
    }

    public static void main(String[] args) {
        new MyFrame();
    }
}
