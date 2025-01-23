use member;

alter table `review_master` add column `rvw_show_yn` varchar(1) not null default 'Y' comment '리뷰 공개 여부' after `rvw_cont`;
alter table `review_master` add column `point_pay_yn` varchar(1) not null default 'N' comment '포인트 지급 여부' after `rvw_show_yn`;