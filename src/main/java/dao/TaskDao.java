package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Task;
import model.TaskCategory;

public class TaskDao {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/myapp_db?serverTimezone=Asia/Tokyo&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mikazuki";

	public TaskDao() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("✅ JDBCドライバロード成功");
		} catch (ClassNotFoundException e) {
			System.out.println("❌ JDBCドライバのロードに失敗：" + e.getMessage());
		}
	}

	// タスクをデータベースに挿入するメソッド
	public boolean insertTask(Task task) {
		boolean result = false;

		String sql = "INSERT INTO trn_tasks "
	            + "(user_id, category_id, priority, "
	            + "task_date_start, task_date_end, start_time, end_time, "
	            + "title, content, created_at, del_flg) "
	            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), 0)";

		try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
		         PreparedStatement stmt = conn.prepareStatement(sql)) {

		        stmt.setInt(1, task.getUserId());

		     // user_id
		        stmt.setInt(1, task.getUserId());

		        // category_id（null対応）
		        if (task.getCategoryId() != null) {
		            stmt.setInt(2, task.getCategoryId());
		        } else {
		            stmt.setNull(2, java.sql.Types.INTEGER);
		        }

		        // priority
		        stmt.setInt(3, task.getPriority());

		        // ★ 開始日
		        stmt.setObject(4, task.getStartDate());

		        // ★ 終了日
		        stmt.setObject(5, task.getEndDate());

		        // ★ 開始時間（null対応）
		        if (task.getStartTime() != null) {
		            stmt.setObject(6, task.getStartTime());
		        } else {
		            stmt.setNull(6, java.sql.Types.TIME);
		        }

		        // ★ 終了時間（null対応）
		        if (task.getEndTime() != null) {
		            stmt.setObject(7, task.getEndTime());
		        } else {
		            stmt.setNull(7, java.sql.Types.TIME);
		        }

		        // title / content
		        stmt.setString(8, task.getTitle());
		        stmt.setString(9, task.getContent());

		        int rows = stmt.executeUpdate();
		        result = rows > 0;

		        if (result) {
		            System.out.println("タスク登録成功：" + task.getTitle());
		        } else {
		            System.out.println("タスク登録失敗：" + task.getTitle());
		        }

		    } catch (SQLException e) {
		        System.out.println("❌ SQLエラー：" + e.getMessage());
		        e.printStackTrace();
		    }

		    return result;
		}

	// 指定されたユーザーIDのタスクをすべて取得するメソッド
	public List<Task> getTasksByUserId(int userId) {
	    List<Task> taskList = new ArrayList<>(); // タスクを格納するリスト

	    String sql = "SELECT "
	            + " t.id, "
	            + " t.task_date_start, "
	            + " t.task_date_end, "
	            + " t.start_time, "
	            + " t.end_time, "
	            + " t.title, "
	            + " t.content, "
	            + " t.category_id, "
	            + " t.priority, "
	            + " c.name  AS category_name, "
	            + " c.color AS category_color "
	            + " FROM trn_tasks t "
	            + " LEFT JOIN mst_task_categories c "
	            + "   ON t.category_id = c.id "
	            + " WHERE t.user_id = ? "
	            + " AND t.del_flg = 0 "
	            + " ORDER BY t.task_date_start ASC, t.start_time ASC";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) { // SQL文をセット

	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery(); // DBから帰ってきたデータを一行ずつ格納する

	        while (rs.next()) {

	            Task t = new Task();

	            t.setId(rs.getInt("id"));
	            t.setUserId(userId);

	            java.sql.Date sDate = rs.getDate("task_date_start");
	            t.setStartDate(sDate != null ? sDate.toLocalDate() : null);

	            java.sql.Date eDate = rs.getDate("task_date_end");
	            t.setEndDate(eDate != null ? eDate.toLocalDate() : null);
	            
	            java.sql.Time st = rs.getTime("start_time");
	            t.setStartTime(st != null ? st.toLocalTime() : null);

	            java.sql.Time et = rs.getTime("end_time");
	            t.setEndTime(et != null ? et.toLocalTime() : null);

	            t.setTitle(rs.getString("title"));
	            t.setContent(rs.getString("content"));

	            t.setCategoryId(rs.getInt("category_id"));
	            t.setCategoryName(rs.getString("category_name"));
	            t.setCategoryColor(rs.getString("category_color"));

	            t.setPriority(rs.getInt("priority"));

	            taskList.add(t);
	        }

	        System.out.println("✅ タスク一覧取得：" + taskList.size() + "件"); // 取得件数を表示

	    } catch (SQLException e) {
	        System.out.println("❌ SQLエラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return taskList;
	}

	// 指定された月のタスクを取得するメソッド
	public List<Task> getTasksByMonth(int userId, int year, int month) {
	    List<Task> taskList = new ArrayList<>();

	    String sql = "SELECT "
	            + "t.id, t.user_id, "
	            + "t.task_date_start, t.task_date_end, "
	            + "t.start_time, t.end_time, "
	            + "t.title, t.content, "
	            + "t.priority, "
	            + "t.category_id, "
	            + "c.name AS category_name, "
	            + "c.color AS category_color "
	            + "FROM trn_tasks t "
	            + "LEFT JOIN mst_task_categories c ON t.category_id = c.id "
	            + "WHERE t.user_id = ? "
	            + "AND ( "
	            + "      (YEAR(t.task_date_start) = ? AND MONTH(t.task_date_start) = ?) "
	            + "   OR (YEAR(t.task_date_end) = ? AND MONTH(t.task_date_end) = ?) "
	            + ") "
	            + "AND t.del_flg = 0 "
	            + "ORDER BY t.task_date_start ASC, t.start_time ASC";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, userId);
	        stmt.setInt(2, year);
	        stmt.setInt(3, month);
	        stmt.setInt(4, year);
	        stmt.setInt(5, month);

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            Task task = new Task();
	            task.setId(rs.getInt("id"));
	            task.setUserId(rs.getInt("user_id"));

	            task.setStartDate(rs.getDate("task_date_start").toLocalDate());
	            task.setEndDate(rs.getDate("task_date_end").toLocalDate());

	            java.sql.Time sTime = rs.getTime("start_time");
	            task.setStartTime(sTime != null ? sTime.toLocalTime() : null);

	            java.sql.Time eTime = rs.getTime("end_time");
	            task.setEndTime(eTime != null ? eTime.toLocalTime() : null);

	            task.setTitle(rs.getString("title"));
	            task.setContent(rs.getString("content"));
	            task.setPriority(rs.getInt("priority"));

	            task.setCategoryId(rs.getInt("category_id"));
	            task.setCategoryName(rs.getString("category_name"));
	            task.setCategoryColor(rs.getString("category_color"));

	            taskList.add(task);
	        }

	    } catch (SQLException e) {
	        System.out.println("❌ getTasksByMonth エラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return taskList;
	}

	// ===== IDでタスクを取得するメソッド =====
	public Task getTaskById(int taskId) {
	    Task task = null;

	    String sql =
	            "SELECT " +
	            " t.id, t.user_id, " +
	            " t.task_date_start, t.task_date_end, " +
	            " t.start_time, t.end_time, " +
	            " t.title, t.content, " +
	            " t.category_id, t.priority, " +
	            " c.name AS category_name, " +
	            " c.color AS category_color " +
	            "FROM trn_tasks t " +
	            "LEFT JOIN mst_task_categories c " +
	            "ON t.category_id = c.id " +
	            "WHERE t.id = ? AND t.del_flg = 0";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, taskId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            task = new Task();
	            task.setId(rs.getInt("id"));
	            task.setUserId(rs.getInt("user_id"));
	            
	            java.sql.Date sDate = rs.getDate("task_date_start");
	            task.setStartDate(sDate != null ? sDate.toLocalDate() : null);

	            java.sql.Date eDate = rs.getDate("task_date_end");
	            task.setEndDate(eDate != null ? eDate.toLocalDate() : null);

	            java.sql.Time st = rs.getTime("start_time");
	            task.setStartTime(st != null ? st.toLocalTime() : null);

	            java.sql.Time et = rs.getTime("end_time");
	            task.setEndTime(et != null ? et.toLocalTime() : null);

	            task.setTitle(rs.getString("title"));
	            task.setContent(rs.getString("content"));

	            task.setCategoryId(rs.getInt("category_id"));
	            task.setCategoryName(rs.getString("category_name"));
	            task.setCategoryColor(rs.getString("category_color"));
	            
	            task.setPriority(rs.getInt("priority"));
	        }

	        System.out.println("✅ タスク取得成功（id=" + taskId + "）");

	    } catch (SQLException e) {
	        System.out.println("❌ getTaskById エラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return task;
	}


	// ===== タスクを更新するメソッド =====
	public boolean updateTask(Task task) {
	    boolean result = false;
	    String sql = "UPDATE trn_tasks "
	               + "SET "
	               + " task_date_start = ?, "
	               + " task_date_end = ?, "
	               + " start_time = ?, "
	               + " end_time = ?, "
	               + " title = ?, "
	               + " content = ?, "
	               + " category_id = ?, "
	               + " priority = ?, "
	               + " updated_at = NOW() "
	               + "WHERE id = ? AND del_flg = 0";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        // ===== 開始日 =====
	        if (task.getStartDate() != null) {
	            stmt.setObject(1, task.getStartDate());
	        } else {
	            stmt.setNull(1, java.sql.Types.DATE);
	        }

	        // ===== 終了日 =====
	        if (task.getEndDate() != null) {
	            stmt.setObject(2, task.getEndDate());
	        } else {
	            stmt.setNull(2, java.sql.Types.DATE);
	        }

	        // ===== 開始時間 =====
	        if (task.getStartTime() != null) {
	            stmt.setObject(3, task.getStartTime());
	        } else {
	            stmt.setNull(3, java.sql.Types.TIME);
	        }

	        // ===== 終了時間 =====
	        if (task.getEndTime() != null) {
	            stmt.setObject(4, task.getEndTime());
	        } else {
	            stmt.setNull(4, java.sql.Types.TIME);
	        }

	        // ===== タイトル =====
	        stmt.setString(5, task.getTitle());

	        // ===== 内容 =====
	        stmt.setString(6, task.getContent());

	        // ===== カテゴリID（null許容） =====
	        if (task.getCategoryId() != null) {
	            stmt.setInt(7, task.getCategoryId());
	        } else {
	            stmt.setNull(7, java.sql.Types.INTEGER);
	        }

	        // ===== 優先度 =====
	        stmt.setInt(8, task.getPriority());

	        // ===== ID =====
	        stmt.setInt(9, task.getId());

	        int rows = stmt.executeUpdate();
	        result = rows > 0;

	    } catch (SQLException e) {
	        System.out.println("❌ updateTask エラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return result;
	}
	
	public boolean deleteTask(int taskId, int userId) {

	    String sql = "UPDATE trn_tasks SET del_flg = 1, updated_at = NOW() "
	               + "WHERE id = ? AND user_id = ?";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, taskId);
	        stmt.setInt(2, userId);

	        int rows = stmt.executeUpdate();
	        return rows > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public List<TaskCategory> getAllCategories() {
	    List<TaskCategory> list = new ArrayList<>();

	    String sql = "SELECT id, name, color FROM mst_task_categories ORDER BY id";

	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            TaskCategory cat = new TaskCategory();
	            cat.setId(rs.getInt("id"));
	            cat.setName(rs.getString("name"));
	            cat.setColor(rs.getString("color"));
	            list.add(cat);
	        }

	        System.out.println("✅ カテゴリ一覧取得成功（" + list.size() + "件）");

	    } catch (SQLException e) {
	        System.out.println("❌ getAllCategories エラー：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return list;
	}
}
