package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class Jdbc {
    /*由于本类是边学边写，所以写的寥寥草草，欢迎指出，我会认真看*/
    //插入数据
    String INSERT = "INSERT INTO OUSERS(USERNAME) VALUES";
    //传入一个Connection 和一个字符串（SQL语句）用于向表中插入数据
    public void insert( Connection conn,String value) {
        try {
            Statement sta= conn.createStatement();
            sta.executeUpdate(this.INSERT + "('" + value + "');");
            sta.close();
        } catch (SQLException var5) {
            var5.printStackTrace();
        }
    }
    //查询表中的数据并返回一个LikedList集合 集合中存放着已存入的数据
    public LinkedList addUser(Connection conn) {
        LinkedList<String> user = new LinkedList();
        ResultSet res=null;
        Statement sta=null;
        try {
            sta=conn.createStatement();
            res = sta.executeQuery("SELECT * FROM OUSERS;");
            while(res.next()) {
                int i=1;//下标从一开始 所以定义int i=1
                user.add(res.getString(i));
                i++;
            }
            sta.close();
            res.close();
        } catch (SQLException var6) {
            var6.printStackTrace();
        }
        return user;
    }
    //注册驱动
    public Connection register(){
        Connection conn=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:" +
                            "3306/liaotian?serverTimezone=UTC",
                    "root", "112209");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
            return conn;
    }

    public static void main(String[] args) {
        Jdbc j=new Jdbc();
        Connection register = j.register();
        LinkedList linkedList = j.addUser(register);
        System.out.println(linkedList);
    }
}
