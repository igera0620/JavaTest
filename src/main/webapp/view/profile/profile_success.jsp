<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>プロフィール登録完了</title>
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>
<body>
	<jsp:include page="/common/header.jsp" />
		<main>
		<h1>プロフィール登録が完了しました！</h1>
	
		<p>ご登録ありがとうございます。<br>登録したプロフィール情報をもとに、マイページや各種機能をご利用いただけます。</p>
	
		<form action="../view/index.jsp" method="get">
			<input type="submit" value="ホーム画面へ戻る">
		</form>
		</main>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script src="<%= request.getContextPath() %>/js/main.js"></script>
	<jsp:include page="/common/footer.jsp" />
</body>
</html>
