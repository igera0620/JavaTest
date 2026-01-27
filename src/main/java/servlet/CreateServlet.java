package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.CreateDao;

@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("★★ CreateServlet doPost arrived ★★");

		// フォームから値を取得
		String firstName = request.getParameter("first_name");
		String lastName = request.getParameter("last_name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// ロジッククラス呼び出し
		CreateDao logic = new CreateDao();
		boolean created = logic.createAccount(firstName, lastName, email, password);

		HttpSession session = request.getSession();
		
		if (created) {
			// 登録成功 - ブラウザのURLが /CreateEngine のままになるのを防ぐためリダイレクトする
			response.sendRedirect(request.getContextPath() + "/view/auth/create_success.jsp");
		} else {
			// 登録失敗
		    session.setAttribute("error", "アカウント作成に失敗しました。登録済みのメールアドレスです。");
		    
		    session.setAttribute("input_last_name", request.getParameter("last_name"));
		    session.setAttribute("input_first_name", request.getParameter("first_name"));
		    session.setAttribute("input_email", request.getParameter("email"));
		    session.setAttribute("input_password", request.getParameter("password"));

		    // create.jsp へリダイレクト
		    response.sendRedirect(request.getContextPath() + "/view/auth/create.jsp");
		}
	}
}