
--USERS table is created to store the details of all the users
DROP TABLE IF EXISTS USERS CASCADE;
CREATE TABLE IF NOT EXISTS USERS(id SERIAL, uuid VARCHAR(200) NOT NULL ,firstName VARCHAR(30) NOT NULL , lastName VARCHAR(30) NOT NULL ,userName VARCHAR(30) UNIQUE NOT NULL,  email VARCHAR(50) UNIQUE NOT NULL ,password VARCHAR(255) NOT NULL, salt VARCHAR(200) NOT NULL ,country VARCHAR(30) ,aboutMe VARCHAR(50),dob VARCHAR(30), role VARCHAR(30),contactNumber VARCHAR(30), PRIMARY KEY (id));
INSERT INTO users(
	id, uuid, firstname, lastname, username, email, password, salt, country, aboutme, dob, role, contactnumber)
	VALUES (1024,'rdtrdtdyt','Abhi','Mahajan','abhi','a@gmail.com','507FF5FED1CAC746','8Xt6jxoCI3MWsVaKY/1ySAp2qzlb2Z7P89+vDrb1o6U=', 'India' ,'I am @ UpGrad' ,'22-10-1995' , 'admin' , '1222333333' );

--USER_AUTH table is created to store the login information of all the users
DROP TABLE IF EXISTS USER_AUTH CASCADE;
CREATE TABLE IF NOT EXISTS USER_AUTH(
	ID BIGSERIAL PRIMARY KEY,
	uuid VARCHAR(200) NOT NULL,
	USER_ID INTEGER NOT NULL,
	ACCESS_TOKEN VARCHAR(500) NOT NULL,
	EXPIRES_AT TIMESTAMP NOT NULL,
	LOGIN_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	LOGOUT_AT TIMESTAMP NULL
);

ALTER TABLE USER_AUTH ADD CONSTRAINT FK_USER_AUTH_USER_ID FOREIGN KEY(USER_ID) REFERENCES USERS(ID) ON DELETE CASCADE ;

--PRODUCTS table is created to store the PRODUCTS related information stored in the warehouse in the Application
DROP TABLE IF EXISTS PRODUCTS CASCADE;
CREATE TABLE IF NOT EXISTS PRODUCTS(id SERIAL,uuid VARCHAR(200) NOT NULL, product_name VARCHAR(500) NOT NULL, date TIMESTAMP NOT NULL , user_id INTEGER NOT NULL, PRIMARY KEY(id), FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE);


--WAREHOUSE table is created to store the product and capacity related information while a product capacity is being set or receiving or dispatching product with mentioned capacity in the Application
DROP TABLE IF EXISTS WAREHOUSE CASCADE;
CREATE TABLE IF NOT EXISTS WAREHOUSE(id SERIAL,uuid VARCHAR(200) NOT NULL, product_capacity INTEGER NOT NULL,date TIMESTAMP NOT NULL , user_id INTEGER NOT NULL, product_id INTEGER NOT NULL , PRIMARY KEY(id), FOREIGN KEY (user_id) REFERENCES USERS(id) ON DELETE CASCADE, FOREIGN KEY (product_id) REFERENCES PRODUCTS(id) ON DELETE CASCADE);
