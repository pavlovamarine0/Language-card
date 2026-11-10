CREATE TABLE USERS (
                       id SERIAL,
                       USERNAME VARCHAR(20) NOT NULL,
                       EMAIL VARCHAR(50) NOT NULL,
                       PASSWORD VARCHAR(20) NOT NULL,
                       CONSTRAINT pk_user_id PRIMARY KEY (id)
);