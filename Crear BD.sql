-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS mydb DEFAULT CHARACTER SET utf8 ;
USE mydb;

-- -----------------------------------------------------
-- Table mydb.role
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.role ;

CREATE TABLE IF NOT EXISTS mydb.role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NULL
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.user
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.user ;

CREATE TABLE IF NOT EXISTS mydb.user (
  id INT PRIMARY KEY AUTO_INCREMENT,
  password VARCHAR(45) NOT NULL,
  name VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  role_id INT NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.profile
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.profile ;

CREATE TABLE IF NOT EXISTS mydb.profile (
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  picture VARCHAR(454) NULL,
  cellphone VARCHAR(45) NULL,
  date_of_birth DATE NULL,
  user_id INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table mydb.password_reset_token
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.password_reset_token;

CREATE TABLE IF NOT EXISTS mydb.password_reset_token (
  id INT PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(45) NULL,
  expiration_date DATE NULL,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.course
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.course ;

CREATE TABLE IF NOT EXISTS mydb.course (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(400) NULL,
  description VARCHAR(445) NULL,
  status BOOLEAN NULL,
  price FLOAT NULL
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.user_course
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.user_course;

CREATE TABLE IF NOT EXISTS mydb.user_course (
  course_id INT,
  user_id INT,
  payment_method VARCHAR(45) NULL,
  amount_paid FLOAT NULL,
  purchase_date DATE NULL,
  PRIMARY KEY (course_id, user_id),
  FOREIGN KEY (course_id) REFERENCES course(id),
  FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.module
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.module ;

CREATE TABLE IF NOT EXISTS mydb.module (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  course_id INT,
  FOREIGN KEY (course_id) REFERENCES course(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.video
-- -----------------------------------------------------
DROP TABLE IF EXISTS mydb.video ;

CREATE TABLE IF NOT EXISTS mydb.video (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  url VARCHAR(45) NOT NULL,
  description VARCHAR(445) NULL,
  module_id INT,
  FOREIGN KEY (module_id) REFERENCES module(id)
)ENGINE = InnoDB;