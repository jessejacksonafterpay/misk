CREATE TABLE text_tokens(
  id bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  text varchar(255) NOT NULL,
  token varchar(32) NOT NULL COLLATE utf8_bin,
  UNIQUE KEY `unq_token` (`token`)
);