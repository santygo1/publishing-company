INSERT INTO writers(id, passport_series, passport_id, surname, name, patronymic, address, phone_number)
VALUES
    ('first-1', 5000, 445321, 'Isakov', 'Tikhon', 'Alekseevich', 'Pogranich','+7924'),
    ('second-20', 5000, 445422, 'Isakov', 'Tikhon', 'Alekseevich', 'Pogranich','+7924');

INSERT INTO contracts(id,writer_id, contract_number,create_date, duration, is_finished, finish_date)
VALUES
    ('123','first-1','№123', '2002-02-10', 1, FALSE, '2002-02-20'),
    ('143','second-20','№124', '2022-02-10', 10, FALSE, '2022-10-20'),
    ('193', NULL ,'№126', '2002-02-10', 1, TRUE, '2002-02-20');

INSERT INTO books(id, isbn, title, circulation, issue_date, cost_price, selling_price, absolute_fee)
VALUES
    ('222', '222-3212-2123', 'book2', 21, '2002-02-20', 200, 400, 500),
    ('111', '111-99-2123', 'book1', 21, '2002-02-20', 200, 400, 500),
    ('333', '222-77-2123', 'book2', 21, '2002-02-20', 200, 400, 500),
    ('444', '222-12-2123', 'book2', 21, '2002-02-20', 200, 400, 500),
    ('555', '222-3-2123', 'book2', 21, '2002-02-20', 200, 400, 500),
    ('666', '3-3212-2123', 'book2', 21, '2002-02-20', 200, 400, 500),
    ('777', '12-3212-2123', 'book2', 21, '2002-02-20', 200, 400, 500);