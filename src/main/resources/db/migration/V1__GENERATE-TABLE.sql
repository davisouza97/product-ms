CREATE TABLE products
(
    ID          INT            NOT NULL AUTO_INCREMENT,
    NAME        varchar(50)    NOT NULL,
    DESCRIPTION varchar(200)   NOT NULL,
    PRICE       decimal(10, 2) NOT NULL,
    PRIMARY KEY (ID)
);