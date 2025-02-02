use member;

drop table if exists comment_history;

create table comment_history(
   cmnt_no varchar(100) not null comment '코멘트 번호',
   cmnt_hist_seq integer not null comment '코멘트 이력 순번',
   cmnt_hist_type varchar(1) not null comment '코멘트 이력 타입',
   cmnt_type varchar(1) not null comment '코멘트 타입',
   target_no varchar(100) not null comment '타겟 번호',
   cmnt_cont text not null comment '코멘트 내용',
   crt_by varchar(50) not null comment '생성자',
   crt_dt datetime not null comment '생성 일시',
   chn_dt datetime not null comment '수정 일시',
   primary key (cmnt_no, cmnt_hist_seq)
);