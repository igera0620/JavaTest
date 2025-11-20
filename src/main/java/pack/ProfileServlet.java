package pack;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { // プロフィール表示のためのGETリクエスト処理
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false); // 既存のセッションを取得
        if (session == null || session.getAttribute("loginUserId") == null) { // セッションが存在しない、またはログインユーザーIDがセッションにない場合
            // ログインしていない場合はログイン画面に戻す
            response.sendRedirect(request.getContextPath() + "/view/index.jsp");
            return;
        }

        int userId = (int) session.getAttribute("loginUserId"); // セッションからログインユーザーIDを取得

        // DBからプロフィール情報を取得
        ProfileLogic logic = new ProfileLogic(); 
        Profile profile = logic.getProfile(userId); // ユーザーIDに基づいてプロフィールを取得

        // JSPにデータを渡す
        request.setAttribute("profile", profile);

        // profile.jsp に転送（ビューを表示）
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/profile.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
