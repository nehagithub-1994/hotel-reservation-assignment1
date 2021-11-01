CREATE SCHEMA hotel_db AUTHORIZATION sa;

create table guest
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL
);
create table booking
(
    id             INT AUTO_INCREMENT PRIMARY KEY,
    booking_status VARCHAR(50) NOT NULL,
    suite_type     VARCHAR(50) NOT NULL,
    guest_id       INT(10)     NOT NULL,
    check_in_date  DATE        NOT NULL,
    check_out_date DATE        NOT NULL,
    no_of_people   INT(8)      NOT NULL,
    CONSTRAINT FK_GUEST_ID FOREIGN KEY (guest_id) REFERENCES guest (id)
);

