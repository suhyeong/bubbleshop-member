use member;

drop table if exists authority_master;
drop table if exists member_authority_master;

create table authority_master(
    authority_id bigint not null comment '권한 아이디',
    role varchar(1000) not null comment '권한',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (authority_id)
);

create table member_authority_master(
    authority_id bigint not null comment '권한 아이디',
    member_id varchar(100) not null comment '회원 아이디',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (authority_id, member_id)
);