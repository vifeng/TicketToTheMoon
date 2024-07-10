SET SCHEMA
    "PUBLIC";

INSERT INTO
    "VENUE"(
        "CITY",
        "COUNTRY",
        "STREET",
        "ZIPCODE",
        "NAME"
    )
VALUES
    (
        'Paris',
        'France',
        'sesame street',
        '75001',
        'Le Trianon'
    );

INSERT INTO
    "EMPLOYEE"(
        "EMAIL",
        "PASSWORD",
        "USERNAME",
        "VENUE_FK"
    )
VALUES
    ('email@admin.fr', 'Secret1*', 'username', 1);

INSERT INTO
    "HALL"(
        "CAPACITY_OF_HALL",
        "NAME",
        "VENUE_FK"
    )
VALUES
    (300, 'Hall 1', 1);

INSERT INTO
    "CONFIGURATION_HALL"(
        "CAPACITY_OF_CONFIGURATION",
        "NAME",
        "HALL_FK"
    )
VALUES
    (120, 'debout', 1);

INSERT INTO
    "EVENT"(
        "CLOSED_DAY",
        "DATE_END",
        "DATE_START",
        "DESCRIPTION",
        "NAME"
    )
VALUES
    (
        'Lundi',
        DATE '2024-02-25',
        DATE '2024-01-05',
        'concert rock super bien',
        'Concert'
    );

INSERT INTO
    "SESSION_EVENT"(
        "DATE_AND_TIME_END_SESSION_EVENT",
        "DATE_AND_TIME_START_SESSION_EVENT",
        "DURATION_IN_MINUTES",
        "CONFIGURATION_HALL_FK",
        "EVENT_FK"
    )
VALUES
    (
        NULL,
        TIMESTAMP '2026-01-05 20:00:00',
        90,
        1,
        1
    );

INSERT INTO
    "TARIFICATION"(
        "BASE_PRICE",
        "DISCOUNT_CHILD_RATE",
        "DISCOUNT_SENIOR_RATE",
        "DISCOUNT_STUDENT_RATE",
        "DISCOUNT_UNEMPLOYED_RATE",
        "TAXE_RATE",
        "EVENT_FK"
    )
VALUES
    (10.0, 0.2, 0.1, 0.5, 0.3, 0.2, 1);

INSERT INTO
    "CATEGORY_TARIFF"("NAME", "TARIFICATION_FK")
VALUES
    ('categorie 1', 1);

INSERT INTO
    "CATEGORY_SPATIAL"("NAME")
VALUES
    ('orchestre');

INSERT INTO
    "SEAT_STATUS"("NAME")
VALUES
    ('booked'),
    ('available'),
    ('sold'),
    ('unavailable');

INSERT INTO
    "SEAT"(
        "IS_SEATED",
        "ROW_NO",
        "SEAT_NO",
        "CATEGORY_SPATIAL_FK",
        "CATEGORY_TARIFF_FK",
        "CONFIGURATION_HALL_FK",
        "SEAT_STATUS_FK"
    )
VALUES
    (TRUE, 'A', 1, 1, 1, 1, 3),
    (TRUE, 'A', 2, 1, 1, 1, 3),
    (TRUE, 'B', 3, 1, 1, 1, 2),
    (TRUE, 'B', 4, 1, 1, 1, 2);

INSERT INTO
    "CUSTOMER"(
        "CITY",
        "COUNTRY",
        "STREET",
        "ZIPCODE",
        "CREDIT_CARD_NUMBER",
        "EMAIL",
        "FIRST_NAME",
        "LAST_NAME",
        "PHONE_NUMBER",
        "USERNAME"
    )
VALUES
    (
        'Paris',
        'France',
        'sesame street',
        '75001',
        '1234567891234567',
        'cookie@gmail.com',
        'Macaron',
        'Le glouton',
        '06 06 06 06 06',
        'cookie'
    );

INSERT INTO
    "PAYMENT_STATUS"("PAYMENT_STATUS_NAME")
VALUES
    ('paid');

INSERT INTO
    "BOOKING"(
        "BOOKING_CREATION_TIMESTAMP",
        "TOTAL_PRICE_HT",
        "CUSTOMER_FK"
    )
VALUES
    (TIMESTAMP '2024-03-11 18:28:39.943', 20.0, 1);

INSERT INTO
    "TICKET_RESERVATION"(
        "IS_BOOKED",
        "SESSION_EVENT_ID",
        "SEAT_ID",
        "BOOKING_FK"
    )
VALUES
    (TRUE, 1, 1, 1),
    (TRUE, 1, 2, 1);

INSERT INTO
    "PAYMENT"(
        "PAYMENT_DATE_TIME",
        "BOOKING_FK",
        "PAYMENT_STATUS_CATEGORY_FK"
    )
VALUES
    (TIMESTAMP '2024-03-11 18:28:39.95235', 1, 1);