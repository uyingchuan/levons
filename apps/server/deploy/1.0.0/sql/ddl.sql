use levons_db;

create table l_article
(
  id          bigint auto_increment comment '文章id'
    primary key,
  author_id   bigint                              not null comment '作者',
  title       varchar(255)                        not null comment '文章标题',
  content     text                                not null comment '文章内容',
  summary     varchar(255)                        not null comment '文章摘要',
  view_count  int       default 0                 not null comment '浏览量',
  like_count  int       default 0                 not null comment '点赞量',
  create_time timestamp default CURRENT_TIMESTAMP not null,
  update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP
)
  comment '博客、文章';

create table l_user
(
  uid         bigint auto_increment comment '业务ID'
    primary key,
  username    varchar(255)                           not null comment '用户名',
  email       varchar(255)                           not null comment '用户邮箱',
  nickname    varchar(255)                           not null comment '用户昵称',
  password    varchar(255)                           not null comment '密码',
  roles       varchar(255) default 'user'            not null comment '用户角色',
  create_time datetime     default CURRENT_TIMESTAMP null comment '创建时间',
  update_time datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
);
