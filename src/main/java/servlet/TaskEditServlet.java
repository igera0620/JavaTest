package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.TaskDao;
import model.Task;
import model.TaskCategory;

/**
 * Servlet implementation class TaskEditEngine
 */
@WebServlet("/TaskEditServlet")
public class TaskEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaskEditServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // パラメータ取得
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/CalendarServlet");
            return;
        }

        int id = Integer.parseInt(idStr);

        // DBからタスク取得
        TaskDao logic = new TaskDao();
        Task task = logic.getTaskById(id);
        if (task == null) {
            // 該当タスクがなければカレンダーへ戻す
            response.sendRedirect(request.getContextPath() + "/CalendarServlet");
            return;
        }
        
        List<TaskCategory> categoryList = logic.getAllCategories();
        request.setAttribute("categoryList", categoryList);

        // JSPに渡す
        request.setAttribute("task", task);
        request.getRequestDispatcher("/view/task/edit_task.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
