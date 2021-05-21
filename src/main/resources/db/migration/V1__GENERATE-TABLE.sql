CREATE TABLE products
(
    ID          BIGINT            NOT NULL AUTO_INCREMENT,
    NAME        varchar(50)    NOT NULL,
    DESCRIPTION varchar(200)   NOT NULL,
    PRICE       decimal(10, 2) NOT NULL,
    PRIMARY KEY (ID)
)engine=InnoDB default charset=utf8;