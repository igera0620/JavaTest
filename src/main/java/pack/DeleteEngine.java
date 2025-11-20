package pack;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DeleteEngine
 */
@WebServlet("/DeleteEngine")
public class DeleteEngine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteEngine() {
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
		// セッション取得（ログイン情報を利用）
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loginUserEmail") == null) {
            // 未ログインならログイン画面へ
            response.sendRedirect(request.getContextPath() + "/view/login.jsp");
            return;
        }

        String email = (String) session.getAttribute("loginUserEmail"); // セッションからメールアドレスを取得

        // ロジッククラスを呼び出し
        DeleteLogic logic = new DeleteLogic();
        boolean success = logic.deleteUserByEmail(email); // ユーザー削除処理を実行

        if (success) {
            // セッション破棄してログアウト
            session.invalidate();

            // 完了画面にリダイレクト
            response.sendRedirect(request.getContextPath() + "/view/delete_success.jsp");
        } else {
            // 失敗時
            session.setAttribute("error", "削除処理中にエラーが発生しました。");
            response.sendRedirect(request.getContextPath() + "/view/setting.jsp");
        }
    }
}