use member;

alter table member_master drop column passwd;
alter table member_master drop column member_nickname;
alter table member_master drop column email;
alter table member_master drop column email_recv_agree_yn;

alter table member_master add column join_platf_code varchar(1) not null comment '가입 경로 코드';