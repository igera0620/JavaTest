<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List, pack.TaskCategory" %>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>タスク登録</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

  <main>
    <h1>タスク登録</h1>

    <!-- メッセージ表示 -->
    <%
      String errorMsg = (String) session.getAttribute("error");
      if (errorMsg != null) {
    %>
      <p style="color:red; font-weight:bold;"><%= errorMsg %></p>
      <% session.removeAttribute("error"); %>
    <%
      }

      String successMsg = (String) session.getAttribute("message");
      if (successMsg != null) {
    %>
      <p style="color:green; font-weight:bold;"><%= successMsg %></p>
      <% session.removeAttribute("message"); %>
    <%
      }
    %>

    <!-- 入力値保持 -->
    <%
		  String inputStartDate = (String) session.getAttribute("input_task_date_start");
		  String inputEndDate   = (String) session.getAttribute("input_task_date_end");
		  String inputStartTime = (String) session.getAttribute("input_start_time");
		  String inputEndTime   = (String) session.getAttribute("input_end_time");
		  String inputTitle     = (String) session.getAttribute("input_title");
		  String inputContent   = (String) session.getAttribute("input_content");
		
		  List<TaskCategory> categoryList = (List<TaskCategory>) request.getAttribute("categoryList");
		
		  session.removeAttribute("input_task_date_start");
		  session.removeAttribute("input_task_date_end");
		  session.removeAttribute("input_start_time");
		  session.removeAttribute("input_end_time");
		  session.removeAttribute("input_title");
		  session.removeAttribute("input_content");
	 %>

    <form action="<%= request.getContextPath() %>/TaskCreateEngine" method="post">

	  <!-- ★ 開始日 -->
	  <label for="task_date_start">開始日</label>
	  <input type="date" name="task_date_start" id="task_date_start"
	         value="<%= inputStartDate != null ? inputStartDate : "" %>"
	         required>
	
	  <!-- ★ 終了日 -->
	  <label for="task_date_end">終了日</label>
	  <input type="date" name="task_date_end" id="task_date_end"
	         value="<%= inputEndDate != null ? inputEndDate : "" %>"
	         required>
	
	  <!-- ★ 開始時間 -->
	  <label for="start_time">開始時間</label>
	  <input type="time" name="start_time" id="start_time"
	         value="<%= inputStartTime != null ? inputStartTime : "" %>">
	
	  <!-- ★ 終了時間 -->
	  <label for="end_time">終了時間</label>
	  <input type="time" name="end_time" id="end_time"
	         value="<%= inputEndTime != null ? inputEndTime : "" %>">
	
	  <!-- タイトル -->
	  <label for="title">タイトル</label>
	  <input type="text" name="title" id="title" 
	         value="<%= inputTitle != null ? inputTitle : "" %>"
	         required maxlength="50">
	
	  <!-- 内容 -->
	  <label for="content">内容</label>
	  <textarea name="content" id="content" rows="5" maxlength="255"><%= 
	      inputContent != null ? inputContent : "" 
	  %></textarea>
	
	  <!-- カテゴリ -->
	  <label for="category_id">カテゴリ</label>
	  <select name="category_id" id="category_id">
	    <option value="">選択しない</option>
	    <% if (categoryList != null) {
	         for (TaskCategory c : categoryList) { %>
	         <option value="<%= c.getId() %>"><%= c.getName() %></option>
	    <% } } %>
	  </select>
	
	  <!-- 優先度 -->
	  <label for="priority">優先度</label>
	  <select name="priority" id="priority">
	    <option value="1">高</option>
	    <option value="2">中</option>
	    <option value="3" selected>低</option
	  </select>
	
	  <input type="submit" value="登録" id="btn">
	</form>
	
	<p><a href="<%= request.getContextPath() %>/CalendarEngine">カレンダーに戻る</a></p>
  </main>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>

