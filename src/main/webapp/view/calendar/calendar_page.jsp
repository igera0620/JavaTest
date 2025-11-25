<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>カレンダー画面</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/calendar.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

 <main class="calendar-main">
  <% 
      // セッションに保存されたメッセージを取得
      String message = (String) session.getAttribute("message");
      if (message != null) { 
    %>
        <p style="color: green; font-weight: bold; text-align: center; margin: 10px 0;"><%= message %></p>
    <%
        // 表示後に削除（1回だけ表示するため）
        session.removeAttribute("message");
      }
    %>
    
    <%
	  // セッションに保存されたエラーメッセージ
	  String error = (String) session.getAttribute("error");
	  if (error != null) {
	%>
	    <p style="color: red; font-weight: bold; text-align: center; margin: 10px 0;">
	      <%= error %>
	    </p>
	<%
	    session.removeAttribute("error");
	  }
	%>
  <jsp:include page="/common/calendar.jsp" />

  <div class="calendar-buttons">
    <form action="<%= request.getContextPath() %>/TaskCreateServlet" method="get">
      <input type="submit" value="タスク登録画面へ" id="btn">
    </form>
    <form action="<%= request.getContextPath() %>/TaskListServlet" method="get">
      <input type="submit" value="タスク一覧へ" id="btn">
    </form>
    <form action="<%= request.getContextPath() %>/view/index.jsp" method="get">
      <input type="submit" value="ホーム画面へ" id="btn">
    </form>
  </div>
</main>

  <!-- jQuery と main.js は必ずここで読み込む（header連動用） -->
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>

  <jsp:include page="/common/footer.jsp" />
</body>
</html>
