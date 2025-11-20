$(function() {

	//パスワード表示切り替え
	$('#passshow').on('click', () => {
		const $p = $("#password, #pass_check");
		const nowPassword = $p.attr("type") === "password";
		$p.attr("type", nowPassword ? "text" : "password");
	});

	//パスワード一致チェック（ここが追加）
	const password = document.getElementById("password");
	const passCheck = document.getElementById("pass_check");

	function validatePasswordMatch() {
		if (password.value !== passCheck.value) {
			passCheck.setCustomValidity("パスワードが一致していません。");
		} else {
			passCheck.setCustomValidity("");
		}
	}

	password.addEventListener("input", validatePasswordMatch);
	passCheck.addEventListener("input", validatePasswordMatch);

});

// ユーザーアイコンのクリックでメニュー表示・非表示
document.addEventListener("DOMContentLoaded", function() { // HTMLの読み込みが完了したら実行
	const icon = document.getElementById("userIcon"); // ユーザーアイコンの要素を取得
	const menu = document.getElementById("userMenu"); // メニューの要素を取得
	if (icon && menu) {
		icon.addEventListener("click", () => {
			menu.classList.toggle("show"); // メニューの表示・非表示を切り替え
		});
		document.addEventListener("click", (e) => {
			if (!icon.contains(e.target) && !menu.contains(e.target)) { // アイコンとメニュー以外がクリックされた場合
				menu.classList.remove("show"); // メニューを非表示
			}
		});
	}
});

document.addEventListener("DOMContentLoaded", function() { // HTMLの読み込みが完了したら実行
	const birthInput = document.getElementById("birth_date"); // ID属性で要素を探して、変数に入れる
	if (birthInput) { // 要素が存在する場合のみ実行
		const today = new Date(); // 今日の日付を取得
		const yyyy = today.getFullYear(); // 年を取得
		const mm = String(today.getMonth() + 1).padStart(2, '0'); // 月を取得（0から始まるので+1して2桁に）
		const dd = String(today.getDate()).padStart(2, '0'); // 日を取得（2桁に）
		const formattedDate = `${yyyy}-${mm}-${dd}`; // YYYY-MM-DD形式に整形

		// max属性に今日の日付をセット
		birthInput.max = formattedDate;
	}
});

document.querySelector("form").addEventListener("submit", function(e) { // フォームが送信されるときに実行
	const pass = document.getElementById("password").value; // パスワードの値を取得
	const passCheck = document.getElementById("pass_check").value; // パスワード確認の値を取得

	if (pass !== passCheck) { // パスワードと確認が一致しない場合
		alert("⚠️ パスワードが一致していません。"); // 警告メッセージを表示
		e.preventDefault(); // フォーム送信を止める
	}
});

document.addEventListener("DOMContentLoaded", () => {
	const calendar = document.querySelector(".calendar");
	const main = document.querySelector("main");

	// カレンダーが存在するページだけ実行する
	if (!calendar || !main) return;

	// カレンダーの位置を取得
	const rect = calendar.getBoundingClientRect();

	// 基準となるヘッダーとの距離
	const baseMargin = 120;

	// カレンダーが押し上げられている場合の差分を計算
	const extra = rect.top < baseMargin ? (baseMargin - rect.top) : 0;

	// main の margin-top を自動調整
	main.style.marginTop = (baseMargin + extra) + "px";
});

document.addEventListener("DOMContentLoaded", () => {
    const monthDisplay = document.getElementById("monthDisplay");
    const select = document.getElementById("yearMonthSelect");
    const form = document.getElementById("ymForm");

    // contextPath を JSP から渡す（必要なので）
    const contextPath = form.getAttribute("action").replace("/CalendarEngine", "");

    // ① span をクリック → セレクト表示
    monthDisplay.addEventListener("click", () => {
        monthDisplay.style.display = "none";
        select.style.display = "inline-block";
        select.focus();
    });

    // ② セレクト変更でカレンダー遷移
    select.addEventListener("change", () => {
        const [y, m] = select.value.split("-");

        form.innerHTML = `
            <input type="hidden" name="year" value="${y}">
            <input type="hidden" name="month" value="${m}">
        `;

        form.submit();
    });

    // ③ フォーカス外れたら戻す
    select.addEventListener("blur", () => {
        select.style.display = "none";
        monthDisplay.style.display = "inline";
    });
});
