<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="model.Profile" %>
<% Profile profile = (Profile) request.getAttribute("profile"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール設定</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</link rel="stylesheet" href="<%= request.getContextPath() %>/css/usericon.css">
</head>

<body>
  <jsp:include page="/common/header.jsp" />
  <main class="profile-page">
  <h1>プロフィール設定</h1>

  <form action="<%= request.getContextPath() %>/ProfileServlet" method="post" enctype="multipart/form-data"> <!-- enctype="multipart/form-data"はファイルアップロード用 -->
  
    <label for="nickname">ニックネーム</label>
    <input type="text" name="nickname" id="nickname"
           value="<%= profile != null ? profile.getNickname() : "" %>"
           required maxlength="30" placeholder="例：いげら">

    <label for="gender">性別</label>
    <select name="gender" id="gender" required>
      <option value="">選択してください</option>
      <option value="男性" <%= (profile != null && "男性".equals(profile.getGender())) ? "selected" : "" %>>男性</option>  <!-- profileがnullではないか、profileに入ってる値が"男性"か、該当した場合selectedで該当しなければ空欄 -->
      <option value="女性" <%= (profile != null && "女性".equals(profile.getGender())) ? "selected" : "" %>>女性</option>
      <option value="その他" <%= (profile != null && "その他".equals(profile.getGender())) ? "selected" : "" %>>その他</option>
    </select>

    <label for="birth_date">生年月日</label>
    <input type="date" name="birth_date" id="birth_date"
           value="<%= profile != null ? profile.getBirthDate() : "" %>" max="<%= java.time.LocalDate.now() %>" required> <!-- profileの値がnullでなければBirthDateの値を表示,無ければ空欄,当日以降は選択できないように制限 -->

    <label for="phone">電話番号</label>
    <input type="tel" name="phone" id="phone"
           value="<%= profile != null ? profile.getPhone() : "" %>"
           maxlength="20" placeholder="090-xxxx-xxxx"
           pattern="^\d{2,4}-\d{2,4}-\d{4}$"
           title="ハイフンを含む形式で入力してください">

    <label for="address">住所</label>
    <input type="text" name="address" id="address"
           value="<%= profile != null ? profile.getAddress() : "" %>"
           maxlength="255" placeholder="例：東京都新宿区...">

    <label for="profile_text">自己紹介</label>
    <textarea name="profile_text" id="profile_text" rows="4" maxlength="300"
              placeholder="趣味や自己紹介を書いてください"><%= profile != null ? profile.getProfileText() : "" %></textarea>
              
     <!-- アイコンアップロード欄 -->
    <label for="icon">アイコン画像</label>
    <input type="file" name="icon" id="icon" accept="image/*">

    <!-- 既存のアイコンがある場合に表示 -->
    <% if (profile != null && profile.getIcon() != null && !profile.getIcon().isEmpty()) { %> <!-- profileがnullでなく、かつiconがnullでなく空欄でない場合 -->
      <p>現在のアイコン</p>
      <img src="<%= request.getContextPath() + "/uploads/" + profile.getIcon() %>"
           alt="プロフィールアイコン" width="100">  <!-- アップロードされた画像を表示 -->
    <% } %>

    <input type="submit" value="プロフィールを保存" id="btn">
  </form>

 <p><a href="<%= request.getContextPath() %>/view/index.jsp">スキップして後で設定する</a></p>

  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/main.js"></script>
  </main>
  <jsp:include page="/common/footer.jsp" />
</body>
</html>
