DROP TABLE IF EXISTS user_info;

CREATE TABLE user_info(
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
    authority VARCHAR(20) NOT NULL,
    PRIMARY KEY(id)
);

