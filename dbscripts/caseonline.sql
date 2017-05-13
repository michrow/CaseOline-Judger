-- 创建数据库

create database caseonline character set utf8 collate utf8_general_ci;

-- 创建表

/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/4/6 15:51:25                            */
/*==============================================================*/

/*==============================================================*/
/* Table: coj_excellence_usecase                                */
/*==============================================================*/
drop table if exists coj_excellence_usecase;
create table coj_excellence_usecase
(
   submission_id       bigint not null,
   testcase_id          bigint,
   usecase_coverage     float,
   usecase_amount      int,
   usecase              varchar(256),
    excebak1                 varchar(32),
   excebak2                 varchar(128),
   excebak3                 varchar(256),
   primary key (submission_id)
);

alter table coj_excellence_usecase comment '存放优秀的测试用例设计';

/*==============================================================*/
/* Table: coj_languages                                         */
/*==============================================================*/
drop table if exists coj_language;
create table coj_language
(
   language_id          int not null auto_increment,
   language_name        varchar(16) not null,
   build_file           text not null comment '使用ant运行测试用例',
   format_id            int not null,
   languagebak1                 varchar(32),
   languagebak2                 varchar(128),
   languagebak3                 varchar(256),
   primary key (language_id)
);

alter table coj_language comment '所支持的语言，及与语言相关联的信息';

/*==============================================================*/
/* Table: coj_submissions                                       */
/*==============================================================*/
drop table if exists coj_submission;
create table coj_submission
(
   submission_id       bigint not null  auto_increment,
   testcase_id          bigint not null,
   user_id              bigint not null,
   language_id          int not null default 1,
   submission_submit_time timestamp not null,
   usecase              varchar(256) not null comment '测试用例数据，格式根据不同语言来定',
   usecase_amount      int,
   usecase_coverage     float,
   judge_result_flag VARCHAR(32) not null default 'PD',
   judge_log text,
   submitbak1                 varchar(32),
   submitbak2                 varchar(128),
   submitbak3                 varchar(256),
   primary key (submission_id)
);

alter table coj_submission comment '提交测试案例';

/*==============================================================*/
/* Table: coj_testcase                                          */
/*==============================================================*/
drop table if exists coj_testcase;
create table coj_testcase
(
   testcase_id          bigint not null auto_increment,
   is_public            char not null default '1',
   testcase_name        varchar(32) not null,
   testcase_code        text not null,
   language_id             int not null,
   total_submission   int,
   total_user_submission int,
   max_coverage float,
   testcase__description varchar(128),
   testcase_param_attr  text  comment '针对测试要测试类的方法的属性，参数、返回值（json 字符串）',
   casebak1                 varchar(32),
   casebak2                 varchar(128),
   casebak3                 varchar(256),
   primary key (testcase_id)
);

alter table coj_testcase comment '测试案例表';

/*==============================================================*/
/* Table: coj_testcase_categories                               */
/*==============================================================*/
drop table if exists coj_testcase_category;
create table coj_testcase_category
(
   category_id          int not null auto_increment,
   category_type        varchar(16) not null,
   category_name        varchar(32) not null,
   pre_category_id      int comment '用于类型嵌套',
   catebak1                 varchar(32),
   catebak2                 varchar(128),
   catebak3                 varchar(256),
   primary key (category_id)
);

alter table coj_testcase_category comment '测试案例类型';

/*==============================================================*/
/* Table: coj_testcase_rela_cate                                */
/*==============================================================*/
drop table if exists coj_testcase_rela_cate;
create table coj_testcase_rela_cate
(
   testcase_id          bigint not null,
   category_id          int not null,
   relabak1                 varchar(32),
   relabak2                 varchar(128),
   relabak3                 varchar(256),
   primary key (testcase_id, category_id)
);

alter table coj_testcase_rela_cate comment '测试案例和类型关系表';

/*==============================================================*/
/* Table: coj_usecase_formats                                   */
/*==============================================================*/
drop table if exists coj_usecase_format;
create table coj_usecase_format
(
   format_id            int not null auto_increment,
   format_text          text not null,
   formatbak1                 varchar(32),
   formatbak2                 varchar(128),
   formatbak3                 varchar(256),
   primary key (format_id)
);

alter table coj_usecase_format comment '向用户展示测试用例编写格式';

/*==============================================================*/
/* Table: coj_user_role                                         */
/*==============================================================*/
drop table if exists coj_user_role;
create table coj_user_role
(
   role_id              int not null auto_increment,
   role_type            varchar(16) not null,
   role_name            varchar(16) not null,
   rolebak1                 varchar(32),
   rolebak2                 varchar(128),
   rolebak3                 varchar(256),
   primary key (role_id)
);

alter table coj_user_role comment '用户角色类型分类';

/*==============================================================*/
/* Table: coj_users                                             */
/*==============================================================*/

drop table if exists coj_user;
create table coj_user
(
   user_id              bigint not null auto_increment,
   user_name            varchar(16) not null,
   password             varchar(64) not null,
   email                varchar(32) not null,
   role_id              int not null,
   register_time        timestamp default current_timestamp,
   userbak1                 varchar(32),
   userbak2                 varchar(128),
   userbak3                 varchar(256),
   primary key (user_id)
);

alter table coj_user comment '记录用户信息';

/*==============================================================*/
/* Table: coj_judge_result                                             */
/*==============================================================*/
drop table if exists coj_judge_result;
CREATE TABLE  coj_judge_result
(
        judge_result_id INT(4) NOT NULL AUTO_INCREMENT,
        judge_result_flag VARCHAR(4) NOT NULL,
        judge_result_name VARCHAR(32) NOT NULL,
       primary key (judge_result_id)
 );
alter table coj_judge_result comment  '测评的结果';

CREATE TABLE  coj_option
    (
        option_id INT NOT NULL AUTO_INCREMENT,
        key_ VARCHAR(64) NOT NULL,
        value_ VARCHAR(32),
        PRIMARY KEY (option_id)
    );
    alter table coj_option comment  '网站信息';
