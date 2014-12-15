--==============================================================
-- DB creation script for Apache Derby
--==============================================================

CONNECT 'jdbc:derby://localhost:1527/testDB;create=true;user=test;password=test';

-- these commands remove all tables from the database
-- it implies an error if tables not exist in DB, just ignore it
DROP TABLE orders_items;
DROP TABLE items;
DROP TABLE orders;
DROP TABLE users;
DROP TABLE roles;

----------------------------------------------------------------
-- ROLES
-- users roles
----------------------------------------------------------------
CREATE TABLE roles(
-- id has the INT type, it is the primary key
	id INT NOT NULL PRIMARY KEY,
-- name has the VARCHAR type - a string with a variable length
-- names values should not be repeated (UNIQUE) 	
	name VARCHAR(10) NOT NULL UNIQUE
);

-- this two commands insert data into roles table
INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'client');

----------------------------------------------------------------
-- USERS
----------------------------------------------------------------
CREATE TABLE users(
-- 'generated always AS identity' means id is autoincrement field 
-- (from 1 up to Integer.MAX_VALUE with the step 1)
	id INT NOT NULL generated always AS identity PRIMARY KEY,
-- 'UNIQUE' means logins values should not be repeated in login column of table	
	login VARCHAR(10) NOT NULL UNIQUE,
-- not null string columns	
	password VARCHAR(10) NOT NULL,
	name VARCHAR(20) NOT NULL,	
-- this declaration contains the foreign key constraint	
-- role_id in users table is associated with id in roles table
-- role_id of user = id of role
	role_id INT NOT NULL REFERENCES roles(id) 
-- removing a row with some ID from roles table implies removing 
-- all rows from users table for which ROLES_ID=ID
-- default value is ON DELETE RESTRICT, it means you cannot remove
-- row with some ID from the roles table if there are rows in 
-- users table with ROLES_ID=ID
		ON DELETE CASCADE 
-- the same as previous but updating is used insted deleting
		ON UPDATE RESTRICT
);

-- id = 1
INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan Ivanov', 0);
-- id = 2
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr Petrov', 1);

----------------------------------------------------------------
-- ORDERS
----------------------------------------------------------------
CREATE TABLE orders(
	id INT generated always AS identity PRIMARY KEY,
	user_id INT NOT NULL REFERENCES users(id) 
);

-- bill = 0; user_id=2; status_id=0
INSERT INTO orders VALUES(DEFAULT, 2);
-- bill = 0; user_id=2; status_id=3
INSERT INTO orders VALUES(DEFAULT, 2);

----------------------------------------------------------------
-- MENU ITEMS
----------------------------------------------------------------
CREATE TABLE items(
	id INT generated always AS identity PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	price INT NOT NULL 
);

-- горячие блюда
INSERT INTO items VALUES(DEFAULT, 'Borsch', 210); -- 1 (item id)
INSERT INTO items VALUES(DEFAULT, 'Kharcho', 210); -- 2
INSERT INTO items VALUES(DEFAULT, 'Solyanka', 250); -- 3
-- напитки
INSERT INTO items VALUES(DEFAULT, 'Juice', 70); -- 4
INSERT INTO items VALUES(DEFAULT, 'Tea', 50); -- 5
INSERT INTO items VALUES(DEFAULT, 'Coffee', 100); -- 6
-- закуски
INSERT INTO items VALUES(DEFAULT, 'Salmon salad', 250); -- 7
INSERT INTO items VALUES(DEFAULT, 'Fish plate', 200); -- 8
-- десерт        
INSERT INTO items VALUES(DEFAULT, 'Fruit plate', 160); -- 9
INSERT INTO items VALUES(DEFAULT, 'Strawberries and cream', 260); --10
                 
----------------------------------------------------------------
-- ORDERS_ITEMS
-- relation between order and menu items
-- each row of this table represents one menu item
----------------------------------------------------------------
CREATE TABLE orders_items(
	order_id INT NOT NULL REFERENCES orders(id),
	item_id INT NOT NULL REFERENCES items(id)
);

INSERT INTO orders_items VALUES(1, 1);
INSERT INTO orders_items VALUES(1, 7);
INSERT INTO orders_items VALUES(1, 5);

INSERT INTO orders_items VALUES(2, 1);
INSERT INTO orders_items VALUES(2, 7);
	
----------------------------------------------------------------
-- test database:
----------------------------------------------------------------
SELECT * FROM orders_items;
SELECT * FROM items;
SELECT * FROM orders;
SELECT * FROM users;
SELECT * FROM roles;