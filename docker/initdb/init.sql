create database if not exists myapp_db -- データベース作成。既に存在する場合はスキップ
  character set utf8mb4                -- 文字コードをutf8mb4に設定
  collate utf8mb4_general_ci;          -- 照合順序をutf8mb4_general_ciに設定

use myapp_db;

set foreign_key_checks = 0;            -- 外部キー制約を一時的に無効化

create table mst_users (
  id int auto_increment primary key,             -- ユーザーid（主キー）
  first_name varchar(50) not null,               -- 名
  last_name varchar(50) not null,                -- 姓
  email varchar(255) not null unique,            -- メールアドレス（重複禁止）
  password varchar(255) not null,                -- ハッシュ化パスワード
  created_at timestamp default current_timestamp, -- 登録日時
  deleted_at datetime default null,              -- 論理削除日時（削除時にセット）
  del_flg tinyint default 0                      -- 削除フラグ（0=有効 / 1=削除済）
) engine=innodb default charset=utf8mb4;

create table mst_user_profiles (
  id int auto_increment primary key,               -- プロフィールid（主キー）
  user_id int not null,                            -- mst_users.id への外部キー
  nickname varchar(50) default null,               -- ニックネーム
  gender varchar(10) default null,                 -- 性別（文字列管理）
  birth_date date default null,                    -- 生年月日
  phone varchar(20) default null,                  -- 電話番号
  address varchar(255) default null,               -- 住所
  profile_text text default null,                  -- 自己紹介文
  icon varchar(255) default null,                  -- アイコン画像ファイル名
  created_at timestamp default current_timestamp,  -- 登録日時
  updated_at timestamp default current_timestamp on update current_timestamp, -- 更新日時
  constraint fk_user_profile_user
    foreign key (user_id) references mst_users(id)
    on delete cascade
) engine=innodb default charset=utf8mb4;

create table mst_task_categories (
  id int auto_increment primary key,                 -- カテゴリID（主キー）
  name varchar(50) not null,                         -- カテゴリ名（仕事 / 勉強 / 生活など）
  color varchar(20) not null,                        -- 表示用カラーコード（例: #ff6666）
  created_at timestamp default current_timestamp,     -- 登録日時
  updated_at timestamp default current_timestamp 
              on update current_timestamp             -- 更新日時
) engine=innodb default charset=utf8mb4;

create table trn_tasks (
  id int auto_increment primary key,                            -- タスクid（主キー）
  user_id int not null,                                         -- mst_users.id への外部キー
  category_id int default null,                                 -- mst_task_categories.id（任意）
  priority tinyint default 3,                                   -- 優先度（1:高、2:中、3:低）

  task_date_start date not null,                                -- ★ タスク開始日
  task_date_end   date not null,                                -- ★ タスク終了日
  start_time      time default null,                            -- ★ 開始時間
  end_time        time default null,                            -- ★ 終了時間

  title varchar(50) not null,                                   -- タスクタイトル
  content text default null,                                    -- タスク内容

  created_at timestamp default current_timestamp,               -- 作成日時
  updated_at timestamp default current_timestamp  on update current_timestamp,                      -- 更新日時
  deleted_at datetime default null,                             -- 論理削除日時
  del_flg tinyint default 0,                                    -- 削除フラグ（0=有効 / 1=削除済）

  constraint fk_task_user 
    foreign key (user_id) references mst_users(id)
    on delete cascade,

  constraint fk_task_category 
    foreign key (category_id) references mst_task_categories(id)
    on delete set null
) engine=innodb default charset=utf8mb4;

insert into mst_task_categories (name, color) values
('緊急', '#ff0000'),           -- 赤色
('仕事', '#28a745'),           -- 緑色
('プライベート', '#007bff'),   -- 青色
('筋トレ', '#ff8800');         -- オレンジ色

set foreign_key_checks = 1;            -- 外部キー制約を再度有効化

