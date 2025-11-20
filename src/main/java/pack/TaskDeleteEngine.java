package pack;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TaskDeleteEngine
 */
@WebServlet("/TaskDeleteEngine")
public class TaskDeleteEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskDeleteEngine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

	    int id = Integer.parseInt(request.getParameter("id"));

	    // ログインユーザーID（セッションから取得）
	    HttpSession session = request.getSession();
	    int userId = (int) session.getAttribute("loginUserId");

	    TaskLogic logic = new TaskLogic();
	    boolean result = logic.deleteTask(id, userId);

	    if (result) {
	        session.setAttribute("message", "タスクを削除しました。");
	    } else {
	        session.setAttribute("message", "タスクの削除に失敗しました。");
	    }

	    response.sendRedirect(request.getContextPath() + "/CalendarEngine");
	}
}
