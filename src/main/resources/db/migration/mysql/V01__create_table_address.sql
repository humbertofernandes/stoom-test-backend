CREATE TABLE IF NOT EXISTS `stoom`.`endereco` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `street_name` VARCHAR(45) NOT NULL,
  `number` VARCHAR(45) NOT NULL,
  `complement` VARCHAR(45) NULL,
  `neighbourhood` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `state` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `zipcode` VARCHAR(45) NOT NULL,
  `latitude` VARCHAR(45) NULL,
  `longitude` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
