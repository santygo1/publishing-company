/* TODO:удалить когда деплой */
DROP TABLE contracts CASCADE;
DROP TABLE writers CASCADE;
DROP TABLE books CASCADE;
DROP TABLE  writers_books CASCADE;
DROP TABLE orders CASCADE;
DROP TABLE customers CASCADE;

CREATE TABLE IF NOT EXISTS writers(
    id VARCHAR PRIMARY KEY,

    /* Паспортные данные*/
    passport_series VARCHAR(4) NOT NULL,
    passport_id VARCHAR(6) NOT NULL, -- номер паспорта
    surname VARCHAR(64) NOT NULL,
    "name" VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64) NULL,


    address VARCHAR(128) NOT NULL,
    phone_number VARCHAR(18) NOT NULL,

    UNIQUE (passport_id, passport_series)
);
CREATE TABLE IF NOT EXISTS contracts(
    id VARCHAR PRIMARY KEY,
    writer_id VARCHAR REFERENCES writers(id) ON DELETE SET NULL ON UPDATE CASCADE,
    contract_number VARCHAR(64) UNIQUE NOT NULL,
    create_date DATE NOT NULL, -- дата заключения контракта
    duration SMALLINT NOT NULL CONSTRAINT must_be_positive_or_zero CHECK ( duration >= 0 ), -- длительность контракта(кол-во лет)
    is_finished BOOLEAN DEFAULT FALSE,  -- расторгнут ли контракт
    finish_date DATE NULL -- дата расторжения контракта
    CONSTRAINT must_be_bigger_then_create_date CHECK ( finish_date > contracts.create_date )
);

CREATE TABLE IF NOT EXISTS books(
    id VARCHAR PRIMARY KEY,
    ISBN VARCHAR(17) NOT NULL CONSTRAINT isbn_must_be_unique UNIQUE, -- International Standard Book Number
    title VARCHAR(128) NOT NULL,
    circulation INTEGER CHECK ( circulation >= 0 ) , -- тираж
    issue_date DATE NOT NULL, -- дата выпуска
    cost_price INTEGER NULL CHECK ( cost_price >= 0 ) , -- себестоимость одной книги
    selling_price INTEGER NULL CHECK ( selling_price >= 0) , -- выручка с одной проданной книги
    absolute_fee INTEGER NULL CHECK ( absolute_fee >= 0 ) -- общая сумма гонорара, выплачиваемая авторам
);

CREATE TABLE IF NOT EXISTS writers_books(
    writer_id VARCHAR REFERENCES writers(id) ON DELETE CASCADE ON UPDATE CASCADE,
    book_id VARCHAR REFERENCES books(id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (writer_id, book_id)
);

CREATE TABLE IF NOT EXISTS customers(
    id VARCHAR PRIMARY KEY,
    company VARCHAR(64) NOT NULL,
    company_address VARCHAR(128) NOT NULL,
    phone_number VARCHAR(18) NOT NULL,
    surname VARCHAR(64) NOT NULL,
    "name" VARCHAR(64) NOT NULL,
    patronymic VARCHAR(64) NULL
);

CREATE TABLE IF NOT EXISTS orders(
    id VARCHAR PRIMARY KEY,
    book_id VARCHAR REFERENCES books(id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    customer_id VARCHAR REFERENCES customers(id) ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    order_number VARCHAR UNIQUE NOT NULL,
    create_date DATE NOT NULL,
    finish_date DATE NULL CONSTRAINT must_be_bigger_or_equals_then_create_date CHECK ( finish_date >= orders.create_date ),
    books_count INTEGER NOT NULL CONSTRAINT must_be_positive CHECK ( books_count > 0 )
);