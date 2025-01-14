use member;

drop table if exists member_master;

create table member_master(
    member_id varchar(100) not null comment '회원 아이디',
    passwd text not null comment '비밀번호',
    member_name varchar(30) not null comment '회원명',
    member_nickname varchar(50) comment '회원 닉네임',
    phone_num text not null comment '전화번호',
    join_dt datetime not null comment '가입 일시',
    withdrawal_dt datetime comment '탈퇴 일시',
    birth_dt datetime comment '생년월일',
    email varchar(100) comment '이메일',
    email_recv_agree_yn varchar(1) default 'N' not null comment '이메일 수신 동의 여부',
    point integer default 0 not null comment '포인트',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (member_id)
);