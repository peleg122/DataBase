use travel;

INSERT INTO airports(a_id,country, city) VALUES 			(1,"ISR","TLV"),
									(2,"USA","CALI"),
									(3,"RUS","MOSC"),
									(4,"ISR","UVDA"),
									(5,"USA","LASVEG");
                                           
INSERT INTO passengers(p_id,f_name, l_name, gender, birth_year) VALUES (100,"peleg","zbrv",1,1996),
								       (101,"momo","toto",0,1998),
                                                                       (102,"suger","milk",0,1997),
                                                                       (103,"tatto","loco",1,1999),
                                                                       (104,"toco","moco",1,1992);
								       
INSERT INTO planes(tail_no, make, model, capacity, mph) VALUES 	       (200,123,"A123",200,500),
								       (201,456,"B456",250,600),
								       (202,132,"C132",300,700),
								       (203,984,"D984",100,800),
								       (204,484,"Y484",150,900);

INSERT INTO flights(flight_no, dep_loc, dep_date, dep_time, arr_loc, arr_date, arr_time, tail_no) VALUES 
								       (10,1,"1996-02-10","00:01",1,"1996-02-11","00:02",200),
								       (11,2,"2000-10-05","05:10",2,"2000-10-06","00:00",201),
                                                                       (12,3,"1990-05-07","20:50",3,"1990-05-08","00:05",202),
                                                                       (13,4,"2014-01-08","01:05",4,"2014-01-09","00:08",203),
                                                                       (14,5,"2011-10-11","15:30",5,"2011-10-12","16:00",204);

INSERT INTO onboard(p_id, flight_no, seat) VALUES (100,10,5),
						  (101,11,6),
                                                  (102,12,2),
                                                  (103,13,7),
                                                  (104,14,8);

