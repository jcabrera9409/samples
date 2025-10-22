CREATE TABLE IF NOT EXISTS exchange_data (
    id serial PRIMARY KEY,
    from_currency VARCHAR (15) NOT NULL,
    to_currency VARCHAR (15) NOT NULL,
    exchange_rate FLOAT NOT NULL
);

INSERT INTO exchange_data (from_currency, to_currency, exchange_rate)
VALUES ('PEN', 'USD', 3.75);