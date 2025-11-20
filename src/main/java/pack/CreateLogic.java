package pack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CreateLogic {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";
	
	public CreateLogic() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("✅ JDBCドライバロード成功");
		} catch (ClassNotFoundException e) {
			System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
		}
	}
	
	public boolean existsEmail(String email) { // メールアドレスが既に存在するか確認するメソッド
		String sql = "SELECT COUNT(*) FROM mst_users WHERE email = ?";
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
			 PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0; // 1件以上あれば登録済み
			}

		} catch (SQLException e) {
			System.out.println("existsEmailエラー：" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public boolean createAccount(String firstName, String lastName, String email, String password) { // アカウント作成メソッド
	    boolean result = false;
	    
	    // メールアドレスが既に存在するか確認
	    if (existsEmail(email)) { 
	        System.out.println("既に登録されているメールアドレスです：" + email);
	        return false;
	    }
	    
	    // INSERT文を準備
	    String sql = "INSERT INTO mst_users "
	               + "(first_name, last_name, email, password, created_at, del_flg) VALUES (?, ?, ?, ?, ?, 0)";
	    
	    // DB接続と処理
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String hashedPassword = hashPassword(password);

	        stmt.setString(1, firstName);
	        stmt.setString(2, lastName);
	        stmt.setString(3, email);
	        stmt.setString(4, hashedPassword);
	        stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));

	        int rows = stmt.executeUpdate(); // 追加件数が1なら成功
	        result = rows > 0;

	    } catch (SQLException e) {
	        System.out.println("❌ SQLエラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return result;
	}

	
	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = md.digest(password.getBytes());
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("SHA-256アルゴリズムが使用できません", e);
		}
	}
}
