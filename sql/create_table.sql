START TRANSACTION;

DROP TABLE IF EXISTS bitcoin_heist;

CREATE TABLE bitcoin_heist (
    address VARCHAR(64) NOT NULL,
    year INT NOT NULL,
    day INT NOT NULL,
    length INT NOT NULL,
    weight FLOAT NOT NULL,
    count INT NOT NULL,
    looped INT NOT NULL,
    neighbors INT NOT NULL,
    income BIGINT NOT NULL,
    label VARCHAR(64) NOT NULL,
    PRIMARY KEY (address, year, day)
);

COMMIT;