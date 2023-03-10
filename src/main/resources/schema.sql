CREATE TABLE IF NOT EXISTS contracts(
    id VARCHAR PRIMARY KEY,
    create_date DATE NOT NULL, -- дата заключения контракта
    duration SMALLINT NOT NULL CONSTRAINT must_be_positive CHECK ( duration >= 0 ), -- длительность контракта(кол-во лет)
    is_finished BOOLEAN DEFAULT FALSE,  -- расторгнут ли контракт
    finish_date DATE NULL -- дата расторжения контракта
        CONSTRAINT must_be_bigger_then_create_date CHECK ( finish_date > contracts.create_date )
);

CREATE TABLE IF NOT EXISTS writers(
    id VARCHAR PRIMARY KEY,
    contact_id VARCHAR REFERENCES contracts(id) ON DELETE CASCADE ,

    /* Паспортные данные*/
    password_series SMALLINT NOT NULL,
    password_id INTEGER NOT NULL, -- номер паспорта
    surname VARCHAR NOT NULL,
    "name" VARCHAR NOT NULL,
    patronymic VARCHAR NOT NULL,

    address VARCHAR NULL,
    phone_number VARCHAR NULL,

    UNIQUE (password_id, password_series)
);

CREATE TABLE IF NOT EXISTS books(
    id VARCHAR PRIMARY KEY,
    ISBN VARCHAR NOT NULL CONSTRAINT isbn_must_be_unique UNIQUE, -- International Standard Book Number
    title VARCHAR NOT NULL,
    circulation INTEGER CHECK ( circulation >= 0 ) , -- тираж
    issue_date DATE NULL, -- дата выпуска
    cost_price INTEGER NULL CHECK ( cost_price >= 0 ) , -- себестоимость одной книги
    selling_price INTEGER NULL CHECK ( selling_price >= 0) , -- выручка с одной проданной книги
    absolute_fee INTEGER NULL CHECK ( absolute_fee >= 0 ) -- общая сумма гонорара, выплачиваемая авторам
);

CREATE TABLE IF NOT EXISTS writers_books(
    writer_id VARCHAR REFERENCES writers(id) ON DELETE CASCADE,
    book_id VARCHAR REFERENCES books(id) ON DELETE CASCADE,
    PRIMARY KEY (writer_id, book_id)
);

CREATE TABLE IF NOT EXISTS customers(
    id VARCHAR PRIMARY KEY,
    company VARCHAR NOT NULL,
    company_address VARCHAR NOT NULL,
    phone_number VARCHAR NOT NULL,
    surname VARCHAR NOT NULL,
    "name" VARCHAR NOT NULL,
    patronymic VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS orders(
    id VARCHAR PRIMARY KEY,
    book_id VARCHAR REFERENCES books(id),
    customer_id VARCHAR REFERENCES customers(id),
    create_date DATE NOT NULL,
    finish_date DATE NULL CONSTRAINT must_be_bigger_then_create_date CHECK ( finish_date > orders.create_date ),
    books_count INTEGER NOT NULL CONSTRAINT must_be_positive CHECK ( books_count > 0 )
);