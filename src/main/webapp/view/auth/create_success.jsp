<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>アカウント作成成功</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
<!-- 共通CSS -->
</head>
<body>
<jsp:include page="/common/header.jsp" />
	<main>
		<h1>アカウント作成に成功しました！</h1>

		<p>ご登録ありがとうございます。ログイン画面でログインしてサービスを利用してください！</p>

		<form action="../view/login.jsp" method="get">
			<input type="submit" value="ログイン画面へ">
		</form>
	</main>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  	<script src="<%= request.getContextPath() %>/js/main.js"></script>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>
