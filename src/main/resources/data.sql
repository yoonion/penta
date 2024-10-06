-- 아래 초기 테스트용 유저의 비밀번호는 모두 '1234' 입니다. 비밀번호 암호화가 적용되어있으며, 로그인 시 비밀번호 '1234' 입력하면 로그인 가능합니다.
-- admin 계정을 제외한 다른 유저의 권한은 'SYSTEM_USER' (일반회원) 입니다.
-- admin 계정 - ID: admin / PW: 1234
-- 일반회원 계정 - ID: test1, test2, test3 / PW: 1234

INSERT INTO `SYSTEM_USER` (`user_idx`, `user_id`, `user_pw`, `user_nm`, `user_auth`) VALUES
(1, 'admin', '$2a$10$2hlN1smKkFs/m6NeXQeVu.HAZR2xCFCsESxz78d3fiy1VeOMIbrcu', '관리자', 'SYSTEM_ADMIN');
INSERT INTO `SYSTEM_USER` (`user_idx`, `user_id`, `user_pw`, `user_nm`, `user_auth`) VALUES
(2, 'test1', '$2a$10$2hlN1smKkFs/m6NeXQeVu.HAZR2xCFCsESxz78d3fiy1VeOMIbrcu', 'user1', 'SYSTEM_USER');
INSERT INTO `SYSTEM_USER` (`user_idx`, `user_id`, `user_pw`, `user_nm`, `user_auth`) VALUES
(3, 'test2', '$2a$10$2hlN1smKkFs/m6NeXQeVu.HAZR2xCFCsESxz78d3fiy1VeOMIbrcu', 'user2', 'SYSTEM_USER');
INSERT INTO `SYSTEM_USER` (`user_idx`, `user_id`, `user_pw`, `user_nm`, `user_auth`) VALUES
(4, 'test3', '$2a$10$2hlN1smKkFs/m6NeXQeVu.HAZR2xCFCsESxz78d3fiy1VeOMIbrcu', 'user3', 'SYSTEM_USER');