package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.LoginDao;
import dao.ProfileDao;
import model.Profile;

/**
 * Servlet implementation class LoginEngine
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath()); // 動作確認用
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) // form actionで送信ボタンを押した際に呼び出し
			throws ServletException, IOException {


		// フォームから送られてきた値を取得
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// ログインチェック用のクラス（インスタンス）を使う
		LoginDao logic = new LoginDao(); // LoginLogicクラスのインスタンスを生成
		int result = logic.loginCheck(email, password); // SQLでータを照合して、結果を受け取る

		if (result == 0) {
			// 成功したらログイン成功画面へ
			HttpSession session = request.getSession(); // セッションを取得
			session.setAttribute("loginUserEmail", email); // メールアドレスを保存

			int userId = logic.getUserIdByEmail(email); // メールアドレスからユーザーIDを取得
			session.setAttribute("loginUserId", userId); // ユーザーIDをセッションに保存

			// ここでDBからプロフィール情報を取得
			ProfileDao profileLogic = new ProfileDao(); // ProfileLogicクラスのインスタンスを生成
			Profile profile = profileLogic.getProfile(userId); // ユーザーIDに基づいてプロフィールを取得

			if (profile != null && profile.getIcon() != null && !profile.getIcon().isEmpty()) {
				session.setAttribute("userIcon", profile.getIcon()); // アイコンファイル名をセッションに保存
			} else {
				session.setAttribute("userIcon", ""); // まだ登録してない場合
			}

			// 成功したらログイン後画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/view/auth/login_success.jsp");

		} else if (result == 1) {
			// 削除済みアカウントの場合
			HttpSession session = request.getSession();
			session.setAttribute("error", "このアカウントは削除されています。");
			response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");

		} else {
			// 失敗したらエラーメッセージを表示
			HttpSession session = request.getSession(); // ログイン情報が何もないのでセッションを取得する必要があるため記述
			session.setAttribute("error", "メールアドレスまたはパスワードが違います。"); // エラーメッセージをセッションに保存。requestではなくsessionに保存することでリダイレクト先でも参照可能にする。
			response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
		}
	}
}