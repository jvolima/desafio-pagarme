CREATE TABLE payables
(
    id               UUID             NOT NULL,
    status           SMALLINT         NOT NULL,
    payment_date     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    discounted_value DOUBLE PRECISION NOT NULL,
    transaction_id   UUID,
    CONSTRAINT pk_payables PRIMARY KEY (id)
);

CREATE TABLE transactions
(
    id                   UUID             NOT NULL,
    value                DOUBLE PRECISION NOT NULL,
    description          VARCHAR(255)     NOT NULL,
    payment_method       SMALLINT         NOT NULL,
    card_number          VARCHAR(255)     NOT NULL,
    cardholder_name      VARCHAR(255)     NOT NULL,
    card_expiration_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    cvv                  VARCHAR(255)     NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_transactions PRIMARY KEY (id)
);

ALTER TABLE payables
    ADD CONSTRAINT FK_PAYABLES_ON_TRANSACTION FOREIGN KEY (transaction_id) REFERENCES transactions (id);