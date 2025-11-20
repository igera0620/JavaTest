<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, pack.Task" %>

<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>タスク一覧</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

  <main class="task-list-page">
    <h1>タスク一覧</h1>

    <% 
      List<Task> taskList = (List<Task>) request.getAttribute("taskList");
      String errorMsg = (String) session.getAttribute("error");
      String successMsg = (String) session.getAttribute("message");
    %>

    <% if (errorMsg != null) { %>
      <p style="color:red; font-weight:bold;"><%= errorMsg %></p>
      <% session.removeAttribute("error"); %>
    <% } %>

    <% if (successMsg != null) { %>
      <p style="color:green; font-weight:bold;"><%= successMsg %></p>
      <% session.removeAttribute("message"); %>
    <% } %>

    <% if (taskList != null && !taskList.isEmpty()) { %>
      <table class="task-table">
        <thead>
          <tr>
            <th>日付</th>
            <th>時間</th>
            <th>タイトル</th>
            <th>タグ</th>
            <th>内容</th>
            <th>優先度</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <% 
            for (Task t : taskList) {

              // ▼ 日付（単発 or 期間）
              String dateStr = "-";
              if (t.getStartDate() != null && t.getEndDate() != null) {
                  if (t.getStartDate().equals(t.getEndDate())) {
                      dateStr = t.getStartDate().toString();
                  } else {
                      dateStr = t.getStartDate() + " 〜 " + t.getEndDate();
                  }
              }

              // ▼ 時間（単発 or 期間）
              String timeStr = "-";
              if (t.getStartTime() != null && t.getEndTime() != null) {
                  timeStr = t.getStartTime().toString().substring(0, 5)
                        + "〜"
                        + t.getEndTime().toString().substring(0, 5);
              } else if (t.getStartTime() != null) {
                  timeStr = t.getStartTime().toString().substring(0, 5);
              }
          %>

            <tr>
              <td><%= dateStr %></td>
              <td><%= timeStr %></td>
              <td><%= t.getTitle() %></td>

              <td>
                <% if (t.getCategoryName() != null) { %>
                  <span class="task-tag" style="background:<%= t.getCategoryColor() %>;">
                    <%= t.getCategoryName() %>
                  </span>
                <% } else { %>
                  -
                <% } %>
              </td>

              <td><%= t.getContent() %></td>

              <td>
                <%
                  int p = t.getPriority();
                  String icon =
                      (p == 1) ? "🔴 " :
                      (p == 2) ? "🟡 " :
                                 "🟢 ";
                %>
                <%= icon %>
              </td>

              <td>
                <div class="action-buttons">
                  <form action="<%= request.getContextPath() %>/TaskEditEngine" method="get">
                    <input type="hidden" name="id" value="<%= t.getId() %>">
                    <input type="submit" value="編集" class="btn">
                  </form>
                  <form action="<%= request.getContextPath() %>/TaskDeleteEngine" method="post"
                        onsubmit="return confirm('本当に削除しますか？');">
                    <input type="hidden" name="id" value="<%= t.getId() %>">
                    <input type="submit" value="削除" class="danger_btn">
                  </form>
                </div>
              </td>
            </tr>

          <% } %>
        </tbody>
      </table>
    <% } else { %>
      <p>登録されたタスクはありません。</p>
    <% } %>

    <div class="page-buttons">
      <form action="<%= request.getContextPath() %>/TaskCreateEngine" method="get">
        <input type="submit" value="タスク登録画面へ" id="btn">
      </form>
      <form action="<%= request.getContextPath() %>/CalendarEngine" method="get">
        <input type="submit" value="カレンダー画面へ" id="btn">
      </form>
    </div>

  </main>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
