-- 스프링 부트 재실행 시, 존재하는 테이블을 drop 하고 새로 생성합니다.
DROP TABLE IF EXISTS `USER_HISTORY`;
DROP TABLE IF EXISTS `SYSTEM_USER`;

CREATE TABLE `SYSTEM_USER` (
  `user_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '회원 기본키',
  `user_id` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '회원 아이디',
  `user_pw` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '회원 비밀번호',
  `user_nm` varchar(100) COLLATE utf8_unicode_ci NOT NULL COMMENT '회원 이름',
  `user_auth` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '회원 권한',
  PRIMARY KEY (`user_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `USER_HISTORY` (
  `history_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '회원 기록 기본키',
  `url` text COLLATE utf8_unicode_ci NOT NULL COMMENT 'URL',
  `action_type` enum('C','U','D') COLLATE utf8_unicode_ci NOT NULL COMMENT '회원 기록 유형',
  `reg_user_idx` int(11) NOT NULL COMMENT '최초 등록 회원 기본키',
  `reg_ip` varchar(16) COLLATE utf8_unicode_ci NOT NULL COMMENT '최초 등록 아이피',
  `reg_dt` datetime NOT NULL COMMENT '최초 등록 일시',
  PRIMARY KEY (`history_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;