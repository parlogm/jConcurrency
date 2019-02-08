DROP SCHEMA IF EXISTS jc;

CREATE SCHEMA jc;
USE jc;

/* Table: user (Application Users) */
CREATE TABLE user (
    user_id     NVARCHAR(20) NOT NULL,
    password    VARCHAR (60) NOT NULL,
    first_name  NVARCHAR(50) ,
    last_name   NVARCHAR(50) ,
    email       NVARCHAR(70) ,
    security_provider_id INT ,
    default_customer_id  INT ,
    company     NVARCHAR(50) ,
    phone       NVARCHAR(20) ,
    address1    NVARCHAR(100),
    address2    NVARCHAR(100),
    country     NVARCHAR(20) ,
    postal      NVARCHAR(20) ,
    role        NVARCHAR(20) ,
    other_roles NVARCHAR(80) ,
    is_active   SMALLINT  ,
    is_blocked  SMALLINT  ,
    secret_question     NVARCHAR(100),
    secret_answer       NVARCHAR(100),
    enable_beta_testing SMALLINT ,
    enable_renewal      SMALLINT ,
    CONSTRAINT user_id PRIMARY KEY(user_id)
);

CREATE TABLE servers (
    id     int(11) NOT NULL AUTO_INCREMENT,
    name    NVARCHAR(20) NOT NULL,
    address  NVARCHAR(50) ,
    environment   NVARCHAR(50) ,
    available_flag    SMALLINT NOT NULL,
    CONSTRAINT id PRIMARY KEY(id)
);