package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DeleteLogic {

    // データベース接続情報（自分の環境に合わせて変更）
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";
	
	public DeleteLogic() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ JDBCドライバロード成功");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
        }
    }

    public boolean deleteUserByEmail(String email) {
        boolean result = false;

        String sql = "UPDATE mst_users SET del_flg = 1 WHERE email = ? AND del_flg = 0"; // 論理削除のSQL

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email); // プレースホルダにメールアドレスをセット

            int rows = stmt.executeUpdate(); // 更新された行数を取得
            if (rows > 0) { // 1行以上更新された場合
                result = true; // 成功
            }

        } catch (Exception e) {
            e.printStackTrace(); // エラーハンドリング
        }

        return result;
    }
}