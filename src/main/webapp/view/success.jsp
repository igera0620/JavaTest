<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン成功</title>
<link rel="stylesheet" href="../css/style.css"> <!-- 共通CSS -->
</head>
<body>
	<jsp:include page="/common/header.jsp" />
		<main>
		  <h1>ログイン成功！</h1>
		
		  <p>ようこそシステムへ！</p>
		
		  <form action="../view/index.jsp" method="get">
		    <input type="submit" value="ホーム画面へ">
		  </form>
		 </main>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="../js/main.js"></script>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>