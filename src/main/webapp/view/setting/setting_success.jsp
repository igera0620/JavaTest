<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>アカウント更新完了</title>
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/usericon.css">
</head>

<body>
	<jsp:include page="/common/header.jsp" />
	  <main>
	    <h1>アカウント情報を更新しました</h1>
	
	    <p>登録内容の変更が完了しました。<br>
	    引き続きサービスをご利用いただけます。</p>
	
	    <form action="<%= request.getContextPath() %>/view/index.jsp" method="get">
	      <input type="submit" value="ホーム画面へ戻る">
	    </form>
	  </main>
	
	  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	  <script src="../js/main.js"></script>
	  <jsp:include page="/common/footer.jsp" />
</body>
</html>
