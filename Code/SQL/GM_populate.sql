-- Luís Felipe S. Baptista	| 20190972
-- Codigo de preenchimento de informacao da DB gardenmarket

insert into users (usr_name, usr_password, usr_email) values ("Alice", "p4ssw0rd", "alice@gmail.com");		-- usr 1
insert into users (usr_name, usr_password, usr_email) values ("Bob", "pa55word", "bob@gmail.com");			-- usr 2
insert into users (usr_name, usr_password, usr_email) values ("Carol", "password123", "carol@gmail.com");	-- usr 3

insert into categories (catg_name) values ("Produce");		-- catg 1
insert into categories (catg_name) values ("Seeds");		-- catg 2
insert into categories (catg_name) values ("Plants");		-- catg 3
insert into categories (catg_name) values ("Fertilizers");	-- catg 4

insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) values (1, 1, "Homegrown Pumpkins", "Great", 7.50, true);	-- ad 1
insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) values (1, 4, "Fertilizer", "Top quality", 12.50, true); 	-- ad 2
insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) values (2, 2, "Tomato Seeds", "Good", 6.75, true);			-- ad 3
insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) values (2, 3, "Bio Chamomile", "Excellent", 10.25, true);	-- ad 4 
insert into advertisements (sellr_id, catg_id, ad_title, ad_description, ad_price, ad_isactive) values (3, 1, "Fresh Potatoes", "Marvelous", 3.00, true);	-- ad 5 

insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 1, 4); 	-- Alice adds chamomile to her wishlist
insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 1, 3); 	-- Alice adds tomato seeds to her wishlist
insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 2, 2); 	-- Bob adds fertilizer to his wishlist
insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 3, 1); 	-- Carol adds pumpkins to her wishlist

-- Alice proceeds to checkout
update advertisements set ad_isactive = false where ad_id = 4 or ad_id = 3;			-- Alice's wishlist items become unbuyable
insert into transactions (transct_total, transct_paymentdate, transct_dispatchdate, transct_deliverydate) values (6.75+10.25, sysdate(), str_to_date('2021.01.08','%Y.%m.%d'), str_to_date('2021.01.15','%Y.%m.%d')); 	-- Alice buys her items
update transactionitems set transct_id = 1 where buyer_id = 1;						-- Her wishlist items become associated with the transaction

insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 1, 5); 	-- Alice adds potatoes to her wishlist for her next purchase
insert into transactionitems (transct_id, buyer_id, ad_id) values (null, 2, 1); 	-- Bob adds pumpkins to his wishlist

-- Bob proceeds to checkout
update advertisements set ad_isactive = false where ad_id = 1 or ad_id = 2;			-- Bob's wishlist items become unbuyable
insert into transactions (transct_total, transct_paymentdate, transct_dispatchdate, transct_deliverydate) values (12.50+7.50, sysdate(), str_to_date('2021.01.09','%Y.%m.%d'), str_to_date('2021.01.16','%Y.%m.%d')); 	-- Bob buys his items
update transactionitems set transct_id = 2 where buyer_id = 2;						-- His wishlist items become associated with the transaction

-- Testing tables
select * from transactions;
select * from transactionitems;