DROP DATABASE IF EXISTS auction;
CREATE DATABASE auction;
USE auction;

CREATE TABLE `user` (
   id INT NOT NULL AUTO_INCREMENT,
   firstname VARCHAR(50) NOT NULL,
   lastname VARCHAR(50) NOT NULL,
  `login` VARCHAR(50) NOT NULL,
  `password` VARCHAR(50) NOT NULL,
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE seller (
  userid INT NOT NULL,
  PRIMARY KEY (userid),
  FOREIGN KEY (userid) REFERENCES `user` (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE buyer (
  userid INT NOT NULL,
  PRIMARY KEY (userid),
  FOREIGN KEY (userid) REFERENCES `user` (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE lot (
  id INT NOT NULL AUTO_INCREMENT,
  idSeller INT NOT NULL,
  `name` VARCHAR(50) NOT NULL,
  `description` VARCHAR(50) NOT NULL,
  minValueForSell INT NOT NULL,
  obligatoryValueForSell INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (idSeller) REFERENCES seller (userid) ON DELETE CASCADE,
  KEY `name` (`name`),
  KEY `description` (`description`)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE category (
  id INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE price (
  Bid INT NOT NULL AUTO_INCREMENT,
  `value` INT NOT NULL,
  PRIMARY KEY (Bid)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE buyer_lot (
  id INT NOT NULL AUTO_INCREMENT,
  buyerid INT(11) NOT NULL,
  lotid INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY buyer_lot (buyerid, lotid),
  KEY lotid (lotid),
  FOREIGN KEY (lotid) REFERENCES lot (id) ON DELETE CASCADE,
  FOREIGN KEY (buyerid) REFERENCES buyer (userid) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE seller_lot (
  id INT NOT NULL AUTO_INCREMENT,
  sellerid INT(11) NOT NULL,
  lotid INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY buyer_lot (sellerid, lotid),
  KEY lotid (lotid),
  FOREIGN KEY (lotid) REFERENCES lot (id) ON DELETE CASCADE,
  FOREIGN KEY (sellerid) REFERENCES seller (userid) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE category_lot (
  id INT NOT NULL AUTO_INCREMENT,
  categoryid INT(11) NOT NULL,
  lotid INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY category_lot (categoryid, lotid),
  KEY lotid (lotid),
  FOREIGN KEY (lotid) REFERENCES lot (id) ON DELETE CASCADE,
  FOREIGN KEY (categoryid) REFERENCES category (id) ON DELETE CASCADE
)
CHARACTER SET utf8
COLLATE utf8_general_ci;

CREATE TABLE session (
  id INT NOT NULL AUTO_INCREMENT,
  uuid VARCHAR(36),
  PRIMARY KEY (id)
)
CHARACTER SET utf8
COLLATE utf8_general_ci;