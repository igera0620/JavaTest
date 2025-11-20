package pack;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class TaskListEngine
 */
@WebServlet("/TaskListEngine")
public class TaskListEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskListEngine() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false);
	        if (session == null || session.getAttribute("loginUserId") == null) {
	            session = request.getSession();
	            session.setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
	            response.sendRedirect(request.getContextPath() + "/view/index.jsp");
	            return;
	        }

	        int userId = (int) session.getAttribute("loginUserId");

	        // DBからタスク一覧を取得
	        TaskLogic logic = new TaskLogic();
	        List<Task> taskList = logic.getTasksByUserId(userId);

	        // JSPにデータを渡してフォワード
	        request.setAttribute("taskList", taskList);
	        request.getRequestDispatcher("/view/list_task.jsp").forward(request, response);
	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
