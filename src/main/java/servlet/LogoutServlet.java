package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutEngine
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response); // GETリクエストでもPOSTリクエストと同じ処理を行う
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 HttpSession session = request.getSession(false); // 既存のセッションを取得

	        if (session != null) {
	            session.invalidate(); // セッション破棄 → ログアウト
	        }

	        // ログイン画面へ戻る
	        HttpSession session2 = request.getSession(); // 新しいセッションを作成
	        session2.setAttribute("logoutMessage", "ログアウトしました"); // ログアウトメッセージを保存
	        response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp"); // ログイン画面へリダイレクト
	    }
	}