import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server{
     ServerSocket senver=null;//服务端Socket
     int  PORE=8806;//端口号
     Socket client=null;//接收客户端套接字的套接字
     HashMap<String,ServerTCP> thread_name=new HashMap<>();//保存用户名与用户相对应的线程
    ArrayList<String> userName=new ArrayList<>();//保存用户名，用于找到map集合中对应的线程
    HashMap<Socket,String> thread_name1=new HashMap<>();//此集合有些浪费资源，只是用于在发送文字信息时找到线程所对应的用户名
    String name;
    /*1.持续的接收客户端的连接请求，并新建ServerTCP类
    * 2.接收用户名，并添加到userName thread_name thread_name1集合中
    * 3.调用start()方法让线程进入就绪状态*/
      Server(int PORE){
          try {
              senver=new ServerSocket(PORE);
              while (true){
                  client=senver.accept();
                  ServerTCP serverTCP=new ServerTCP(client);
                  DataInputStream dis=new DataInputStream(client.getInputStream());
                  name = dis.readUTF();
                  userName.add(name);
                  thread_name.put(name,serverTCP);
                  thread_name1.put(client,name);
                  serverTCP.start();
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
    public class ServerTCP extends Thread{
        public DataOutputStream dos;
        public DataInputStream dis;
        public Socket s;
        ServerTCP(Socket s){
            this.s=s;
        }
        //群聊方法 提取发送人名字与文字消息一同发送
        private void qunliao(String str){
            if (str.startsWith("@")){
                siliao(str);
            }else {
                try {
                    for (String s1 : userName) {
                        if (s == thread_name.get(s1).s) continue;
                        thread_name.get(s1).dos.writeUTF(thread_name1.get(s).substring(1)+"说："+str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //私聊方法 提取发送信息的人的名字与文字消息一同发送
        private void siliao(String str) {
            try {
                String name= thread_name1.get(s);
                String user = str.substring(0, name.length());
                String message=str.substring(name.length());
                thread_name.get(user).dos.writeUTF(name.substring(1)+"对你说："+message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            try {
                dis=new DataInputStream(s.getInputStream());
                dos=new DataOutputStream(s.getOutputStream());
                String str ;
                while (true) {
                    str=dis.readUTF();
                    qunliao(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        new Server(8806);
    }
}