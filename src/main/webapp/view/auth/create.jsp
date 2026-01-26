<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false); // 既存のセッションを取得（新規作成しない）
    String lastName = "";
    String firstName = "";
    String email = "";
    String errorMsg = null;

    if (sessionObj != null) {
        lastName = (String) sessionObj.getAttribute("input_last_name");
        firstName = (String) sessionObj.getAttribute("input_first_name");
        email = (String) sessionObj.getAttribute("input_email");
        errorMsg = (String) sessionObj.getAttribute("error");

        sessionObj.removeAttribute("input_last_name");
        sessionObj.removeAttribute("input_first_name");
        sessionObj.removeAttribute("input_email");
        sessionObj.removeAttribute("error");
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント作成</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
<jsp:include page="/common/header.jsp" />
  <main>
  <h1>アカウント作成</h1>
  
    <% if (errorMsg != null) { %>
	  <p style="color:red; font-weight:bold;"><%= errorMsg %></p>
	<% } %>
  <form action="<%= request.getContextPath() %>/CreateServlet" method="post">
	  <label for="last_name">姓</label>
	  <input type="text" name="last_name" id="last_name"
	         value="<%= lastName != null ? lastName : "" %>"
	         required
	         pattern="^[A-Za-zぁ-んァ-ヶ一-龥々ー]+$"
	         title="記号・数字・空白は使えません（英字・ひらがな・カタカナ・漢字のみ）"
	         maxlength="20">
	
	  <label for="first_name">名</label>
	  <input type="text" name="first_name" id="first_name"
	         value="<%= firstName != null ? firstName : "" %>"
	         required
	         pattern="^[A-Za-zぁ-んァ-ヶ一-龥々ー]+$"
	         title="記号・数字・空白は使えません（英字・ひらがな・カタカナ・漢字のみ）"
	         maxlength="20">

	  <label for="user_email">メールアドレス</label>
	  <input type="email" name="email" id="email"
		     value="<%= email != null ? email : "" %>"
		     required
		     pattern="^\S+$"
		     title="スペースを含めないでください"
		     maxlength="50">
	
	  <label for="pass">パスワード</label>
	  <input type="password" name="password" id="password" required pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,8}"
	  title="半角英字と半角数字をそれぞれ1文字以上含む4〜8文字（スペース不可）" maxlength="8">
	
	  <label for="pass_check">パスワード（確認）</label>
	  <input type="password" name="pass_check" id="pass_check" required pattern="(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{4,8}"
	  title="半角英字と半角数字をそれぞれ1文字以上含む4〜8文字（スペース不可）" maxlength="8">
	  
	  <input type="submit" value="アカウント作成" id="btn">
  </form>
  
  <p>すでにアカウントをお持ちですか？ <a href="../view/index.jsp">ログインはこちら</a></p>
  </main>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
