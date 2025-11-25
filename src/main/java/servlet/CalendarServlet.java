package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TaskDao;
import model.Task;

/**
 * Servlet implementation class CalendarEngine
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession(false); // 既存のセッションを取得、なければnullを返す
		 if (session == null || session.getAttribute("loginUserId") == null) {

			    // ★ session が null の場合は新規作成
			    if (session == null) {
			        session = request.getSession(true);
			    }

			    session.setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
			    response.sendRedirect(request.getContextPath() + "/view/login.jsp");
			    return;
			}

	        int userId = (int) session.getAttribute("loginUserId"); // セッションからログインユーザーIDを取得

	        // 年と月のパラメータを取得、なければ現在の年と月を使用
	        int year = request.getParameter("year") != null
	                ? Integer.parseInt(request.getParameter("year"))
	                : LocalDate.now().getYear();
	        int month = request.getParameter("month") != null
	                ? Integer.parseInt(request.getParameter("month"))
	                : LocalDate.now().getMonthValue();

	        TaskDao logic = new TaskDao(); // TaskLogicのインスタンスを作成
	        List<Task> taskList = logic.getTasksByMonth(userId, year, month); // 指定された月のタスクを取得
	        
	        System.out.println("カレンダー取得: userId=" + userId + ", " + year + "年" + month + "月, タスク数=" + taskList.size());

	        request.setAttribute("taskList", taskList); // タスクリストをリクエスト属性に設定
	        request.setAttribute("year", year); // 年をリクエスト属性に設定
	        request.setAttribute("month", month); // 月をリクエスト属性に設定

	        request.getRequestDispatcher("/view/calendar/calendar_page.jsp").forward(request, response); // カレンダーページにフォワード
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
