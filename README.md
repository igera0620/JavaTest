# タスク管理アプリ

Java（Servlet / JSP）と MySQL を使って構築した  
個人向けのタスク管理 Web アプリです。

カレンダー表示・カテゴリ別管理・タスク登録/編集など、  
日々の予定管理をスムーズに行えるようにしています。

---

# 機能紹介

## ユーザー登録

<a href="https://gyazo.com/d574e1c7a15c0743db49e145575e50ae"><img src="https://i.gyazo.com/d574e1c7a15c0743db49e145575e50ae.gif" width="600"></a>

登録フォームに必要情報を入力してアカウントを作成します。  
登録完了後は成功ページへ遷移し、その後ログイン画面に移動してサービスを利用開始できます。

---

## ログイン機能

<a href="https://gyazo.com/bd3fa99c30549c4b543da49ab0be8398"><img src="https://i.gyazo.com/bd3fa99c30549c4b543da49ab0be8398.gif" width="600"></a>

登録したメールアドレスとパスワードを入力してログインします。  
ログインに成功すると成功ページが表示され、その後ホーム画面へ遷移してサービスが利用できます。

---

## ヘッダーメニュー（プロフィール編集 / 設定 / ログアウト）

<a href="https://gyazo.com/ea9caf364e3add484a96a2f9690f5538"><img src="https://i.gyazo.com/ea9caf364e3add484a96a2f9690f5538.gif" width="600"></a>

ログイン後、右上にユーザーアイコンが表示されます。  
クリックするとメニューが展開され、プロフィール編集・設定・ログアウトが選択できます。

---

## プロフィール編集機能

<a href="https://gyazo.com/d2b431394aa51cb0eb157e1e1917dde6"><img src="https://i.gyazo.com/d2b431394aa51cb0eb157e1e1917dde6.gif" width="600"></a>

プロフィール編集画面で、名前・住所・プロフィール画像を入力して作成します。  
作成後は成功ページが表示され、右上のアイコンも最新情報に更新されます。

---

## アカウント設定機能

<a href="https://gyazo.com/efb2206a8d82bcecf54654762aa593d9"><img src="https://i.gyazo.com/efb2206a8d82bcecf54654762aa593d9.gif" width="600"></a>

変更したい情報（姓・名・メールアドレスなど）を入力して保存できます。  
パスワード以外の情報は成功後に再度画面へ戻ると更新されています。

---

## アカウント削除機能

<a href="https://gyazo.com/19cb18678cc56d57c7e85b7d85d7c718"><img src="https://i.gyazo.com/19cb18678cc56d57c7e85b7d85d7c718.gif" width="600"></a>

アカウント削除ボタンを押すと **del_flg=1** に更新され論理削除されます。  
削除後はログインできなくなります。

---

## カレンダー表示機能

<a href="https://gyazo.com/a2bc7dea576fcf5d8ab2a55e857c0a89"><img src="https://i.gyazo.com/a2bc7dea576fcf5d8ab2a55e857c0a89.gif" width="600"></a>

ログイン中ユーザーのタスクをDBから取得し、カレンダーに表示します。  
日付ごとにタスクを一覧できます。

---

## タスク作成機能

<a href="https://gyazo.com/980ff95b5bf18735ff2fb8525647afb4"><img src="https://i.gyazo.com/980ff95b5bf18735ff2fb8525647afb4.gif" width="600"></a>

タスク登録画面で名前・日付・詳細を入力して作成できます。  
作成後は **「タスクを登録しました」** のメッセージが表示されます。

---

## タスク一覧表示機能

<a href="https://gyazo.com/b3c50db100d879224f7fb16175f033cf"><img src="https://i.gyazo.com/b3c50db100d879224f7fb16175f033cf.gif" width="600"></a>

登録済みタスクを一覧画面でまとめて確認できます。

---

## タスク編集機能

<a href="https://gyazo.com/824e1acc4923caa1f4f3d64a18a19952"><img src="https://i.gyazo.com/824e1acc4923caa1f4f3d64a18a19952.gif" width="600"></a>

編集画面からタスクを更新できます。  
更新後は **「タスクが更新されました」** のメッセージが表示されます。

---

## タスク削除機能

<a href="https://gyazo.com/9707780750c8a222edb962aef7448f8b"><img src="https://i.gyazo.com/9707780750c8a222edb962aef7448f8b.gif" width="600"></a>

削除ボタンを押して確認すると論理削除（del_flg=1）されます。  
削除後は **「タスクを削除しました」** が表示されます。

---

## タスク詳細表示機能

<a href="https://gyazo.com/65461be423ddcf4258a5e8dec674e41a"><img src="https://i.gyazo.com/65461be423ddcf4258a5e8dec674e41a.gif" width="600"></a>

カレンダー上のタスクをクリックすると詳細画面へ遷移します。  
ここから編集や削除も可能です。


---

# 使用技術

| カテゴリ | 技術 |
|---------|------|
| 言語 | Java 21 |
| Web | Jakarta Servlet / JSP |
| サーバー | Tomcat 10 |
| DB | MySQL 8 |
| データアクセス | JDBC（DAOパターン／手書きSQL） |
| フロント | HTML / CSS / JavaScript |
| 環境 | Eclipse / WSL2 / Docker |
| バージョン管理 | GitHub |

---

## ER図

アプリで使用している主要テーブル構造です。

- **mst_users** – ユーザー基本情報  
- **mst_user_profiles** – プロフィール情報（1:1）  
- **mst_task_categories** – タスクカテゴリマスタ  
- **trn_tasks** – タスク本体（カテゴリ、日付など）

<img width="500" src="https://i.gyazo.com/42eae481fcbf1b89ac3a09641ab5b5ab.png" alt="Gyazo image" />
