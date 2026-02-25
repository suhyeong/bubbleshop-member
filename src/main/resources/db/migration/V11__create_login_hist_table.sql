use member;

drop table if exists member_login_history;
drop table if exists external_interface_history;

create table member_login_history (
    login_hist_sn bigint not null auto_increment comment '이력 순번',
    member_id varchar(100) not null comment '회원 아이디',
    provider_code varchar(1) not null comment '회원가입 제공 플랫폼 코드',
    login_ip varchar(100) not null comment '로그인 시도 IP',
    crt_dt datetime not null comment '생성 일시',
    primary key (login_hist_sn)
);

create table external_interface_history (
  ext_intf_hist_sn bigint not null auto_increment comment '외부 인터페이스 호출 이력 순번',
  ext_intf_tp varchar(255) not null comment '외부 인터페이스 타입',
  member_id varchar(100) comment '회원 아이디',
  ext_intf_req_url varchar(500) not null comment '외부 인터페이스 호출 요청 URL',
  ext_intf_req_dt datetime not null comment '외부 인터페이스 호출 요청일시',
  ext_intf_rsp_cd varchar(50) not null comment '외부 인터페이스 응답 결과코드',
  ext_intf_rsp_msg varchar(1000) comment '외부 인터페이스 응답 결과메세지',
  ext_intf_rsp_dt datetime not null comment '외부 인터페이스 응답일시',
  crt_dt datetime not null comment '생성 일시',
  primary key (ext_intf_hist_sn)
);