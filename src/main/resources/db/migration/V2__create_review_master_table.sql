use member;

drop table if exists review_master;
drop table if exists review_img_mng;
drop table if exists comment_master;

create table review_master(
    rvw_no varchar(100) not null comment '리뷰 번호',
    member_id varchar(100) not null comment '회원 아이디',
    prd_code varchar(50) not null comment '상품 코드',
    prd_score int not null comment '상품 점수',
    rvw_cont text not null comment '리뷰 내용',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (rvw_no)
);

create table review_img_mng(
    rvw_no varchar(100) not null comment '리뷰 번호',
    rvw_img_seq int not null comment '리뷰 이미지 순번',
    rvw_img_path varchar(50) not null comment '리뷰 이미지 경로',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (rvw_no, rvw_img_seq)
);

create table comment_master(
    cmnt_no varchar(100) not null comment '코멘트 번호',
    cmnt_type varchar(1) not null comment '코멘트 타입',
    target_no varchar(100) not null comment '타겟 번호',
    cmnt_cont text not null comment '코멘트 내용',
    crt_dt datetime not null comment '생성 일시',
    chn_dt datetime not null comment '수정 일시',
    primary key (cmnt_no)
);