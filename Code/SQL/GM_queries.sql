-- Luís Felipe S. Baptista	| 20190972
-- Codigo de queries da DB gardenmarket

use gardenmarket;

-- Active ads Springboot view
select ad_id as id, ad_title as title, usr_name as seller, ad_price as price, catg_name as category
from advertisements a, users, adcategories c
where usr_id = sellr_id and c.catg_id = a.catg_id
and ad_isactive = true;

-- Active ads view filtered by title
EXPLAIN select ad_id as id, ad_title as title, usr_name as seller, ad_price as price, catg_name as category
from advertisements a, users, adcategories c
where usr_id = sellr_id and c.catg_id = a.catg_id
and ad_isactive = true
and ad_title like '%Garden%';

CREATE INDEX search_filtered_ads ON advertisements (ad_title, catg_id);

-- Active ads view filtered by category
select ad_id as id, ad_title as title, usr_name as seller, ad_price as price, catg_name as category
from advertisements a, users, adcategories c
where usr_id = sellr_id and c.catg_id = a.catg_id
and ad_isactive = true
and catg_name = 'Produce';

-- Active ads view filtered by seller
select ad_id as id, ad_title as title, usr_name as seller, ad_price as price, catg_name as category
from advertisements a, users, adcategories c
where usr_id = sellr_id and c.catg_id = a.catg_id
and ad_isactive = true
and sellr_id = 5;

-- One user's shopping cart view
select a.ad_id, sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive, t.transct_id, buyer_id
from advertisements a
inner join transactionitems ts on ts.ad_id = a.ad_id
inner join transactions t on t.transct_id = ts.transct_id
where t.buyer_id = 6 and a.ad_isactive = true;

-- Shopping cart view in Ad Summary form
select a.ad_id as id, ad_title as title, usr_name as seller, ad_price as price, catg_name as category
from advertisements a
inner join users u on usr_id = a.sellr_id
inner join adcategories c on c.catg_id = a.catg_id
inner join transactionitems ts on ts.ad_id = a.ad_id
inner join transactions t on t.transct_id = ts.transct_id
WHERE t.buyer_id = 6 and a.ad_isactive = true;

-- View all active ads ordered by category
select * from advertisements a
inner join adcategories ac on  a.catg_id = ac.catg_id
where ad_isactive = true
group by ad_id order by ac.catg_id asc;

-- View most popular ad categories
select catg_id, count(catg_id) from advertisements a
group by catg_id order by count(catg_id) desc;
-- listed by name
select catg_name from adcategories where catg_id in 
(
	select catg_id from advertisements a
    group by catg_id order by count(catg_id) desc
);

-- View each users' total spending
select t.transct_id, usr_name, sum(ad_price) as total_spending from transactions t
inner join users u on usr_id = t.buyer_id
inner join transactionitems ti on ti.transct_id = t.transct_id
inner join advertisements a on a.ad_id = ti.ad_id
where ad_isactive = false
group by transct_id
order by total_spending desc;

-- Calculate aggregate total spending
select sum(ad_price) as total from advertisements 
where ad_isactive = false;

-- Calculate average spending per user
select sum(ad_price)/(select count(*) from users) as average_spending from advertisements
where ad_isactive = false;

-- View current sate and time of state change of all transactions
select t.transct_id, t.buyer_id, ts_date, max(ts.state_id) as statelvl from transactions t
inner join users u on usr_id = t.buyer_id
inner join transactionstate ts on ts.transct_id = t.transct_id
group by t.transct_id;

-- View current sate of one transaction
select t.transct_id, t.buyer_id, max(ts.state_id) as statelvl from transactions t
inner join users u on usr_id = t.buyer_id
inner join transactionstate ts on ts.transct_id = t.transct_id
where t.transct_id = 4;

-- View current state of all of a user's transactions
select t.transct_id, t.buyer_id, max(ts.state_id) as statelvl from transactions t
inner join users u on usr_id = t.buyer_id
inner join transactionstate ts on ts.transct_id = t.transct_id
where usr_id = 6
group by t.transct_id;

-- View a user's cart transaction(s), if they have one
select * from 
(
	select t.transct_id, t.buyer_id, max(ts.state_id) as statelvl from transactions t
	inner join users u on usr_id = t.buyer_id
	inner join transactionstate ts on ts.transct_id = t.transct_id
	where usr_id = 6
	group by t.transct_id
) a
where statelvl = 1
limit 1;

-- View one user's transactions without an associated state 
select t.transct_id as id, buyer_id as buyerId from transactions t
left join transactionstate ts
on t.transct_id = ts.transct_id
where ts.transct_id IS NULL;

-- View one user's latest transaction without an associated state 
select t.transct_id as id, buyer_id as buyerId from transactions t
left join transactionstate ts
on t.transct_id = ts.transct_id
where ts.transct_id IS NULL
and buyer_id = 6
limit 1;

-- View all ads in a given transaction
SELECT * FROM advertisements a 
INNER JOIN transactionitems ts ON ts.ad_id = a.ad_id
INNER JOIN transactions t ON t.transct_id = ts.transct_id
WHERE t.transct_id = 6;
    
-- Set active status of all ads in a given transaction
UPDATE advertisements SET ad_isactive = TRUE 
WHERE ad_id IN (
	SELECT aid FROM (
		SELECT a.ad_id AS aid FROM advertisements a
		INNER JOIN transactionitems ts ON ts.ad_id = a.ad_id
		INNER JOIN transactions t ON t.transct_id = ts.transct_id
		WHERE t.transct_id = 12
	) a
); 

-- View all of a user's puchased items
select a.ad_id, sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive, t.transct_id, buyer_id
from advertisements a
inner join transactionitems ti on ti.ad_id = a.ad_id
inner join transactions t on t.transct_id = ti.transct_id
where t.buyer_id = 6 and a.ad_isactive = FALSE;

-- Checking if an ad is active
SELECT ad_isactive FROM advertisements
WHERE ad_id = 9;

-- Checking whether an ad is from a given user
SELECT count(*) AS from_user FROM advertisements
INNER JOIN users ON sellr_id = usr_id
WHERE ad_id = 1 AND usr_id = 6;

-- Checking if an ad is already in a user's cart
SELECT count(*) AS already_in_cart FROM advertisements a
INNER JOIN transactionitems ti ON ti.ad_id = a.ad_id
INNER JOIN transactions t ON t.transct_id = ti.transct_id
INNER JOIN transactionstate ts ON ts.transct_id = t.transct_id
INNER JOIN users u ON u.usr_id = t.buyer_id
WHERE a.ad_id = 1 AND u.usr_id = 6;

-- Getting all of one user's purchased items with transaction id
SELECT t.transct_id AS transactionId, ts_date AS purchaseDate, s.state_name AS state, a.ad_id AS id, a.ad_title AS title, u.usr_name AS buyer, a.sellr_id AS seller, a.ad_price AS price, c.catg_name AS category
FROM advertisements a
INNER JOIN adcategories c ON a.catg_id = c.catg_id
INNER JOIN transactionitems ti ON ti.ad_id = a.ad_id
INNER JOIN transactions t ON t.transct_id = ti.transct_id
INNER JOIN transactionstate ts ON ts.transct_id = t.transct_id
INNER JOIN state s ON s.state_id = ts.state_id
INNER JOIN users u ON u.usr_id = t.buyer_id
WHERE u.usr_id = 6 AND ts.state_id BETWEEN 2 AND 4;




