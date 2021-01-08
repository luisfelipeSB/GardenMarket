-- Luís Felipe S. Baptista	| 20190972
-- Codigo de criacao da DB gardenmarket

create database gardenmarket;
use gardenmarket;

-- A regular signed-up user
create table users (usr_id int not null auto_increment,
					usr_name varchar(60) not null,
                    usr_password varchar(60) not null,
                    usr_email varchar(60) not null,
                    primary key (usr_id));
               
-- Possible ad categories in the platform
create table categories(catg_id int not null auto_increment,
						catg_name varchar(30),
                        primary key (catg_id));
                        
-- All user-created advertisements
create table advertisements(ad_id int not null auto_increment,
							sellr_id int,
                            catg_id int,
							ad_title varchar(30),
                            ad_description varchar(100),
                            ad_price decimal(7,2),
                            ad_isactive boolean,
                            primary key (ad_id));
                            
-- Items from all user purchases AND Items from all user wishlists
create table transactionitems( 	transct_id int,
								item_id int not null auto_increment,
								buyer_id int,
								ad_id int,
								primary key (item_id));
                        
-- All transaction information
create table transactions (	transct_id int not null auto_increment,
							transct_total decimal(7,2),
                            transct_paymentdate datetime,
                            transct_dispatchdate datetime,
                            transct_deliverydate datetime,
                            primary key (transct_id));

-- Foreign keys

alter table advertisements add constraint 
            foreign key (sellr_id) references users(usr_id) 
			ON DELETE NO ACTION ON UPDATE NO ACTION;
alter table advertisements add constraint 
			foreign key (catg_id) references categories(catg_id)
            ON DELETE NO ACTION ON UPDATE NO ACTION;
            
alter table transactionitems add constraint
			foreign key (buyer_id) references users(usr_id)
			ON DELETE NO ACTION ON UPDATE NO ACTION;
alter table transactionitems add constraint
			foreign key (ad_id) references advertisements(ad_id)
			ON DELETE NO ACTION ON UPDATE NO ACTION;
alter table transactionitems add constraint 
            foreign key (transct_id) references transactions(transct_id) 
			ON DELETE NO ACTION ON UPDATE CASCADE;