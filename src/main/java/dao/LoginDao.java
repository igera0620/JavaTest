package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";
	
	// ✅ JDBCドライバはコンストラクタで一度だけロード
	public LoginDao() { 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ JDBCドライバロード成功");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
        }
    }

	public int loginCheck(String email, String password) {
        int resultCode = 2; // デフォルトは「失敗」

        String sql = "SELECT password, del_flg FROM mst_users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int delFlg = rs.getInt("del_flg");
                String storedHash = rs.getString("password");
                String inputHash = hashPassword(password);

                if (delFlg == 1) {
                    resultCode = 1; // 削除済み
                } else if (storedHash.equals(inputHash)) {
                    resultCode = 0; // ログイン成功
                }
            }

        } catch (SQLException e) { // SQL実行中にエラーが発生した場合の例外処理
            System.out.println("❌ SQL実行中にエラーが発生しました");
            e.printStackTrace();
        }

        return resultCode;
    }
	
	public int getUserIdByEmail(String email) { // メールアドレスからユーザーIDを取得するメソッド
	    int id = -1; // デフォルト値（見つからなかった場合）

	    String sql = "SELECT id, first_name, last_name, email FROM mst_users WHERE email = ? AND del_flg = 0";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS); 
	         PreparedStatement stmt = conn.prepareStatement(sql)) { // SQL文の準備

	        stmt.setString(1, email); // プレースホルダにメールアドレスをセット
	        ResultSet rs = stmt.executeQuery(); // SQLの実行

	        if (rs.next()) {
	            id = rs.getInt("id"); // DBのユーザーIDを取得
	        }

	    } catch (Exception e) { // 例外処理
	        System.out.println("❌ ユーザーID取得中にエラー：" + e.getMessage()); // エラーメッセージを表示
	        e.printStackTrace(); // スタックトレースを出力
	    }

	    return id; // ユーザーIDを返す
	}
	
	// パスワードをSHA-256でハッシュ化するメソッド
	private String hashPassword(String password) { // パスワードをSHA-256でハッシュ化
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256"); // SHA-256アルゴリズムのインスタンスを取得
			byte[] hashBytes = md.digest(password.getBytes()); // パスワードをバイト配列に変換してハッシュ化
			StringBuilder sb = new StringBuilder(); // ハッシュ値を16進数の文字列に変換
			for (byte b : hashBytes) { // 各バイトをループ
				sb.append(String.format("%02x", b)); // 16進数に変換してStringBuilderに追加
			} 
			return sb.toString(); // ハッシュ化されたパスワードを返す
		} catch (NoSuchAlgorithmException e) { // SHA-256アルゴリズムが見つからない場合の例外処理
			throw new RuntimeException("SHA-256アルゴリズムが使用できません", e); // ランタイム例外をスロー
		}
	}
}
