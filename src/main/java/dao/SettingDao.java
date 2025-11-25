package dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class SettingDao {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";
	
	public SettingDao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ JDBCドライバロード成功");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
        }
    }
	
	// 他のユーザーが同じメールアドレスを使用しているか確認する処理
	public boolean existsEmailForOtherUser(String email, int userId) {
        String sql = "SELECT COUNT(*) FROM mst_users WHERE email = ? AND id <> ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery(); 

            if (rs.next()) {
                return rs.getInt(1) > 0; // 1件以上なら重複あり
            }

        } catch (SQLException e) {
            System.out.println("❌ existsEmailForOtherUserエラー：" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

	// 🔹 ユーザー情報を更新する処理
	public boolean updateUserInfo(int userId, String lastName, String firstName, String email, String password) {
		boolean result = false;
		
		if (existsEmailForOtherUser(email, userId)) {
            return false;
        }
		
		String sql = "UPDATE mst_users SET last_name=?, first_name=?, email=?, password=? WHERE id=?";

			try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				 PreparedStatement stmt = conn.prepareStatement(sql)) {

				stmt.setString(1, lastName);
				stmt.setString(2, firstName);
				stmt.setString(3, email);
				stmt.setString(4, hashPassword(password)); // ✅ パスワードをハッシュ化して保存
				stmt.setInt(5, userId);

				int rows = stmt.executeUpdate();
				result = (rows > 0);
			} catch (SQLException e) {
	            System.out.println("❌ updateUserInfoエラー：" + e.getMessage());
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	// ユーザー情報を取得する処理
	public User getUserInfo(int userId) {
	    User user = null;

	    String sql = "SELECT id, first_name, last_name, email, password, created_at, deleted_at, del_flg "
	               + "FROM mst_users WHERE id = ? AND del_flg = 0";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            user = new User();
	            user.setId(rs.getInt("id"));
	            user.setFirstName(rs.getString("first_name"));
	            user.setLastName(rs.getString("last_name"));
	            user.setEmail(rs.getString("email"));
	            user.setPassword(rs.getString("password"));
	            user.setCreatedAt(rs.getTimestamp("created_at"));

	            var deletedAt = rs.getTimestamp("deleted_at");
	            user.setDeletedAt(deletedAt != null ? deletedAt.toLocalDateTime() : null);

	            user.setDelFlg(rs.getInt("del_flg"));
	        }

	    } catch (SQLException e) {
	        System.out.println("❌ getUserInfoエラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return user;
	}
	
	// 🔹 パスワードをSHA-256でハッシュ化
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