
# タスク管理アプリ

Java（Servlet / JSP）と MySQL を使って構築した  
個人向けのタスク管理 Web アプリです。

カレンダー表示・カテゴリ別管理・タスク登録/編集など、  
日々の予定管理をスムーズに行えるようにしています。

---

# 機能紹介

| ユーザー登録 |
|:---:|
| <a href="https://gyazo.com/d574e1c7a15c0743db49e145575e50ae"><img src="https://i.gyazo.com/d574e1c7a15c0743db49e145575e50ae.gif" width="500"></a> |
| 登録フォームに必要情報を入力してアカウントを作成します。<br>登録完了後は成功ページへ遷移し、その後ログイン画面に移動することでサービスを利用開始できます。 |

| ログイン機能 |
|:---:|
| <a href="https://gyazo.com/bd3fa99c30549c4b543da49ab0be8398"><img src="https://i.gyazo.com/bd3fa99c30549c4b543da49ab0be8398.gif" width="500"></a> |
| 登録したメールアドレスとパスワードを入力してログインします。<br>ログインに成功すると専用の成功ページが表示され、その後ホーム画面へ遷移してサービスを利用できるようになります。 |

| ヘッダーメニュー（プロフィール編集 / 設定 / ログアウト） |
|:---:|
| <a href="https://gyazo.com/ea9caf364e3add484a96a2f9690f5538"><img src="https://i.gyazo.com/ea9caf364e3add484a96a2f9690f5538.gif" width="500"></a> |
| ログイン後、右上にユーザーアイコンが表示されます。<br>アイコンをクリックするとトグルメニューが表示され、プロフィール編集・設定・ログアウトを選択できます。 |

| プロフィール編集機能 |
|:---:|
| <a href="https://gyazo.com/d2b431394aa51cb0eb157e1e1917dde6"><img src="https://i.gyazo.com/d2b431394aa51cb0eb157e1e1917dde6.gif" width="500"></a> |
| プロフィール編集画面で、名前・住所・プロフィール画像などを入力して作成します。<br>作成後は成功ページが表示され、右上のユーザーアイコンをクリックすると最新のプロフィール情報が再表示されます。 |

| アカウント設定機能 |
|:---:|
| <a href="https://gyazo.com/efb2206a8d82bcecf54654762aa593d9"><img src="https://i.gyazo.com/efb2206a8d82bcecf54654762aa593d9.gif" width="500"></a> |
| 設定画面で変更したい値（姓・名・メールアドレスなど）を入力して保存します。<br>成功画面のあと再度設定画面へ戻ると、パスワード以外の既存の情報が最新の状態で表示されます。 |

| アカウント削除機能 |
|:---:|
| <a href="https://gyazo.com/19cb18678cc56d57c7e85b7d85d7c718"><img src="https://i.gyazo.com/19cb18678cc56d57c7e85b7d85d7c718.gif" width="500"></a> |
| アカウント削除ボタンを押すと、対象ユーザーの **del_flg が 1（論理削除）に更新** されます。<br>削除したメールアドレスでログインしようとすると「アカウントは削除されています」と表示されます。 |

| カレンダー表示機能 |
|:---:|
| <a href="https://gyazo.com/a2bc7dea576fcf5d8ab2a55e857c0a89"><img src="https://i.gyazo.com/a2bc7dea576fcf5d8ab2a55e857c0a89.gif" width="500"></a> |
| ホーム画面からカレンダー画面へ遷移すると、ログイン中ユーザーの既存タスクをDBから取得してカレンダーに表示します。<br>日付ごとにタスクが反映され、予定の確認が直感的に行えます。 |

| タスク作成機能 |
|:---:|
| <a href="https://gyazo.com/980ff95b5bf18735ff2fb8525647afb4"><img src="https://i.gyazo.com/980ff95b5bf18735ff2fb8525647afb4.gif" width="500"></a> |
| タスク登録画面でタスク名・日付・詳細を入力して登録します。<br>登録成功後は **「タスクを登録しました」** のフラッシュメッセージが表示され、カレンダーに反映されます。 |

| タスク一覧表示機能 |
|:---:|
| <a href="https://gyazo.com/b3c50db100d879224f7fb16175f033cf"><img src="https://i.gyazo.com/b3c50db100d879224f7fb16175f033cf.gif" width="500"></a> |
| 「タスク一覧」ボタンで一覧画面に移動し、登録済みタスクを一覧できます。<br>タイトル・日付・詳細などをまとめて確認できます。 |

| タスク編集機能 |
|:---:|
| <a href="https://gyazo.com/824e1acc4923caa1f4f3d64a18a19952"><img src="https://i.gyazo.com/824e1acc4923caa1f4f3d64a18a19952.gif" width="500"></a> |
| 編集ボタンからタスク内容を変更できます。<br>更新後、**「タスクが更新されました」** のフラッシュメッセージが表示されます。 |

| タスク削除機能 |
|:---:|
| <a href="https://gyazo.com/9707780750c8a222edb962aef7448f8b"><img src="https://i.gyazo.com/9707780750c8a222edb962aef7448f8b.gif" width="500"></a> |
| 削除ボタンを押すと確認ダイアログが表示され、削除を確定すると **del_flg=1** に更新され、論理削除されます。<br>その後、**「タスクを削除しました」** のメッセージが表示されます。 |

| タスク詳細表示機能 |
|:---:|
| <a href="https://gyazo.com/65461be423ddcf4258a5e8dec674e41a"><img src="https://i.gyazo.com/65461be423ddcf4258a5e8dec674e41a.gif" width="500"></a> |
| カレンダー上のタスクをクリックすると詳細画面へ遷移します。<br>内容の確認だけでなく、編集・削除もここから行えます。 |

---

# 使用技術

| カテゴリ | 技術 |
|---------|------|
| 言語 | Java 21 |
| Web | Jakarta Servlet / JSP |
| サーバー | Tomcat 10 |
| DB | MySQL 8 |
| データアクセス | DAO パターン（手書きSQL） |
| フロント | HTML / CSS / JavaScript |
| 環境 | Eclipse / WSL2 / Docker（任意） |
| バージョン管理 | GitHub |

---

## ER図

アプリで使用している主要テーブル構造です。

- **mst_users** – ユーザー基本情報  
- **mst_user_profiles** – プロフィール情報（1:1）  
- **mst_task_categories** – タスクカテゴリマスタ  
- **trn_tasks** – タスク本体（カテゴリ、日付など）

<img width="500" src="https://github.com/user-attachments/assets/f38ce1c9-bd24-4c0c-84b7-4a22cb99211b" />

