use member;

alter table `comment_master` add column `crt_by` varchar(50) not null comment '생성자' after `cmnt_cont`;