CREATE USER 'chat'@'%' IDENTIFIED WITH caching_sha2_password BY '***';GRANT USAGE ON *.* TO 'chat'@'%';
ALTER USER 'chat'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
CREATE DATABASE IF NOT EXISTS `chat`;
GRANT ALL PRIVILEGES ON `chat`.* TO 'chat'@'%';

CREATE TABLE `chat`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `login` VARCHAR(50) NOT NULL ,
    `password` VARCHAR(128) NOT NULL ,
    `creationDate` VARCHAR(50) NOT NULL ,
    `status` VARCHAR(50) NOT NULL ,
    `lastConnection` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE `LOGIN` (`login`(50))
) ENGINE = InnoDB;

CREATE TABLE `chat`.`conversations` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `admin` VARCHAR(50) NOT NULL ,
    `creationDate` VARCHAR(50) NOT NULL ,
    `label` VARCHAR(50) NOT NULL ,
    `type` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `chat`.`messages` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `conversation` INT NOT NULL ,
    `sender` INT NOT NULL ,
    `dateSent` VARCHAR(50) NOT NULL ,
    `content` VARCHAR(10000) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `chat`.`participants` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `conversation` INT NOT NULL ,
    `user` INT NOT NULL ,
    `addDate` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;
