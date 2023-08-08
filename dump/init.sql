CREATE USER 'chat'@'%' IDENTIFIED WITH caching_sha2_password BY '***';GRANT USAGE ON *.* TO 'chat'@'%';
ALTER USER 'chat'@'%' REQUIRE NONE WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0;
CREATE DATABASE IF NOT EXISTS `chat`;
GRANT ALL PRIVILEGES ON `chat`.* TO 'chat'@'%';

CREATE TABLE `chat`.`users` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `username` VARCHAR(50) NOT NULL ,
    `password` VARCHAR(200) NOT NULL ,
    `creationDate` VARCHAR(50) NOT NULL ,
    `status` VARCHAR(50) NOT NULL ,
    `lastConnection` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE `USERNAME` (`username`(50))
) ENGINE = InnoDB;

CREATE TABLE `chat`.`conversations` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `prime` INT NOT NULL ,
    `creationDate` VARCHAR(50) NOT NULL ,
    `label` VARCHAR(50) NOT NULL ,
    `type` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `chat`.`messages` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `conversation` INT NOT NULL ,
    `sender` INT NOT NULL ,
    `date` VARCHAR(50) NOT NULL ,
    `modification` VARCHAR(50),
    `content` VARCHAR(10000) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `chat`.`participations` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `conversation` INT NOT NULL ,
    `user` INT NOT NULL ,
    `addDate` VARCHAR(50) NOT NULL ,
    `role` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;

CREATE TABLE `chat`.`usernames` (
    `id` INT NOT NULL AUTO_INCREMENT ,
    `username` VARCHAR(50) NOT NULL ,
    PRIMARY KEY (`id`),
    UNIQUE `username` (`username`(50))
) ENGINE = InnoDB;

INSERT INTO usernames (username) VALUES
 ('abuse'),
 ('account'),
 ('admin'),
 ('anonymous'),
 ('away'),
 ('block'),
 ('bot'),
 ('busy'),
 ('chat'),
 ('chatbot'),
 ('contact'),
 ('conversation'),
 ('customer'),
 ('error'),
 ('faq'),
 ('feedback'),
 ('friend'),
 ('friendrequest'),
 ('group'),
 ('guest'),
 ('help'),
 ('helpdesk'),
 ('info'),
 ('invite'),
 ('login'),
 ('logout'),
 ('marketing'),
 ('member'),
 ('message'),
 ('moderator'),
 ('news'),
 ('newsletter'),
 ('noreply'),
 ('notification'),
 ('offline'),
 ('online'),
 ('password'),
 ('pending'),
 ('policy'),
 ('presence'),
 ('privacy'),
 ('profile'),
 ('readreceipt'),
 ('register'),
 ('report'),
 ('reportcontent'),
 ('reportuser'),
 ('request'),
 ('root'),
 ('sales'),
 ('security'),
 ('settings'),
 ('signup'),
 ('status'),
 ('support'),
 ('system'),
 ('team'),
 ('terms'),
 ('test'),
 ('typingindicator'),
 ('update'),
 ('user'),
 ('webmaster');
