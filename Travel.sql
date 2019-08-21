CREATE DATABASE Travel;

USE Travel;

CREATE TABLE passengers(p_id INT PRIMARY KEY,
						f_name VARCHAR(30), 
                        l_name VARCHAR(30),
                        gender BIT DEFAULT 0,
                        birth_year YEAR);

CREATE TABLE airports	(a_id INT PRIMARY KEY,
						country VARCHAR(30),
						city VARCHAR(30));

CREATE TABLE planes		(tail_no INT PRIMARY KEY,
						make INT,
						model VARCHAR(30), 
						capacity INT,
						mph INT);

CREATE TABLE flights	(flight_no INT PRIMARY KEY,
						dep_loc INT,
						dep_date DATE,
						dep_time TIME,
						arr_loc INT,
                        FOREIGN  KEY (arr_loc) REFERENCES airports(a_id),
						arr_time TIME,
						arr_date DATE, 
						tail_no INT,
						FOREIGN  KEY (tail_no) REFERENCES planes(tail_no));

CREATE TABLE onboard	(p_id INT,
						FOREIGN  KEY (p_id) REFERENCES passengers(p_id),
						flight_no INT,
						FOREIGN  KEY (flight_no) REFERENCES flights(flight_no),
						seat INT UNIQUE);  