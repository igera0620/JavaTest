<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="pack.Task" %>

<%
    String idStr = request.getParameter("id");
    Task task = (Task) request.getAttribute("task");
%>

<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>タスク編集</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

  <main class="task-list-page">

    <%
      String errorMsg = (String) session.getAttribute("error");
      if (errorMsg != null) {
    %>
        <p style="color:red; font-weight:bold;"><%= errorMsg %></p>
    <%
          session.removeAttribute("error");
      }
    %>

    <h1>タスク編集</h1>

    <form action="<%= request.getContextPath() %>/TaskUpdateEngine" method="post">

      <!-- 更新対象ID -->
      <input type="hidden" name="id" value="<%= idStr %>">

      <!-- ▼ 開始日 -->
      <label for="start_date">開始日</label>
      <input type="date"
             id="start_date"
             name="start_date"
             value="<%= (task != null && task.getStartDate() != null)
                       ? task.getStartDate().toString()
                       : "" %>"
             required>

      <!-- ▼ 終了日 -->
      <label for="end_date">終了日</label>
      <input type="date"
             id="end_date"
             name="end_date"
             value="<%= (task != null && task.getEndDate() != null)
                       ? task.getEndDate().toString()
                       : "" %>"
             required>

      <!-- ▼ 開始時間 -->
      <label for="start_time">開始時間</label>
      <input type="time"
             id="start_time"
             name="start_time"
             value="<%= (task != null && task.getStartTime() != null)
                       ? task.getStartTime().toString().substring(0,5)
                       : "" %>">

      <!-- ▼ 終了時間 -->
      <label for="end_time">終了時間</label>
      <input type="time"
             id="end_time"
             name="end_time"
             value="<%= (task != null && task.getEndTime() != null)
                       ? task.getEndTime().toString().substring(0,5)
                       : "" %>">

      <!-- ▼ タイトル -->
      <label for="title">タイトル</label>
      <input type="text"
             id="title"
             name="title"
             value="<%= (task != null && task.getTitle() != null)
                       ? task.getTitle()
                       : "" %>"
             required maxlength="50">

      <!-- ▼ カテゴリ -->
      <%
        java.util.List<pack.TaskCategory> categoryList =
            (java.util.List<pack.TaskCategory>) request.getAttribute("categoryList");
      %>

      <label for="category_id">カテゴリ</label>
      <select name="category_id" id="category_id">
          <option value="">選択しない</option>

          <%
            if (categoryList != null) {
              for (pack.TaskCategory c : categoryList) {
                  boolean selected = (task != null && task.getCategoryId() != null
                                       && task.getCategoryId().equals(c.getId()));
          %>

            <option value="<%= c.getId() %>" <%= selected ? "selected" : "" %>>
                <%= c.getName() %>
            </option>

          <% }} %>
      </select>

      <!-- ▼ 優先度 -->
      <label for="priority">優先度</label>
      <select name="priority" id="priority">
          <option value="1" <%= task != null && task.getPriority() == 1 ? "selected" : "" %>>高</option>
          <option value="2" <%= task != null && task.getPriority() == 2 ? "selected" : "" %>>中</option>
          <option value="3" <%= task != null && task.getPriority() == 3 ? "selected" : "" %>>低</option>
      </select>

      <!-- ▼ 内容 -->
      <label for="content">内容（任意）</label>
      <textarea name="content" id="content" rows="5" maxlength="255"
                placeholder="詳細内容を入力...">
<%= (task != null && task.getContent() != null) ? task.getContent() : "" %>
      </textarea>

      <input type="submit" value="更新" class="btn">

    </form>

    <p><a href="<%= request.getContextPath() %>/CalendarEngine">カレンダーに戻る</a></p>

  </main>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />

</body>
</html>
