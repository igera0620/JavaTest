package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectTest {

    public static void main(String[] args) {
        // DB接続情報
        String URL = "jdbc:mysql://192.168.81.115:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true";
        String USER = "root";
        String PASS = "mikazuki";

        try {
            // MySQLドライバをロード
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 接続を試みる
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ データベースへの接続に成功しました！");

            conn.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}