-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema partytime
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `partytime` ;

-- -----------------------------------------------------
-- Schema partytime
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `partytime` DEFAULT CHARACTER SET utf8 ;
USE `partytime` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT(4) NOT NULL DEFAULT '1',
  `created_at` DATETIME NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_unique` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

INSERT INTO `partytime`.`user`
(`id`,
`username`,
`password`,
`enabled`,
`created_at`,
`role`,
`first_name`,
`last_name`,
`updated_at`)
VALUES
('1', 'planner@pasciak.com', '$2a$10$MaOJ.VrpBKnaujGXrKbCQOSmHIoELU/eR3HIStEjNWGuY3m96Ke5K', '1', '2024-06-19 12:24:54', 'standard', 'Planner', 'Pasciak', '2024-06-19 12:24:54');


-- -----------------------------------------------------
-- Table `event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `event` ;

CREATE TABLE IF NOT EXISTS `event` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `lat` DOUBLE NULL DEFAULT NULL,
  `lng` DOUBLE NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  `datetime` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_event_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

INSERT INTO `partytime`.`event`
(`id`,
`lat`,
`lng`,
`user_id`,
`datetime`)
VALUES
(1,
44.0,
-1111.0,
1,
'2024-06-19 12:44:44');

-- -----------------------------------------------------
-- Table `event_invite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `event_invite` ;

CREATE TABLE IF NOT EXISTS `event_invite` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `user_id` INT(11) NOT NULL,
  `event_id` INT(11) NOT NULL,
  `comment` VARCHAR(255) NULL DEFAULT NULL,
  `attending` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_invite_user1_idx` (`user_id` ASC),
  INDEX `fk_event_invite_event1_idx` (`event_id` ASC),
  CONSTRAINT `fk_event_invite_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_invite_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 1
DEFAULT CHARACTER SET = utf8;

INSERT INTO `partytime`.`event_invite`
(
`user_id`,
`event_id`,
`comment`,
`attending`)
VALUES
(1,
1,
'',
NULL);

SET SQL_MODE = '';
DROP USER IF EXISTS planner@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'planner'@'localhost' IDENTIFIED BY 'planner';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'planner'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'planner'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
