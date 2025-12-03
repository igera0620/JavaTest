package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.SettingDao;
import model.User;

/**
 * Servlet implementation class SettingEngine
 */
@WebServlet("/SettingServlet")
public class SettingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SettingServlet() {
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
		        response.sendRedirect(request.getContextPath() + "/view/login.jsp");
		        return;
		    }

		    int userId = (int) session.getAttribute("loginUserId");

		    SettingDao dao = new SettingDao();
		    User user = dao.getUserInfo(userId);

		    // フォームの初期値として session に保存
		    session.setAttribute("input_last_name", user.getLastName());
		    session.setAttribute("input_first_name", user.getFirstName());
		    session.setAttribute("input_email", user.getEmail());

		    request.getRequestDispatcher("/view/setting/setting.jsp").forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		// フォームの値を取得
		String lastName = request.getParameter("last_name");
		String firstName = request.getParameter("first_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String passCheck = request.getParameter("pass_check");

		// セッションからユーザーIDを取得
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserId") == null) {
		    session.setAttribute("error", "ログインセッションが切れています。再ログインしてください。");
		    response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
		    return;
		}
		
		int userId = (int) session.getAttribute("loginUserId");

		// パスワード確認
		if (!password.equals(passCheck)) {
			request.setAttribute("error", "⚠️ パスワードが一致しません。");
			request.getRequestDispatcher("/view/setting/settings.jsp").forward(request, response);
			return;
		}

		// ロジックを呼び出して更新処理を実行
		SettingDao logic = new SettingDao();
		boolean isUpdated = logic.updateUserInfo(userId, lastName, firstName, email, password);

		// 結果に応じて遷移先を分岐
		if (isUpdated) {
			session.setAttribute("loginUserEmail", email); // セッション上のメールも更新
			request.setAttribute("message", "アカウント情報を更新しました。");
			response.sendRedirect(request.getContextPath() + "/view/setting/setting_success.jsp");
			
		} else if (logic.existsEmailForOtherUser(email, userId)) {
			HttpSession session3 = request.getSession();
			session3.setAttribute("error", "このメールアドレスは既に使用されています。");
			
			session3.setAttribute("input_last_name", request.getParameter("last_name"));
		    session3.setAttribute("input_first_name", request.getParameter("first_name"));
		    session3.setAttribute("input_email", request.getParameter("email"));
		    
			response.sendRedirect(request.getContextPath() + "/view/setting/setting.jsp");

		} else {
			HttpSession session3 = request.getSession();
			session3.setAttribute("error", "更新に失敗しました。もう一度お試しください。");
			
			session3.setAttribute("input_last_name", request.getParameter("last_name"));
		    session3.setAttribute("input_first_name", request.getParameter("first_name"));
		    session3.setAttribute("input_email", request.getParameter("email"));
		    
			response.sendRedirect(request.getContextPath() + "/view/setting/setting.jsp");
		}
	}
}