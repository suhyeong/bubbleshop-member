use member;

alter table member_master drop column provider_code;
alter table member_master drop column provider_id;

drop table if exists member_social_accnt_master;

create table member_social_accnt_master (
      member_id varchar(100) not null comment '회원 아이디',
      provider_code varchar(1) not null comment '회원가입 제공 플랫폼 코드',
      provider_id varchar(255) not null comment '회원가입 제공 고유 아이디',
      provider_email varchar(255) not null comment '이메일',
      crt_dt datetime not null comment '생성 일시',
      chn_dt datetime not null comment '수정 일시',
      primary key (member_id, provider_code)
);