use  RCTTC;

-- Find all performances in the last quarter of 2021 (Oct. 1, 2021 - Dec. 31 2021).
select show_name, show_date
from `show`
where show_date between '2021-10-01' and '2021-12-31';

-- List customers without duplication. 
select customer_id, customer_last, customer_first
from customer;

-- Find all customers without a .com email address.
select customer_id, customer_last, customer_first, customer_email
from customer
where customer_email not like ('%.com');

-- Find the three cheapest shows.
select show_name, ticket_price
from `show`
order by ticket_price asc
limit 3;

-- List customers and the show they're attending with no duplication.
select distinct customer_last, customer_first, show_name
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id;

-- List customer, show, theater, and seat number in one query.
select customer_last, customer_first, show_name, theater_name, seat
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id 
inner join theater as t on s.theater_id = t.theater_id;

-- Find customers without an address.
select customer_id, customer_last, customer_first, customer_address
from customer
where customer_address = '';

-- Recreate the spreadsheet data with a single query.
select customer_last, customer_first, customer_email, customer_phone, customer_address, 
seat, show_name as `show`, ticket_price, show_date, theater_address, theater_phone, theater_email
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id 
inner join theater as t on s.theater_id = t.theater_id;

-- Count total tickets purchased per customer.
select  c.customer_last, c.customer_first, count(r.reservation_id) as number_of_tickets
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id 
inner join theater as t on s.theater_id = t.theater_id
group by  c.customer_last, c.customer_first;

-- Calculate the total revenue per show based on tickets sold.
-- total revenue for shows (ignore date)
select sum(s.ticket_price), s.show_name
from reservation as r
inner join `show` as s on r.show_id = s.show_id
inner join theater as t on s.theater_id = t.theater_id
group by s.show_name
order by sum(s.ticket_price) desc;

-- Calculate the total revenue per theater based on tickets sold.
select t.theater_name, sum(s.ticket_price) as total_revenue
from reservation as r
inner join `show` as s on r.show_id = s.show_id
inner join theater as t on s.theater_id = t.theater_id
group by t.theater_id
order by total_revenue desc;


-- Who is the biggest supporter of RCTTC? Who spent the most in 2021?
select  c.customer_last, c.customer_first, count(r.reservation_id) as number_of_tickets
from customer as c 
inner join reservation as r on c.customer_id = r.customer_id
inner join `show` as s on r.show_id = s.show_id 
inner join theater as t on s.theater_id = t.theater_id
group by  c.customer_last, c.customer_first
order by number_of_tickets desc;