delimiter $$
CREATE trigger seat_availability2 BEFORE insert on onboard FOR EACH ROW
begin
	if NEW.seat and NEW.flight_no in (select O.seat from onboard O where (O.seat = NEW.seat))
    then DELETE from onboard O where O.seat = NEW.seat AND O.flight_no = NEW.flight_no;
    end if;
    end; $$
delimiter ;

drop procedure traveling;
delimiter $$
CREATE PROCEDURE traveling (in pass_id INT)
BEGIN
Select  A.city from Onboard O inner join flights F  on O.flight_no=F.flight_no 
						inner join airports A on A.a_id= F.arr_loc
where O.p_id = pass_id and f.dep_date Between DATE_ADD(CURRENT_DATE(), INTERVAL -1 YEAR)  and CURRENT_DATE();
END $$
delimiter ;

