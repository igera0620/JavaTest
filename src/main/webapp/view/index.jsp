<%@ page language="java" pageEncoding="UTF-8"%> <!-- JSPの文字コードをUTF-8に設定 -->
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false); // 既存のセッションを取得（新規作成しない）
    String loginUserEmail = null; // ログインユーザーのメールアドレスを格納する変数
    String iconFileName = null; // ユーザーアイコンのファイル名を格納する変数

    if (sessionObj != null) { // セッションが存在する場合
        loginUserEmail = (String) sessionObj.getAttribute("loginUserEmail"); // ← LoginEngineで登録したキー
        iconFileName = (String) sessionObj.getAttribute("userIcon"); // ← ProfileEngineで登録したキー
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
<title>ホーム画面</title> <!-- ページタイトル -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />
   <main>
    <% if (loginUserEmail != null) { %>
      <h1>カレンダーで現在のタスクを確認しよう！</h1>
        <form action="<%= request.getContextPath() %>/CalendarEngine" method="get">
          <input type="submit" value="カレンダー画面へ" id="btn">
        </form>
    <% } else { %>
      <h1>ようこそ ゲスト さん！</h1>
      <p>ログインしていません。</p>
      <form action="<%= request.getContextPath() %>/view/login.jsp" method="get">
        <input type="submit" value="ログイン画面へ" id="btn">
      </form>
    <% } %>
  </main>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>