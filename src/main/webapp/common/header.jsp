<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String loginUserEmail = null;
    String iconFileName = null;

    if (sessionObj != null) {
        loginUserEmail = (String) sessionObj.getAttribute("loginUserEmail");
        iconFileName = (String) sessionObj.getAttribute("userIcon");
    }

    String iconPath;
    if (iconFileName != null && !iconFileName.isEmpty()) {
        iconPath = request.getContextPath() + "/uploads/" + iconFileName;
    } else {
        iconPath = request.getContextPath() + "/images/kinnniku.jpeg";
    }
%>

<!-- CSS / JSは基本的にhead内に入れるのが推奨 -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">

<header class="site-header">
  <div class="header-left">
    <h1><a href="<%= request.getContextPath() %>/view/index.jsp">ユニサーブ</a></h1>
  </div>

  <% if (loginUserEmail != null) { %>
  <div class="header-right">
    <div id="userIconContainer">
      <div id="userIcon" style="background-image: url('<%= iconPath %>');"></div>
      <div id="userMenu">
        <p><strong><%= loginUserEmail %></strong></p>
        <a href="<%= request.getContextPath() %>/ProfileServlet">プロフィール編集</a>
        <a href="<%= request.getContextPath() %>/SettingServlet">設定</a>
        <a href="<%= request.getContextPath() %>/LogoutServlet">ログアウト</a>
      </div>
    </div>
  </div>
  <% } %>
</header>
