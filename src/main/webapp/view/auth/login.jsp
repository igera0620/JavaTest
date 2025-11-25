<%@ page language="java" pageEncoding="UTF-8"%> <!-- JSPの文字コードをUTF-8に設定 -->
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false); // 既存のセッションを取得（新規作成しない）
    String loginUserEmail = null; // ログインユーザーのメールアドレスを格納する変数
    String iconFileName = null; // ユーザーアイコンのファイル名を格納する変数

    if (sessionObj != null) { // セッションが存在する場合
        loginUserEmail = (String) sessionObj.getAttribute("loginUserEmail"); // ← LoginServletで登録したキー
        iconFileName = (String) sessionObj.getAttribute("userIcon"); // ← ProfileServletで登録したキー
    }

    String iconPath; // ユーザーアイコンのパスを格納する変数
    if (iconFileName != null && !iconFileName.isEmpty()) { // ユーザーアイコンが存在する場合
        iconPath = request.getContextPath() + "/uploads/" + iconFileName; // アップロードされた画像のパス
    } else {
        iconPath = request.getContextPath() + "/images/kinnniku.jpeg"; // デフォルト画像のパス
    }
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"> <!-- pageはUTF-8で記述 -->
	<title>ログイン画面</title> <!-- ページタイトル -->
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
<jsp:include page="/common/header.jsp" />
<main>
	<%
	  String errorMsg = (String) sessionObj.getAttribute("error"); // セッションからエラーメッセージを取得
	  if (errorMsg != null) {
	%>
	    <p style="color:red; font-weight:bold;"><%= errorMsg %></p>  <!-- エラーメッセージを赤字で表示 -->
	<%
	    sessionObj.removeAttribute("error"); // エラーメッセージをセッションから削除
	  }
	%>
	
	<%
	  String logoutMsg = (String) sessionObj.getAttribute("logoutMessage"); // セッションからログアウトーメッセージを取得
	  if (logoutMsg != null) {
	%>
	    <p style="color:red; font-weight:bold;"><%= logoutMsg %></p>  <!-- ログアウトーメッセージを赤字で表示 -->
	<%
	    sessionObj.removeAttribute("logoutMessage"); // ログアウトメッセージをセッションから削除
	  }
	%>
	 
	<h1>ログイン画面</h1>

  <form action="<%= request.getContextPath() %>/LoginServlet" method="post"> <!-- フォームの送信先とメソッドを指定 -->
    <label for="email">メールアドレス</label> <!-- ラベルと入力フィールドを関連付ける -->
    <input type="email" name="email" id="email" required> <!-- ユーザIDの入力フィールド -->
    <label for="password">パスワード</label>
	<div class="password-wrapper">
	  <input type="password" name="password" id="password" required>
	  <button type="button" id="passshow">👁</button>
	</div>
    <input type="submit" value="ログイン" id="btn"> <!-- 送信ボタン -->
  </form>
  
  <p>アカウントをお持ちでない方は
    <a href="<%= request.getContextPath() %>/view/auth/create.jsp">こちら</a>から作成できます。
  </p>
  
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  </main>
 <jsp:include page="/common/footer.jsp" />
</body>
</html>