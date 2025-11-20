package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProfileLogic {
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";

	public ProfileLogic() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
		}
	}

	// ユーザープロフィールが存在するか確認するメソッド
	private boolean existsProfile(int userId) {
		String sql = "SELECT COUNT(*) FROM mst_user_profiles WHERE user_id = ?"; // ユーザーIDでプロフィールを検索。count句でuser_idの件数を取得。
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId); 
			ResultSet rs = stmt.executeQuery(); // 

			if (rs.next()) { // 
				return rs.getInt(1) > 0; // 1件以上なら存在する
			}
		} catch (Exception e) {
			System.out.println("❌ existsProfileエラー：" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	// プロフィールを挿入するメソッド
	public boolean insertProfile(int userId, String nickname, String gender, String birthDate, String phone,
			String address, String profileText, String icon) {
		boolean result = false;
		String sql = "INSERT INTO mst_user_profiles "
				+ "(user_id, nickname, gender, birth_date, phone, address, profile_text, icon, created_at) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
					PreparedStatement stmt = conn.prepareStatement(sql)) {

				stmt.setInt(1, userId);
				stmt.setString(2, nickname);
				stmt.setString(3, gender);
				stmt.setDate(4, java.sql.Date.valueOf(birthDate));
				stmt.setString(5, phone);
				stmt.setString(6, address);
				stmt.setString(7, profileText);
				stmt.setString(8, icon);
				stmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

				int rows = stmt.executeUpdate();
				result = rows > 0;
			}
		} catch (Exception e) {
			System.out.println("❌ SQLエラー：" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	// プロフィールを更新するメソッド
	private boolean updateProfile(int userId, String nickname, String gender, String birthDate,
			String phone, String address, String profileText, String icon) {

		String sql = "UPDATE mst_user_profiles "
				+ "SET nickname=?, gender=?, birth_date=?, phone=?, address=?, profile_text=?, icon=?, updated_at=? "
				+ "WHERE user_id=?";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, nickname);
			stmt.setString(2, gender);
			stmt.setDate(3, java.sql.Date.valueOf(birthDate));
			stmt.setString(4, phone);
			stmt.setString(5, address);
			stmt.setString(6, profileText);
			stmt.setString(7, icon);
			stmt.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
			stmt.setInt(9, userId);

			return stmt.executeUpdate() > 0;

		} catch (Exception e) {
			System.out.println("❌ updateProfileエラー：" + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	// プロフィールを保存（挿入または更新）するメソッド
	public boolean saveProfile(int userId, String nickname, String gender, String birthDate,
			String phone, String address, String profileText, String icon) {

		if (existsProfile(userId)) {
			System.out.println("🔁 既存データあり → UPDATE実行");
			return updateProfile(userId, nickname, gender, birthDate, phone, address, profileText, icon);
		} else {
			System.out.println("🆕 データなし → INSERT実行");
			return insertProfile(userId, nickname, gender, birthDate, phone, address, profileText, icon);
		}
	}
	
	// プロフィールを取得するメソッド
	public Profile getProfile(int userId) {
	    String sql = "SELECT * FROM mst_user_profiles WHERE user_id = ?";
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Profile profile = new Profile(); // プロフィールオブジェクトを作成
	            profile.setNickname(rs.getString("nickname"));
	            profile.setGender(rs.getString("gender"));
	            profile.setBirthDate(rs.getString("birth_date"));
	            profile.setPhone(rs.getString("phone"));
	            profile.setAddress(rs.getString("address"));
	            profile.setProfileText(rs.getString("profile_text"));
	            profile.setIcon(rs.getString("icon")); // アイコンの取得を追加
	            return profile;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}
