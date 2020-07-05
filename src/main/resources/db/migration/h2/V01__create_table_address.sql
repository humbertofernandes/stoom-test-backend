CREATE SEQUENCE IF NOT EXISTS endereco_id_seq START WITH 1 INCREMENT BY 1;
CREATE TABLE `endereco`
(
    `id`           BIGINT(20) default endereco_id_seq.nextval primary key,
    `street_name`   VARCHAR(45) NOT NULL,
    `number`        VARCHAR(45) NOT NULL,
    `complement`    VARCHAR(45) NULL,
    `neighbourhood` VARCHAR(45) NOT NULL,
    `city`          VARCHAR(45) NOT NULL,
    `state`         VARCHAR(45) NOT NULL,
    `country`       VARCHAR(45) NOT NULL,
    `zipcode`       VARCHAR(45) NOT NULL,
    `latitude`      VARCHAR(45) NULL,
    `longitude`     VARCHAR(45) NULL,
    PRIMARY KEY (`id`)
);
