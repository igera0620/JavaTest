package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.TaskDao;
import model.Task;

/**
 * Servlet implementation class TaskShowEngine
 */
@WebServlet("/TaskShowServlet")
public class TaskShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskShowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		// パラメータの id を取得
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            // id が無ければカレンダーに戻す
            response.sendRedirect(request.getContextPath() + "/CalendarServlet");
            return;
        }

        int id = Integer.parseInt(idStr);

        // DB からタスク取得
        TaskDao logic = new TaskDao();
        Task task = logic.getTaskById(id);
        
        if (task == null) {
            session.setAttribute("error", "指定されたタスクの表示に失敗しました。再度お試しください。");
            response.sendRedirect(request.getContextPath() + "/CalendarServlet");
            return;
        }

        // JSP へ渡す
        request.setAttribute("task", task);
        request.getRequestDispatcher("/view/task/show_task.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
