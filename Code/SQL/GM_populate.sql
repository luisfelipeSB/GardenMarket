-- Luís Felipe S. Baptista	| 20190972
-- Codigo de preenchimento de informacao da DB gardenmarket

insert into users (usr_name, usr_password, usr_email) 
values 	("Alice", "p4ssw0rd", "alice@gmail.com"),
		("Bob", "pa55word", "bob@gmail.com"),
        ("Carol", "password123", "carol@gmail.com"),
        ("David", "123password", "david@gmail.com"),
        ("Edith", "xXpasswordXx", "edith@gmail.com"),
        ("Johnny Test", "psswrd", "johnny@gmail.com");		

insert into adcategories (catg_name) 
values 	("Produce"),
		("Seeds"),
        ("Plants"),
        ("Fertilizers"),
        ("Natural-product"),
        ("Other");
        
insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) 
values 	(1, 1, "Homegrown Pumpkins", "Vibrant", 7.50, true),
		(1, 4, "Compost", "Top quality", 12.50, true),
        (2, 2, "Tomato Seeds", "Healthy seeds", 6.75, true),
        (2, 3, "Citrus × Sinesis", "Beautiful", 20.00, true),
        (3, 1, "Fresh Potatoes", "Tasty", 3.00, true),
        (3, 5, "Honey", "Incredibly sweet", 2.25, true),
        (4, 3, "Bio Chamomile", "Pretty", 2.25, true),
        (4, 6, "Compost Bin", "Efficient", 32.50, true),
        (4, 4, "Nutrient Water", "Nutrient-rich", 16.00, true),
        (4, 2, "Hydroponic Seed Pods", "Varied", 14.50, true),
        (5, 3, "Sunflower Bundle", "Brilliant", 4.00, true),
        (5, 6, "Gardening for Dummies", "Informative", 26.75, true),
        (5, 6, "Gardening Toolbag", "Brand New", 35.00, true),
        (5, 1, "Organic Carrots", "Natural", 4.75, true),
        (5, 1, "Fresh Tomatoes", "Homegrown", 3.75, true);

insert into state (state_name) 
values 	("CART"),
		("PURCHASED"),
        ("DISPATCHED"),
        ("DELIVERED");


/*----- ALICE's TRIP -----/
-- Alice adds items to her cart
insert into transactions (buyer_id) values (1);					-- transct 1
insert into transactionstate (transct_id, state_id, ts_date)	-- set to cart state
	values (1, 1, sysdate());
insert into transactionitems (transct_id, ad_id) values (1, 3);	-- ad 3
insert into transactionitems (transct_id, ad_id) values (1, 5); -- ad 5

-- Alice buys her items
insert into transactionstate (transct_id, state_id, ts_date) 
	values (1, 2, sysdate());
update advertisements set ad_isActive = false where ad_id = 3 or ad_id = 5;

-- Alice's items are dispatched
insert into transactionstate (transct_id, state_id, ts_date) 
	values (1, 3, sysdate());
    
-- Alice's items are delivered
insert into transactionstate (transct_id, state_id, ts_date) 
	values (1, 4, sysdate());
    

/*----- BOB's TRIP -----/
-- Bob adds items to his cart
insert into transactions (buyer_id) values (2);					-- transct 2
insert into transactionstate (transct_id, state_id, ts_date)	-- set to cart state
	values (2, 1, sysdate());
insert into transactionitems (transct_id, ad_id) values (2, 1);	-- ad 1

-- Bob buys his items
insert into transactionstate (transct_id, state_id, ts_date) 
	values (2, 2, sysdate());
update advertisements set ad_isActive = false where ad_id = 1;

-- Bob's items are dispatched
insert into transactionstate (transct_id, state_id, ts_date) 
	values (2, 3, sysdate());


/*----- CAROL's TRIP -----/
-- Carol adds items to her cart
insert into transactions (buyer_id) values (3);					-- transct 3
insert into transactionstate (transct_id, state_id, ts_date)	-- set to cart state
	values (3, 1, sysdate());
insert into transactionitems (transct_id, ad_id) values (3, 2);	-- ad 2
insert into transactionitems (transct_id, ad_id) values (3, 4);	-- ad 4

-- Carol buys her items
insert into transactionstate (transct_id, state_id, ts_date) 
	values (3, 2, sysdate());
update advertisements set ad_isActive = false where ad_id = 2 or ad_id = 4;


/*----- ALICE's SECOND TRIP -----/
-- Alice adds items to her cart
insert into transactions (buyer_id) values (1);					-- transct 4
insert into transactionstate (transct_id, state_id, ts_date)	-- set to cart state
	values (4, 1, sysdate());
insert into transactionitems (transct_id, ad_id) values (4, 6);	-- ad 6


/*----- JOHNNY BUYS HIS ITEMS -----/
CALL create_cart(6);
insert into transactionitems (transct_id, ad_id) values (9, 9);	-- ad 6

select * from advertisements where ad_isactive = true;
-- CALL update_ts(6, 2);

/*----- TEST QUERIES -----*/
select * from advertisements;
select * from transactions;
select * from transactionitems;
select * from transactionstate;
