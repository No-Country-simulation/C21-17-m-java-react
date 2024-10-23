-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `elearning_courses_db` DEFAULT CHARACTER SET utf8 ;
USE `elearning_courses_db`;

CREATE SCHEMA IF NOT EXISTS `elearning_users_db` DEFAULT CHARACTER SET utf8 ;
USE `elearning_users_db`;

CREATE SCHEMA IF NOT EXISTS `elearning_payments_db` DEFAULT CHARACTER SET utf8 ;
USE `elearning_payments_db`;


-- -----------------------------------------------------
-- Table mydb.role
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_users_db.role ;

CREATE TABLE IF NOT EXISTS elearning_users_db.role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(45) NULL
) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.user
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_users_db.user ;

CREATE TABLE IF NOT EXISTS elearning_users_db.user (
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
DROP TABLE IF EXISTS elearning_users_db.profile ;

CREATE TABLE IF NOT EXISTS elearning_users_db.profile (
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
DROP TABLE IF EXISTS elearning_users_db.password_reset_token;

CREATE TABLE IF NOT EXISTS elearning_users_db.password_reset_token (
  id INT PRIMARY KEY AUTO_INCREMENT,
  token VARCHAR(45) NULL,
  expiration_date DATE NULL,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES user(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.course
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_courses_db.course ;

CREATE TABLE IF NOT EXISTS elearning_courses_db.course (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(400) NULL,
  description VARCHAR(445) NULL,
  status BOOLEAN NULL,
  price FLOAT NULL
)ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table mydb.module
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_courses_db.module ;

CREATE TABLE IF NOT EXISTS elearning_courses_db.module (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  description VARCHAR(45) NULL,
  course_id INT,
  FOREIGN KEY (course_id) REFERENCES course(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.video
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_courses_db.video ;

CREATE TABLE IF NOT EXISTS elearning_courses_db.video (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(45) NULL,
  url VARCHAR(45) NOT NULL,
  description VARCHAR(445) NULL,
  module_id INT,
  FOREIGN KEY (module_id) REFERENCES module(id)
)ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table mydb.user_course
-- -----------------------------------------------------
DROP TABLE IF EXISTS elearning_payments_db.user_course;

CREATE TABLE IF NOT EXISTS elearning_payments_db.user_course (
  course_id INT NOT NULL,
  user_id INT NOT NULL,
  payment_method VARCHAR(45) NULL,
  amount_paid FLOAT NOT NULL,
  purchase_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (course_id, user_id)  -- Combinación única de course_id y user_id
) ENGINE = InnoDB;

