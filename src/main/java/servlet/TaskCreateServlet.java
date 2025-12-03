package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TaskDao;
import model.Task;
import model.TaskCategory;

/**
 * Servlet implementation class TaskCreateEngine
 */
@WebServlet("/TaskCreateServlet")
public class TaskCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

        // ログインチェック
        Integer userId = (Integer) session.getAttribute("loginUserId");
        if (userId == null) {
            session.setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        // カテゴリ一覧を取得して JSP に渡す
        TaskDao logic = new TaskDao();
        List<TaskCategory> categoryList = logic.getAllCategories();
        request.setAttribute("categoryList", categoryList);

        // JSP へフォワード
        request.getRequestDispatcher("/view/task/create_task.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();

	    // ログイン確認
	    Integer userId = (Integer) session.getAttribute("loginUserId");
	    if (userId == null) {
	        session.setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
	        response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
	        return;
	    }

	    // --- パラメータ取得 ---
	    String startDateStr = request.getParameter("task_date_start");
	    String endDateStr   = request.getParameter("task_date_end");
	    String startTimeStr = request.getParameter("start_time");
	    String endTimeStr   = request.getParameter("end_time");

	    String title        = request.getParameter("title");
	    String content      = request.getParameter("content");
	    String categoryIdStr = request.getParameter("category_id");
	    String priorityStr   = request.getParameter("priority");

	    // --- 入力値保持 ---
	    session.setAttribute("input_task_date_start", startDateStr);
	    session.setAttribute("input_task_date_end", endDateStr);
	    session.setAttribute("input_start_time", startTimeStr);
	    session.setAttribute("input_end_time", endTimeStr);
	    session.setAttribute("input_title", title);
	    session.setAttribute("input_content", content);
	    session.setAttribute("input_category_id", categoryIdStr);
	    session.setAttribute("input_priority", priorityStr);

	    // --- バリデーション ---
	    if (startDateStr == null || startDateStr.isEmpty() ||
	    	startTimeStr == null || startTimeStr.isEmpty() ||
	    	title == null || title.isEmpty()) {

	    	 session.setAttribute("error", "開始日・開始時間・タイトルは必須です。");
	    	 response.sendRedirect(request.getContextPath() + "/view/task/create_task.jsp");
	    	 return;
	    }

	    // LocalDate 変換
	    LocalDate startDate = LocalDate.parse(startDateStr);
	    // 終了日は任意：未入力なら開始日と同じにする
	    LocalDate endDate;
	    if (endDateStr == null || endDateStr.isEmpty()) {
	        endDate = startDate;
	    } else {
	    	 endDate = LocalDate.parse(endDateStr);
	    }

	    // LocalTime 変換 (任意)
	    LocalTime startTime = null;
	    LocalTime endTime   = null;

	    if (startTimeStr != null && !startTimeStr.isEmpty()) {
	        startTime = LocalTime.parse(startTimeStr);
	    }
	    if (endTimeStr != null && !endTimeStr.isEmpty()) {
	        endTime = LocalTime.parse(endTimeStr);
	    }

	    // categoryId・priority
	    Integer categoryId = null;
	    if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
	        categoryId = Integer.parseInt(categoryIdStr);
	    }

	    int priority = (priorityStr != null && !priorityStr.isEmpty())
	                    ? Integer.parseInt(priorityStr)
	                    : 3;

	    // --- Task モデル作成 ---
	    Task task = new Task();
	    task.setUserId(userId);
	    task.setStartDate(startDate);
	    task.setEndDate(endDate);
	    task.setStartTime(startTime);
	    task.setEndTime(endTime);
	    task.setTitle(title);
	    task.setContent(content);
	    task.setCategoryId(categoryId);
	    task.setPriority(priority);

	    // --- DB登録 ---
	    TaskDao logic = new TaskDao();
	    boolean isInserted = logic.insertTask(task);

	    if (isInserted) {
	        // 表示後クリア
	        session.removeAttribute("input_task_date_start");
	        session.removeAttribute("input_task_date_end");
	        session.removeAttribute("input_start_time");
	        session.removeAttribute("input_end_time");
	        session.removeAttribute("input_title");
	        session.removeAttribute("input_content");
	        session.removeAttribute("input_category_id");
	        session.removeAttribute("input_priority");

	        session.setAttribute("message", "タスクを登録しました。");
	        response.sendRedirect(request.getContextPath() + "/CalendarServlet");
	    } else {
	        session.setAttribute("error", "タスク登録に失敗しました。");
	        response.sendRedirect(request.getContextPath() + "/view/task/create_task.jsp");
	    }
	}
}
