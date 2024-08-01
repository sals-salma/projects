use RCTTC;

-- write selects

insert into customer(customer_first, customer_last, customer_email, customer_phone, customer_address)
select distinct customer_first, customer_last, customer_email, customer_phone, customer_address
from RCTTC.`rcttc-raw-data`;

insert into theater(theater_name, theater_address, theater_phone, theater_email)
select distinct theater as theater_name , theater_address, theater_phone, theater_email
from RCTTC.`rcttc-raw-data`;

insert into `show`(theater_id, show_name, ticket_price, show_date)
select distinct theater_id, `show` as show_name , ticket_price, `date` as show_date
from RCTTC.`rcttc-raw-data` as r
inner join theater as t
on r.theater = t.theater_name;

insert into reservation(customer_id, show_id, seat)

select distinct customer_id, show_id, seat 
from RCTTC.`rcttc-raw-data` as r
inner join customer as c
on r.customer_last = c.customer_last 
inner join `show` as s
on r.`show` = s.show_name and s.show_date = r.date ;

-- update : 1
-- The Little Fitz's 2021-03-01 performance of The Sky Lit Up is listed with a $20 ticket price.
-- The actual price is $22.25 because of a visiting celebrity actor. 
-- (Customers were notified.) Update the ticket price for that performance only.
select *
from `show` 
where show_name = "The Sky Lit Up "
and show_date = "2021-03-01" 
and ticket_price = 20;

update `show` set 
ticket_price = 22.25
where show_id = 5;

-- update: 2
-- In the Little Fitz's 2021-03-01 performance of The Sky Lit Up, Pooh Bedburrow and Cullen Guirau seat reservations aren't in the same row.
-- Adjust seating so all groups are seated together in a row. This may require updates to all reservations for that performance. 
-- Confirm that no seat is double-booked and that everyone who has a ticket is as close to their original seat as possible.

select c.customer_last, c.customer_first, r.seat, c.customer_id
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id and s.show_name = "The Sky Lit Up" and s.show_date = "2021-03-01"
inner join theater as t on s.theater_id = t.theater_id and t.theater_name = "Little Fitz";

-- 1. Move Bedburrow from A4 to B4 (id: 37)
update reservation 
set seat = "B4"
where customer_id = 37 and seat = "A4";
-- 2. Move Guirau from B4 to C2 (id:38)
update reservation 
set seat =  "C2"
where customer_id = 38 and seat = "B4";
-- 3. Vail from C2 to A4 (id: 39)
update reservation 
set seat =  "A4"
where customer_id = 39 and seat = "C2";

-- update: 3
-- Update Jammie Swindles's phone number from "801-514-8648" to "1-801-EAT-CAKE".
select * from customer
where customer_last = "Swindles" and customer_first = "Jammie";

update customer set
customer_phone = "1-801-EAT-CAKE"
where customer_id = 48;

-- delete: 1
-- Delete all single-ticket reservations at the 10 Pin. (You don't have to do it with one query.)
select reservation_id
from reservation as r 
inner join `show` as s on r.show_id = s.show_id and s.theater_id = 1
where customer_id in 
(select tempQuery.customer_id
from 
(select count(*) as `count`, r.customer_id
from reservation as r 
inner join `show` as s on r.show_id = s.show_id
inner join theater as t on s.theater_id = t.theater_id and t.theater_name = "10 Pin"
group by r.customer_id) as tempQuery 
where tempQuery.`count` = 1); 

delete from reservation
where reservation_id in (25
, 26
, 29 
, 41
, 50
, 51
, 59
, 67
, 68); 

-- delete: 2
-- Delete the customer Liv Egle of Germany. It appears their reservations were an elaborate joke.

select * from customer
where customer_first = "liv" and customer_last = "Egle of Germany";
-- customer_id : 65
select * from reservation
where customer_id = 65;

delete from reservation 
where customer_id = 65;

delete from customer
where customer_id = 65;
