import mysql.Jdbc;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.util.LinkedList;
import JFrame.*;
public class ClientTCP extends MyFrame implements Runnable{

    Socket s;
    DataInputStream dis;
    DataOutputStream dos;
    Jdbc j=new Jdbc();//数据库相关操作类，用于在表中查询用户
    Connection register = j.register();//jdbc注册方法
    LinkedList<String> linkedList = j.addUser(register);//在表中查找到的数据存在此集合中，Linked List集合方便找到首尾元素
    String name;
    ClientTCP(){
        try {
            /*
            * 1.创建对象是直接发送连接请求
            * 2。并向服务端发送客户端的名字
            * 3.把数据库表中一存在的用户显示在在线列表中*/
            this.s=new Socket("127.0.0.1",8806);
            this.dis=new DataInputStream(s.getInputStream());
            this.dos=new DataOutputStream(s.getOutputStream());
            name="@"+title.getText();
            dos.writeUTF(name);
            j.insert(register,name);
            for (int i = 0; i < linkedList.size(); i++) {
                number.append(linkedList.get(i)+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*点击Enter就可以发送文字信息 send事件相同，这样做是为了方便
    *   1。要发送的文字信息不能为空
    *   2.点击发送后将文字信息添加至消息框并清空文字输入区*/
    private void send(){
        inputMessage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputMessage.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"不能发送空消息");
                }else {
                    try {
                        dos.writeUTF(inputMessage.getText());
                        message.append("我说："+inputMessage.getText() + "\n");
                        inputMessage.setText("");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        /*点击发送按钮就可以发送文字信息 和inputMessage事件只有不同对象的区别，这样做是为了方便
         *   1。要发送的文字信息不能为空
         *   2.点击发送后将文字信息添加至消息框并清空文字输入区*/
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputMessage.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "不能发送空消息");
                }else {
                    try {
                        dos.writeUTF(inputMessage.getText());
                        message.append("我说："+inputMessage.getText() + "\n");
                        inputMessage.setText("");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }
    /*刷新在线用户列表
    *   1.获取一个新的在线用户集合，因为程序执行到这儿linkedList集合是没有更新过的
    *   2.未更新过的集合与新的集合比较长度，如果长度相等，那么把新的用户添加到在线用户列表中
    *       （1.记录就集合与新集合的长度，用于添加就旧集合之后的所有元素
    *       （2.如果集合中碰到此类所代表的用户，跳过
    *       （3.让旧集合的地址指向集合的地址，并结束新集合*/

    private void refresh(){
        count.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LinkedList <String> list1 = j.addUser(register);
                if (linkedList.size()==list1.size()){
                    return;
                }else {
                    int len1=linkedList.size();
                    int len2=list1.size();
                    for (int i = len1; i <len2 ; i++) {
                        if (list1.get(i).equals(name)){
                            continue;
                        }
                        number.append(list1.get(i)+"\n");
                    }
                    linkedList=list1;
                }
                list1.clone();
            }
        });
    }
    @Override
    public void run() {
        /*send();发送信息方法
        * refresh();更新在线用户方法
        * 把收到的文字信息添加到消息框中*/
        send();
        refresh();
        while(true){
            try {
                message.append(dis.readUTF() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                close(s,dos,dis);
            }
        }
    }
    //关闭Socket 输入流 输出流方法
    public void close(Socket s,DataOutputStream dos,DataInputStream dis){
        if (dos==null) {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dis==null) {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (s==null) {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ClientTCP c=new ClientTCP();
        Thread t=new Thread(c);
        t.start();
    }
}