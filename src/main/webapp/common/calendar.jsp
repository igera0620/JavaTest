<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.time.*, java.util.*, pack.Task"%>
<%@ page import="java.time.temporal.ChronoUnit" %>

<%
/* 年月セット */
int year = (request.getAttribute("year") != null)
        ? (Integer) request.getAttribute("year")
        : LocalDate.now().getYear();

int month = (request.getAttribute("month") != null)
        ? (Integer) request.getAttribute("month")
        : LocalDate.now().getMonthValue();

// タスクリスト取得
List<Task> taskList = (List<Task>) request.getAttribute("taskList"); 
if (taskList == null) taskList = new ArrayList<>(); // null対策

// 月初・月末日付取得
LocalDate firstDay = LocalDate.of(year, month, 1); // 月初を取得
LocalDate lastDay  = firstDay.withDayOfMonth(firstDay.lengthOfMonth()); // 月末を取得

// 週開始・週終了日付取得
LocalDate startWeek = firstDay.with(DayOfWeek.MONDAY); // 1日を基準に、週の最初の月曜まで戻す
LocalDate endWeek   = lastDay.with(DayOfWeek.SUNDAY);  // 月末を基準に、週の最後の日曜まで進める

LocalDate today = LocalDate.now(); // サーバーの現在時刻から今日の日付を取り出している

// 前月・次月に移動するリンク作成のため記述
LocalDate prevMonth = firstDay.minusMonths(1); // 表示している月の1日から1ヶ月引く
LocalDate nextMonth = firstDay.plusMonths(1); // 表示している月の1日から1ヶ月足す

// 月全体を「週単位」に分割してリストにする処理
// 月曜始まり、日曜終わりで揃えるため、実際の月の前の月末や次の月初の日付も含まれる
List<LocalDate[]> weeks = new ArrayList<>(); // 週ごとに7日分の日付を格納する配列をリスト化
LocalDate cursor = startWeek; // 週の開始日からループ開始
while (!cursor.isAfter(endWeek)) { // 週の終了日までループ
    LocalDate[] week = new LocalDate[7]; // 1週間分の日付を格納する配列を作成
    for (int i = 0; i < 7; i++) // 7日分の日付を配列にセット
        week[i] = cursor.plusDays(i); // cursorからi日後の日付をセット
    weeks.add(week); // 週配列をリストに追加
    cursor = cursor.plusWeeks(1); // cursorを1週間進める
}

// 取得したタスクをバーとして描画するための情報をまとめたクラス
class TaskSpan {
    Task task; // タスク本体
    int startCol; // 週の何日目から始まるか（0〜6）
    int span;    // 何日分にまたがるか（1〜7）
    TaskSpan(Task t, int sc, int sp) { task = t; startCol = sc; span = sp; } // 取得した値をここにセットしている
}

// 週ごとにタスクバーの行情報をまとめたリスト
List<List<List<TaskSpan>>> weekRows = new ArrayList<>(); //TaskSpanで計算された、どの曜日から始まるか、何日跨ぐか、どのタスクかを出し、実際のカレンダーの描写に使用

// 各週ごとにタスクを配置する処理
// 一週間分のタスクについて、何週から何日延びるか、タスク同士がかぶらないように何行目に置くかを計算してweekRowsに詰めている
for (LocalDate[] week : weeks) {

    List<List<TaskSpan>> rows = new ArrayList<>(); // その週に表示するタスクバーの行リスト

    for (Task t : taskList) { // すべてのタスクを確認
        LocalDate s = t.getStartDate(); // タスク開始日
        LocalDate e = t.getEndDate(); // タスク終了日

        LocalDate ws = week[0]; // 週開始日（月曜）
        LocalDate we = week[6]; // 週終了日（日曜）

        if (e.isBefore(ws) || s.isAfter(we)) continue; // 週範囲外のタスクはスキップ

        LocalDate bs = s.isBefore(ws) ? ws : s; // タスクバー開始日
        LocalDate be = e.isAfter(we) ? we : e; // タスクバー終了日

        int col = (int) ChronoUnit.DAYS.between(ws, bs); // タスクバー開始日の週内での列位置
        int span = (int) ChronoUnit.DAYS.between(bs, be) + 1; // タスクバーの横幅（日数）

        TaskSpan ts = new TaskSpan(t, col, span); // タスクバー情報をまとめる

        boolean placed = false; // タスクバーが配置されたかどうかのフラグ
        for (List<TaskSpan> line : rows) { // 既存の行に配置できるか確認
            boolean conflict = false; // 競合フラグ
            for (TaskSpan x : line) { // 既存のタスクバーと競合するか確認
                if (!(col + span - 1 < x.startCol || x.startCol + x.span - 1 < col)) { // 競合判定
                    conflict = true; // 競合あり
                    break; // 確認終了
                }
            }
            if (!conflict) { // 競合なしなら配置
                line.add(ts); // 行にタスクバーを追加
                placed = true; // 配置済みフラグを立てる
                break; // 確認終了
            }
        }

        if (!placed) { // 既存の行に配置できなかった場合、新しい行を作成
            List<TaskSpan> newLine = new ArrayList<>(); // 新しい行を作成
            newLine.add(ts); // タスクバーを追加
            rows.add(newLine); // 行リストに追加
        }
    }

    weekRows.add(rows); // その週のタスクバー行リストを週リストに追加
}
%>

<!-- ナビ -->
<div class="calendar-nav">
    <a class="nav-arrow"
       href="<%=request.getContextPath()%>/CalendarEngine?year=<%=prevMonth.getYear()%>&month=<%=prevMonth.getMonthValue()%>">&lt;</a>

    <span id="monthDisplay" class="month-display"><%=year%>年 <%=month%>月</span>

    <select id="yearMonthSelect" class="month-select" style="display:none;">
        <% for(int y = 1950; y <= 2030; y++){
               for(int m=1; m<=12; m++){ %>
              <option value="<%=y%>-<%=m%>" <%= (year==y&&month==m?"selected":"") %>>
                <%=y%>年 <%=m%>月
              </option>
        <% }} %>
    </select>

    <form id="ymForm" method="get" action="<%=request.getContextPath()%>/CalendarEngine"></form>

    <a class="nav-arrow"
       href="<%=request.getContextPath()%>/CalendarEngine?year=<%=nextMonth.getYear()%>&month=<%=nextMonth.getMonthValue()%>">&gt;</a>
</div>

<!-- カレンダー本体 -->
<table class="calendar">
<thead>
<tr>
    <th>月</th><th>火</th><th>水</th><th>木</th><th>金</th><th>土</th><th>日</th>
</tr>
</thead>
<tbody>

		<!--  -->
	<%
		for (int wi = 0; wi < weeks.size(); wi++) {
		    LocalDate[] week = weeks.get(wi);
		
		    // 日付行
		    out.println("<tr>");
		    for (int i = 0; i < 7; i++) {
		        LocalDate d = week[i];
		        boolean isMonth = (d.getMonthValue() == month);
		        String cls = (!isMonth ? "empty" : (d.equals(today) ? "today" : ""));
		        out.println("<td class='"+cls+"'><div class='date-num'>"+d.getDayOfMonth()+"</div></td>");
		    }
		    out.println("</tr>");
		
		    // タスク行（複数行
		    List<List<TaskSpan>> lines = weekRows.get(wi);
		    for (List<TaskSpan> line : lines) {
		
		        out.println("<tr class='task-row'>");
		
		        int col = 0;
		        for (TaskSpan ts : line) {
		            while (col < ts.startCol) {
		                out.println("<td></td>");
		                col++;
		            }
		
		            Task t = ts.task;
		            String color = t.getCategoryColor() != null ? t.getCategoryColor() : "#999";
		            String tag = t.getCategoryName() != null ? t.getCategoryName() : "";
		            String startTimeStr = (t.getStartTime() != null)
		                    ? t.getStartTime().toString().substring(0,5)
		                    : "";
		
		            String endTimeStr = (t.getEndTime() != null)
		                    ? t.getEndTime().toString().substring(0,5)
		                    : "";
		
		            String time = "";
		            if (!startTimeStr.isEmpty() && !endTimeStr.isEmpty()) {
		                time = startTimeStr + "〜" + endTimeStr;
		            } else if (!startTimeStr.isEmpty()) {
		                time = startTimeStr;
		            } else if (!endTimeStr.isEmpty()) {
		                time = "〜" + endTimeStr;
		            }
		            String dotClass = "prio-"+t.getPriority();
		
		            out.println("<td colspan='"+ts.span+"' class='task-cell'>");
		            out.println("<div class='task-bar' style='--tagColor:"+color+";'>");
		            out.println("<span class='priority-dot "+dotClass+"'></span>");
		            out.println("<a href='"+request.getContextPath()+"/TaskShowEngine?id="+t.getId()+"'>");
		            out.println(time+" "+t.getTitle());
		            out.println("</a>");
		            out.println("<span class='task-tag' style='background:"+color+";'>"+tag+"</span>");
		            out.println("</div></td>");
		
		            col += ts.span;
		        }
		
		        while (col < 7) {
		            out.println("<td></td>");
		            col++;
		        }
		
		        out.println("</tr>");
		    }
		}
	%>

</tbody>
</table>
