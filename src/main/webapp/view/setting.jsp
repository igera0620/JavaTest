<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String loginUserEmail = null;
    String iconFileName = null;
    String errorMsg = null;
    
    String inputLastName = null;
    String inputFirstName = null;
    String inputEmail = null;

    if (sessionObj != null) {
        loginUserEmail = (String) sessionObj.getAttribute("loginUserEmail");
        iconFileName = (String) sessionObj.getAttribute("userIcon");
        errorMsg = (String) sessionObj.getAttribute("error");
        
        inputLastName = (String) sessionObj.getAttribute("input_last_name");
        inputFirstName = (String) sessionObj.getAttribute("input_first_name");
        inputEmail = (String) sessionObj.getAttribute("input_email");
    }

    String iconPath;
    if (iconFileName != null && !iconFileName.isEmpty()) {
        iconPath = request.getContextPath() + "/uploads/" + iconFileName;
    } else {
        iconPath = request.getContextPath() + "/images/kinnniku.jpeg";
    }
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント設定</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

  <main class="setting_page">
  <% if (errorMsg != null) { %>
  <p style="color:red; font-weight:bold;"><%= errorMsg %></p>
  <%
      if (sessionObj != null) {
          sessionObj.removeAttribute("error");
      }
  %>
  <% } %>
    <h1>アカウント設定</h1>
    <p>登録情報の変更ができます。</p>

    <form action="<%= request.getContextPath() %>/SettingEngine" method="post">

      <label for="last_name">姓</label>
	  <input type="text" name="last_name" id="last_name"
	         value="<%= inputLastName != null ? inputLastName : "" %>"
	         required
	         pattern="^[A-Za-zぁ-んァ-ヶ一-龥々ー]+$"
	         title="記号・数字・空白は使えません（英字・ひらがな・カタカナ・漢字のみ）"
	         maxlength="20">
		
	  <label for="first_name">名</label>
	  <input type="text" name="first_name" id="first_name"
	         value="<%= inputFirstName != null ? inputFirstName : "" %>"
	         required
	         pattern="^[A-Za-zぁ-んァ-ヶ一-龥々ー]+$"
	         title="記号・数字・空白は使えません（英字・ひらがな・カタカナ・漢字のみ）"
	         maxlength="20">
		
	  <label for="user_email">メールアドレス</label>
	  <input type="email" name="email" id="email"
	         value="<%= inputEmail != null ? inputEmail : "" %>"
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

      <input type="submit" value="変更を保存">
  	  <input type="submit" formaction="<%= request.getContextPath() %>/view/index.jsp"
  	  		 formmethod="get" formnovalidate value="ホームへ戻る">
  	  		 
  	  <input type="submit" class="danger_btn" formaction="<%= request.getContextPath() %>/DeleteEngine"
  	  		 formmethod="post" formnovalidate value="アカウント削除" 
  	  		 onclick="return confirm('本当にアカウントを削除しますか？この操作は元に戻せません。');">
    </form>

  </main>
  
  <!--  フォーム送信後、受け取った値をリセット -->
  <%
	if (sessionObj != null) {
	    sessionObj.removeAttribute("input_last_name");
	    sessionObj.removeAttribute("input_first_name");
	    sessionObj.removeAttribute("input_email");
	}
  %>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="../js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
