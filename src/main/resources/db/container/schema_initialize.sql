create database member;
create user member identified by 'welcome';
grant all privileges on member.* to member;
grant super on *.* to member;