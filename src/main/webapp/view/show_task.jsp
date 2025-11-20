<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="pack.Task" %>

<%
    Task task = (Task) request.getAttribute("task");
%>

<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>タスク詳細</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />

  <main class="task-list-page">

    <h1>タスク詳細</h1>

    <div class="task-detail-box">

      <table class="task-detail-table">
        <tbody>

          <!-- ▼ 日付（開始〜終了） -->
          <tr>
            <th>日付</th>
            <td>
              <%=
                (task.getStartDate() != null ? task.getStartDate() : "") +
                " 〜 " +
                (task.getEndDate() != null ? task.getEndDate() : "")
              %>
            </td>
          </tr>

          <!-- ▼ 時間（開始〜終了） -->
          <tr>
            <th>時間</th>
            <td>
              <%
                String sTime = (task.getStartTime() != null)
                                ? task.getStartTime().toString().substring(0,5)
                                : "";
                String eTime = (task.getEndTime() != null)
                                ? task.getEndTime().toString().substring(0,5)
                                : "";

                String timeStr = (sTime.isEmpty() && eTime.isEmpty())
                                  ? "なし"
                                  : sTime + " 〜 " + eTime;
              %>
              <%= timeStr %>
            </td>
          </tr>

          <!-- ▼ タイトル -->
          <tr>
            <th>タイトル</th>
            <td><%= task.getTitle() %></td>
          </tr>

          <!-- ▼ カテゴリー -->
          <tr>
            <th>カテゴリー</th>
            <td>
              <% if (task.getCategoryName() != null) { %>
                <span class="task-tag"
                      style="background:<%= task.getCategoryColor() %>;">
                  <%= task.getCategoryName() %>
                </span>
              <% } else { %>
                なし
              <% } %>
            </td>
          </tr>

          <!-- ▼ 優先度 -->
          <tr>
            <th>優先度</th>
            <td>
              <%
                int p = task.getPriority();
                String icon =
                    (p == 1) ? "🔴 高" :
                    (p == 2) ? "🟡 中" :
                               "🟢 低";
              %>
              <%= icon %>
            </td>
          </tr>

          <!-- ▼ 内容 -->
          <tr>
            <th>内容</th>
            <td>
              <pre class="task-content"><%= task.getContent() != null ? task.getContent() : "" %></pre>
            </td>
          </tr>

        </tbody>
      </table>

      <!-- 編集・削除ボタン -->
      <div class="action-buttons mt-20">
        <form action="<%= request.getContextPath() %>/TaskEditEngine" method="get">
          <input type="hidden" name="id" value="<%= task.getId() %>">
          <input type="submit" value="編集" class="btn">
        </form>

        <form action="<%= request.getContextPath() %>/TaskDeleteEngine" method="post"
              onsubmit="return confirm('本当に削除しますか？');">
          <input type="hidden" name="id" value="<%= task.getId() %>">
          <input type="submit" value="削除" class="danger_btn">
        </form>
      </div>

      <!-- カレンダーへ戻る -->
      <div class="page-buttons mt-25">
        <form action="<%= request.getContextPath() %>/CalendarEngine" method="get">
          <input type="submit" value="カレンダー画面へ" id="btn">
        </form>
      </div>

    </div>

  </main>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
