CREATE TABLE `stock_item` (
  `id`          BIGINT(21)  NOT NULL AUTO_INCREMENT,
  `type`        VARCHAR(45) NOT NULL,
  `title`       VARCHAR(45) NOT NULL,
  `description` VARCHAR(1000)        DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8;