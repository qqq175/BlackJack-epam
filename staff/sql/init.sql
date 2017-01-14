-- MySQL Script generated by MySQL Workbench
-- Fri 13 Jan 2017 01:24:20 AM +03
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema black_jack
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `black_jack` ;

-- -----------------------------------------------------
-- Schema black_jack
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `black_jack` DEFAULT CHARACTER SET utf8 ;
USE `black_jack` ;

-- -----------------------------------------------------
-- Table `black_jack`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `black_jack`.`user` ;

CREATE TABLE IF NOT EXISTS `black_jack`.`user` (
  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` CHAR(64) NOT NULL,
  `first_name` VARCHAR(127) NOT NULL,
  `last_name` VARCHAR(127) NOT NULL,
  `display_name` VARCHAR(255) NOT NULL COMMENT 'Default FirstName + LastName',
  `rating` DOUBLE NOT NULL DEFAULT 0.0,
  `account_balance` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `locked_balance` DECIMAL(12,2) NOT NULL DEFAULT 0.0,
  `type` ENUM('admin', 'player') NOT NULL COMMENT 'user type - admin, player',
  `registred` DATETIME NOT NULL DEFAULT NOW() COMMENT 'NULL means that email is not confirmed.',
  `is_active` TINYINT(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;

CREATE UNIQUE INDEX `EMAIL` ON `black_jack`.`user` (`email` ASC);


-- -----------------------------------------------------
-- Table `black_jack`.`userstat`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `black_jack`.`userstat` ;

CREATE TABLE IF NOT EXISTS `black_jack`.`userstat` (
  `user_id` BIGINT NOT NULL,
  `win` INT NOT NULL DEFAULT 0,
  `blackjack` INT NOT NULL DEFAULT 0,
  `loss` INT NOT NULL DEFAULT 0,
  `tie` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_userstat_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `black_jack`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_userstat_user1_idx` ON `black_jack`.`userstat` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `black_jack`.`game`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `black_jack`.`game` ;

CREATE TABLE IF NOT EXISTS `black_jack`.`game` (
  `game_id` BIGINT NOT NULL AUTO_INCREMENT,
  `creation_time` DATETIME NOT NULL DEFAULT NOW(),
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`game_id`),
  CONSTRAINT `fk_game_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `black_jack`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_game_user1_idx` ON `black_jack`.`game` (`user_id` ASC);


-- -----------------------------------------------------
-- Table `black_jack`.`message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `black_jack`.`message` ;

CREATE TABLE IF NOT EXISTS `black_jack`.`message` (
  `message_id` BIGINT NOT NULL AUTO_INCREMENT,
  `game_id` BIGINT NOT NULL,
  `user_id` BIGINT NULL COMMENT 'null for system messages',
  `text` VARCHAR(4096) NOT NULL,
  `time` DATETIME NOT NULL,
  PRIMARY KEY (`message_id`),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `black_jack`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_game1`
    FOREIGN KEY (`game_id`)
    REFERENCES `black_jack`.`game` (`game_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_message_user1_idx` ON `black_jack`.`message` (`user_id` ASC);

CREATE INDEX `fk_message_game1_idx` ON `black_jack`.`message` (`game_id` ASC);


-- -----------------------------------------------------
-- Table `black_jack`.`account_operation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `black_jack`.`account_operation` ;

CREATE TABLE IF NOT EXISTS `black_jack`.`account_operation` (
  `account_operation_id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT NOT NULL,
  `ammount` DECIMAL(12,2) NOT NULL,
  `type` ENUM('payment', 'withdrawal', 'win', 'loss') NOT NULL,
  `time` DATETIME NOT NULL,
  `comment` VARCHAR(255) NULL,
  PRIMARY KEY (`account_operation_id`),
  CONSTRAINT `fk_account_operation_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `black_jack`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE INDEX `fk_account_operation_user1_idx` ON `black_jack`.`account_operation` (`user_id` ASC);

USE `black_jack`;

DELIMITER $$

USE `black_jack`$$
DROP TRIGGER IF EXISTS `black_jack`.`userstat_AFTER_UPDATE` $$
USE `black_jack`$$
CREATE DEFINER = CURRENT_USER TRIGGER `black_jack`.`userstat_AFTER_UPDATE` AFTER UPDATE ON `userstat` FOR EACH ROW
BEGIN
	SET @rating =((NEW.win + NEW.blackjack + NEW.tie*0.5) / (NEW.win+NEW.tie*2+NEW.loss*3+1)*(LOG2(NEW.win + NEW.blackjack + 1)));
	UPDATE user
	SET rating = @rating
	WHERE user_id = NEW.user_id;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
