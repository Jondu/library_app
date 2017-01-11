CREATE TABLE raamatud(id bigserial primary key , title VARCHAR(200) UNIQUE, status boolean);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('endymion',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('olympos',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('hyperion',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('java8',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('sapiens',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('ilium',true);


CREATE TABLE people(id SERIAL, name text, phone bigint, book_id integer REFERENCES raamatud (id));

INSERT INTO people (name, phone) VALUES ('Toomas',5654343);
INSERT INTO people (name, phone) VALUES ('Marju',342452);
INSERT INTO people (name, phone) VALUES ('Kristi',43636634);
INSERT INTO people (name, phone) VALUES ('Raivo',879878);