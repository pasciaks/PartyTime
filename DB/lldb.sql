-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema lldb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `lldb` ;

-- -----------------------------------------------------
-- Schema lldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `lldb` DEFAULT CHARACTER SET utf8 ;
USE `lldb` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `cohort` VARCHAR(255) NOT NULL,
  `enabled` TINYINT NOT NULL DEFAULT b'1',
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


-- -----------------------------------------------------
-- Table `question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question` ;

CREATE TABLE IF NOT EXISTS `question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `question` TEXT NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `enabled` TINYINT NULL DEFAULT b'1',
  `hint` TEXT NULL DEFAULT NULL,
  `explanation` TEXT NULL DEFAULT NULL,
  `user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_question_user_idx` (`user_id` ASC),
  CONSTRAINT `fk_question_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `choice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `choice` ;

CREATE TABLE IF NOT EXISTS `choice` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(255) NULL DEFAULT NULL,
  `position` INT(11) NULL DEFAULT '0',
  `correct` TINYINT NULL DEFAULT NULL,
  `explanation` VARCHAR(255) NULL DEFAULT NULL,
  `question_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_choice_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_choice_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz` ;

CREATE TABLE IF NOT EXISTS `quiz` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `enabled` TINYINT NULL DEFAULT NULL,
  `created_at` DATETIME NULL DEFAULT NULL,
  `updated_at` DATETIME NULL DEFAULT NULL,
  `instructor_user_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quiz_user1_idx` (`instructor_user_id` ASC),
  CONSTRAINT `fk_quiz_user1`
    FOREIGN KEY (`instructor_user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_question` ;

CREATE TABLE IF NOT EXISTS `quiz_question` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `quiz_id` INT(11) NOT NULL,
  `question_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_quiz_question_quiz1_idx` (`quiz_id` ASC),
  INDEX `fk_quiz_question_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_quiz_question_quiz1`
    FOREIGN KEY (`quiz_id`)
    REFERENCES `quiz` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_quiz_question_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `tag` ;

CREATE TABLE IF NOT EXISTS `tag` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `question_has_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question_has_tag` ;

CREATE TABLE IF NOT EXISTS `question_has_tag` (
  `question_id` INT(11) NOT NULL,
  `tag_id` INT(11) NOT NULL,
  PRIMARY KEY (`question_id`, `tag_id`),
  INDEX `fk_question_has_tag_tag1_idx` (`tag_id` ASC),
  INDEX `fk_question_has_tag_question1_idx` (`question_id` ASC),
  CONSTRAINT `fk_question_has_tag_question1`
    FOREIGN KEY (`question_id`)
    REFERENCES `question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_has_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `quiz_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `quiz_answer` ;

CREATE TABLE IF NOT EXISTS `quiz_answer` (
  `user_id` INT(11) NOT NULL,
  `quiz_question_id` INT(11) NOT NULL,
  `created_at` DATETIME NULL,
  `choice_id` INT(11) NOT NULL,
  PRIMARY KEY (`user_id`, `quiz_question_id`),
  INDEX `fk_user_has_quiz_question_quiz_question1_idx` (`quiz_question_id` ASC),
  INDEX `fk_user_has_quiz_question_user1_idx` (`user_id` ASC),
  INDEX `fk_quiz_guess_choice1_idx` (`choice_id` ASC),
  CONSTRAINT `fk_user_has_quiz_question_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_quiz_question_quiz_question1`
    FOREIGN KEY (`quiz_question_id`)
    REFERENCES `quiz_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_quiz_guess_choice1`
    FOREIGN KEY (`choice_id`)
    REFERENCES `choice` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

SET SQL_MODE = '';
DROP USER IF EXISTS instructor@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'instructor'@'localhost' IDENTIFIED BY 'instructor';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'instructor'@'localhost';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'instructor'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `user` (`id`, `username`, `password`, `cohort`, `enabled`, `created_at`, `role`, `first_name`, `last_name`, `updated_at`) VALUES (1, 'student', '$2a$10$Yr/bZ9GV8Hs5Q8Y5jSWzLer5D6FvM/ZT9CuN1O6qpFTtgbfIdo.Qe', 'C43', 1, NULL, 'student', 'student', 'student', NULL);
INSERT INTO `user` (`id`, `username`, `password`, `cohort`, `enabled`, `created_at`, `role`, `first_name`, `last_name`, `updated_at`) VALUES (2, 'instructor', '$2a$10$qhYu0ujqki.Q9set4PEIz.M8LGrgoDw5.CmT9ch0SQ3Stg2m9cQlC', 'C43', 1, NULL, 'instructor', 'instructor', 'instructor', NULL);
INSERT INTO `user` (`id`, `username`, `password`, `cohort`, `enabled`, `created_at`, `role`, `first_name`, `last_name`, `updated_at`) VALUES (3, 'admin', '$2a$10$CqI2A1ZumblrWLAzrYwKcuF1QUpBs290dNOBtioCBl1QbkAR.QVPW', 'C43', 1, NULL, 'admin', 'admin', 'admin', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `question`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `question` (`id`, `question`, `created_at`, `updated_at`, `enabled`, `hint`, `explanation`, `user_id`) VALUES (1, 'Question', NULL, NULL, 1, 'Hint', 'Explanation', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `choice`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `choice` (`id`, `content`, `position`, `correct`, `explanation`, `question_id`) VALUES (1, 'Choice', 1, 1, 'Explanation', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `quiz`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `quiz` (`id`, `title`, `enabled`, `created_at`, `updated_at`, `instructor_user_id`) VALUES (1, 'Quiz', 1, NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `quiz_question`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `quiz_question` (`id`, `quiz_id`, `question_id`) VALUES (1, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `tag`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `tag` (`id`, `title`) VALUES (1, 'jfop');

COMMIT;


-- -----------------------------------------------------
-- Data for table `question_has_tag`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `question_has_tag` (`question_id`, `tag_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `quiz_answer`
-- -----------------------------------------------------
START TRANSACTION;
USE `lldb`;
INSERT INTO `quiz_answer` (`user_id`, `quiz_question_id`, `created_at`, `choice_id`) VALUES (1, 1, NULL, 1);

COMMIT;

