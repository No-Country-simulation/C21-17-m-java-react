

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';


-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`User_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User_profile` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User_profile` (
  `user Id` INT NOT NULL,
  `profile_picture` VARCHAR(454) NULL,
  `cellphone` VARCHAR(45) NULL,
  `date of birth` DATE NULL,
  PRIMARY KEY (`user Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Role` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Role` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`, `user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`password_reset_token`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`password_reset_token` ;

CREATE TABLE IF NOT EXISTS `mydb`.`password_reset_token` (
  `id` INT NOT NULL,
  `token` VARCHAR(45) NULL,
  `expiration_date` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `id` INT NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `User_profile_user Id` INT NOT NULL,
  `Role_id` INT NOT NULL,
  `Role_user_id` INT NOT NULL,
  `password_reset_token_id` INT NOT NULL,
  PRIMARY KEY (`id`, `User_profile_user Id`, `Role_id`, `Role_user_id`, `password_reset_token_id`),
  INDEX `fk_User_User_profile1_idx` (`User_profile_user Id` ASC),
  INDEX `fk_User_Role1_idx` (`Role_id` ASC, `Role_user_id` ASC) ,
  INDEX `fk_User_password_reset_token1_idx` (`password_reset_token_id` ASC),
  CONSTRAINT `fk_User_User_profile1`
    FOREIGN KEY (`User_profile_user Id`)
    REFERENCES `mydb`.`User_profile` (`user Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_Role1`
    FOREIGN KEY (`Role_id` , `Role_user_id`)
    REFERENCES `mydb`.`Role` (`id` , `user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_password_reset_token1`
    FOREIGN KEY (`password_reset_token_id`)
    REFERENCES `mydb`.`password_reset_token` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`User_Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`User_Course` ;

CREATE TABLE IF NOT EXISTS `mydb`.`User_Course` (
  `idCourse` INT NOT NULL,
  `payment_method` VARCHAR(45) NULL,
  `amount_paid` FLOAT NULL,
  `purchase_date` DATE NULL,
  `User_id` INT NOT NULL,
  `User_User_profile_user Id` INT NOT NULL,
  `User_Role_id` INT NOT NULL,
  `User_Role_user_id` INT NOT NULL,
  `User_password_reset_token_id` INT NOT NULL,
  PRIMARY KEY (`idCourse`, `User_id`, `User_User_profile_user Id`, `User_Role_id`, `User_Role_user_id`, `User_password_reset_token_id`),
  INDEX `fk_User_Course_User1_idx` (`User_id` ASC, `User_User_profile_user Id` ASC, `User_Role_id` ASC, `User_Role_user_id` ASC, `User_password_reset_token_id` ASC) ,
  CONSTRAINT `fk_User_Course_User1`
    FOREIGN KEY (`User_id` , `User_User_profile_user Id` , `User_Role_id` , `User_Role_user_id` , `User_password_reset_token_id`)
    REFERENCES `mydb`.`User` (`id` , `User_profile_user Id` , `Role_id` , `Role_user_id` , `password_reset_token_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Video`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Video` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Video` (
  `idVideo` INT NOT NULL,
  `module_ID` INT NULL,
  `tittle` VARCHAR(45) NULL,
  `url` VARCHAR(45) NOT NULL,
  `description` VARCHAR(445) NULL,
  PRIMARY KEY (`idVideo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Module`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Module` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Module` (
  `idModule` INT NOT NULL,
  `course_id` INT NULL,
  `title` VARCHAR(45) NULL,
  `description` VARCHAR(45) NULL,
  `Video_idVideo` INT NOT NULL,
  PRIMARY KEY (`idModule`, `Video_idVideo`),
  INDEX `fk_Module_Video1_idx` (`Video_idVideo` ASC),
  CONSTRAINT `fk_Module_Video1`
    FOREIGN KEY (`Video_idVideo`)
    REFERENCES `mydb`.`Video` (`idVideo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Course` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Course` (
  `idC` INT NOT NULL,
  `ttitle` VARCHAR(400) NULL,
  `description` VARCHAR(445) NULL,
  `status` TINYINT NULL,
  `price` FLOAT NULL,
  `User_Course_idCourse` INT NOT NULL,
  `Module_idModule` INT NOT NULL,
  PRIMARY KEY (`idC`, `User_Course_idCourse`, `Module_idModule`),
  INDEX `fk_Course_User_Course1_idx` (`User_Course_idCourse` ASC) ,
  INDEX `fk_Course_Module1_idx` (`Module_idModule` ASC) ,
  CONSTRAINT `fk_Course_User_Course1`
    FOREIGN KEY (`User_Course_idCourse`)
    REFERENCES `mydb`.`User_Course` (`idCourse`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Course_Module1`
    FOREIGN KEY (`Module_idModule`)
    REFERENCES `mydb`.`Module` (`idModule`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
