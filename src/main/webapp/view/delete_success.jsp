<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント削除完了</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/usericon.css">
</head>

<body>
   <jsp:include page="/common/header.jsp" />
	  <main>
	    <h1>アカウントを削除しました</h1>
	
	    <p>ご利用ありがとうございました。<br>
	    またのご利用をお待ちしております。</p>
	
	    <form action="<%= request.getContextPath() %>/view/login.jsp" method="get">
	      <input type="submit" value="ログインページへ戻る">
	    </form>
	  </main>
	
	  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	  <script src="../js/main.js"></script>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>
