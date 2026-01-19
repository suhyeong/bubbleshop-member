use member;

alter table member_master drop column passwd;
alter table member_master drop column member_nickname;
alter table member_master drop column email;
alter table member_master drop column email_recv_agree_yn;

alter table member_master modify phone_num varchar(255) not null comment '전화번호';
alter table member_master add column phone_num_hash varchar(64) not null comment '검색전용 해시 전화번호' after phone_num;

alter table member_master add column provider_code varchar(1) not null comment '회원가입 제공 플랫폼 코드' after phone_num_hash;
alter table member_master add column provider_id varchar(255) not null comment '회원가입 제공 고유 아이디' after provider_code;