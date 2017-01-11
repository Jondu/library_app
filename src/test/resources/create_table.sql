CREATE TABLE raamatud(id bigserial primary key , title VARCHAR(200) UNIQUE, status boolean);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('endymion',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('olympos',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('hyperion',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('java8',true);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('sapiens',false);
INSERT INTO RAAMATUD (TITLE, STATUS) VALUES ('ilium',true);


CREATE TABLE people
(
   id SERIAL,
   name text,
   phone bigint
   book_id integer REFERENCES raamatud (id)
);