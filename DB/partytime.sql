-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

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
-- Table `partytime`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `partytime`.`user` ;

CREATE TABLE IF NOT EXISTS `partytime`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT '1',
  `created_at` DATETIME NULL DEFAULT NULL,
  `role` VARCHAR(100) NULL DEFAULT NULL,
  `first_name` VARCHAR(100) NULL DEFAULT NULL,
  `last_name` VARCHAR(100) NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `user_unique` (`username` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 3
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
-- Table `partytime`.`event`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `partytime`.`event` ;

CREATE TABLE IF NOT EXISTS `partytime`.`event` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `lat` DOUBLE NULL,
  `lng` DOUBLE NULL,
  `user_id` INT NOT NULL,
  `datetime` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_event_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `partytime`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

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
-- Table `partytime`.`event_invite`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `partytime`.`event_invite` ;

CREATE TABLE IF NOT EXISTS `partytime`.`event_invite` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `event_id` INT NOT NULL,
  `comment` VARCHAR(255) NULL,
  `attending` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_invite_user1_idx` (`user_id` ASC) ,
  INDEX `fk_event_invite_event1_idx` (`event_id` ASC) ,
  CONSTRAINT `fk_event_invite_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `partytime`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_invite_event1`
    FOREIGN KEY (`event_id`)
    REFERENCES `partytime`.`event` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

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

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `partytime`.* TO 'planner'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;