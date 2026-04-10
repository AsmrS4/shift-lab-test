CREATE TABLE IF NOT EXISTS sellers(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_info VARCHAR(128) NOT NULL,
    registration_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP WITH TIME ZONE DEFAULT NULL,
    is_active BOOLEAN DEFAULT true
);

CREATE TABLE IF NOT EXISTS transactions(
    id SERIAL PRIMARY KEY,
    amount BIGINT NOT NULL CHECK (amount > 0),
    payment_type VARCHAR(10) NOT NULL CHECK (payment_type IN ('CASH', 'CARD', 'TRANSFER')),
    transaction_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    seller_id INTEGER NOT NULL REFERENCES sellers(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sellers_audit(
    id SERIAL PRIMARY KEY,
    seller_id INTEGER NOT NULL REFERENCES sellers(id) ON DELETE CASCADE,

    prev_name VARCHAR(255),
    prev_contact_info VARCHAR(128),
    prev_status BOOLEAN,

    new_name VARCHAR(255),
    new_contact_info VARCHAR(128),
    new_status BOOLEAN,

    operation_type VARCHAR(10) CHECK (operation_type IN ('INSERT', 'UPDATE')),
    modified_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
