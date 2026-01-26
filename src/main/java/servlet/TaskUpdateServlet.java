package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TaskDao;
import model.Task;

/**
 * Servlet implementation class TaskUpdateEngine
 */
@WebServlet("/TaskUpdateServlet")
public class TaskUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TaskUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();

		if (session == null || session.getAttribute("loginUserId") == null) {

			request.getSession().setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
			response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
			return;
		}

		// パラメータ取得
		String idStr = request.getParameter("id");
		String startDateStr = request.getParameter("start_date");
		String endDateStr = request.getParameter("end_date");
		String startTimeStr = request.getParameter("start_time");
		String endTimeStr = request.getParameter("end_time");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String categoryIdStr = request.getParameter("category_id");
		Integer categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty())
				? Integer.parseInt(categoryIdStr)
				: null;
		String priorityStr = request.getParameter("priority");

		int id = Integer.parseInt(request.getParameter("id"));
		int priority = (priorityStr != null && !priorityStr.isEmpty())
				? Integer.parseInt(priorityStr)
				: 3;

		if (idStr == null || idStr.isEmpty() ||
				startDateStr == null || startDateStr.isEmpty() ||
				startTimeStr == null || startTimeStr.isEmpty() ||
				title == null || title.isEmpty()) {

			session.setAttribute("error", "開始日・開始時間・タイトルは必須です。");
			response.sendRedirect(request.getContextPath() + "/TaskEditServlet?id=" + idStr);
			return;
		}

		// 値をTaskに詰める
		Task task = new Task();
		task.setId(id);

		task.setStartDate(LocalDate.parse(startDateStr));
		task.setEndDate(LocalDate.parse(endDateStr));

		task.setStartTime((startTimeStr != null && !startTimeStr.isEmpty())
				? LocalTime.parse(startTimeStr)
				: null);

		task.setEndTime((endTimeStr != null && !endTimeStr.isEmpty())
				? LocalTime.parse(endTimeStr)
				: null);

		task.setTitle(title);
		task.setContent(content);
		task.setCategoryId(categoryId);
		task.setPriority(priority);

		// DB更新
		TaskDao logic = new TaskDao();
		boolean isUpdated = logic.updateTask(task);

		if (isUpdated) {
			// ✅ セッションに成功メッセージを保存
			session.setAttribute("message", "タスクを更新しました。");

			// カレンダー画面へ戻る
			response.sendRedirect(request.getContextPath() + "/CalendarServlet");
		} else {
			// 失敗時
			session.setAttribute("error", "タスク更新に失敗しました。");
			response.sendRedirect(request.getContextPath() + "/TaskEditServlet?id=" + id);
		}
	}
}
