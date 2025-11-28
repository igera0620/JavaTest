package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import dao.ProfileDao;
import model.Profile;
/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet("/ProfileServlet") //プロフィール保存用サーブレット
@MultipartConfig( // ファイルアップロードを受け取るための設定。指定しないと「ファイルアップロードを受け取る設定がされていません」という例外が発生。
		fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 5, // 5MB
		maxRequestSize = 1024 * 1024 * 10 // 10MB
)
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
        ProfileDao logic = new ProfileDao(); 
        Profile profile = logic.getProfile(userId); // ユーザーIDに基づいてプロフィールを取得

        // JSPにデータを渡す
        request.setAttribute("profile", profile);

        // profile.jsp に転送（ビューを表示）
        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/profile/profile.jsp");
        dispatcher.forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		// フォームデータ取得
		String nickname = request.getParameter("nickname");
		String gender = request.getParameter("gender");
		String birthDate = request.getParameter("birth_date");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String profileText = request.getParameter("profile_text");
		
		Profile inputProfile = new Profile();
        inputProfile.setNickname(nickname);
        inputProfile.setGender(gender);
        inputProfile.setBirthDate(birthDate);
        inputProfile.setPhone(phone);
        inputProfile.setAddress(address);
        inputProfile.setProfileText(profileText);

		Part iconPart = request.getPart("icon"); //文字データではなく、バイナリデータが送られるためPartで受け取る
		String iconFileName = null; // アップロードされたファイル名を取得
		
		if (iconPart != null && iconPart.getSize() > 0) { // ファイルがアップロードされているか確認

	        // MIMEチェック（画像以外は拒否）
	        String mime = iconPart.getContentType(); // アップロードされたファイルのMIMEタイプを取得
	        if (mime == null || !mime.startsWith("image/")) { // MIMEタイプが画像で始まらない場合
	            request.setAttribute("error", "画像ファイル以外はアップロードできません。"); // エラーメッセージをリクエストに設定
	            request.setAttribute("profile", inputProfile); // 入力されたプロフィール情報をリクエストに設定
	            request.getRequestDispatcher("/view/profile/profile.jsp").forward(request, response); // プロフィールページにフォワード
	            return;
	        }

	        // アップロードファイル名取得
	        iconFileName = Paths.get(iconPart.getSubmittedFileName()).getFileName().toString(); // ブラウザによってはフルパスで送信されることがあるため、ファイル名のみを抽出

	        // 保存先フォルダ作成
	        String uploadDir = getServletContext().getRealPath("/uploads"); // アップロード先のディレクトリパスを取得
	        File uploadDirFile = new File(uploadDir); // Fileオブジェクトを作成
	        if (!uploadDirFile.exists()) uploadDirFile.mkdirs(); // ディレクトリが存在しない場合は作成

	        // ファイル保存
	        iconPart.write(uploadDir + File.separator + iconFileName); // ファイルを指定のディレクトリに保存
	    }

		HttpSession session = request.getSession(false); // 既存のセッションを取得
		int userId = 0; // デフォルト値
		if (session != null && session.getAttribute("loginUserId") != null) { // // セッションが存在することを確認→ユーザーiDがセッションに保存されているか確認
			userId = (int) session.getAttribute("loginUserId"); // ユーザーIDをセッションから取得
		} else {
			session.setAttribute("error", "ログイン情報がありません。再度ログインしてください。");
			response.sendRedirect(request.getContextPath() + "/view/auth/login.jsp");
			return;
		}

		// ロジックに処理を任せる
		ProfileDao logic = new ProfileDao();
		boolean result = logic.saveProfile(userId, nickname, gender, birthDate, phone, address, profileText, iconFileName);

		if (result) {
			// 成功時にセッションへアイコンファイル名を登録
			if (iconFileName != null && !iconFileName.isEmpty()) {
				session.setAttribute("userIcon", iconFileName);
			}
			response.sendRedirect(request.getContextPath() + "/view/profile/profile_success.jsp");
		} else {
			request.setAttribute("error", "プロフィールの保存中にエラーが発生しました。");
			request.setAttribute("profile", inputProfile);
			response.sendRedirect(request.getContextPath() + "/view/profile/profile.jsp");
		}
	}
}
