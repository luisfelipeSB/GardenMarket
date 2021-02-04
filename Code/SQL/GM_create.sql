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
create table adcategories(	catg_id int not null auto_increment,
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
                            primary key (ad_id),
                            foreign key (sellr_id) references users(usr_id) 
								ON DELETE NO ACTION ON UPDATE NO ACTION,
							foreign key (catg_id) references adcategories(catg_id)
								ON DELETE NO ACTION ON UPDATE NO ACTION);

-- All user purchases or wishlists
create table transactions(	transct_id int not null auto_increment,
							buyer_id int,
                            primary key (transct_id),
                            foreign key (buyer_id) references users(usr_id)
								ON DELETE NO ACTION ON UPDATE NO ACTION);
                            
-- Possible states of a transaction
create table state( state_id int not null auto_increment,
					state_name varchar(30),
					primary key (state_id));
                            
-- Literal states of a transaction and its definition date
create table transactionstate(	ts_id int not null auto_increment,
								transct_id int,
                                state_id int,
                                ts_date datetime not null,
                                primary key (ts_id),
                                foreign key (transct_id) references transactions(transct_id)
									ON DELETE NO ACTION ON UPDATE NO ACTION,
								foreign key (state_id) references state(state_id)
									ON DELETE NO ACTION ON UPDATE NO ACTION);
                            
-- Items from all user purchases or wishlists
create table transactionitems( 	item_id int not null auto_increment,
								transct_id int,
								ad_id int,
								primary key (item_id),
                                foreign key (transct_id) references transactions(transct_id)
									ON DELETE NO ACTION ON UPDATE NO ACTION,
								foreign key (ad_id) references advertisements(ad_id)
									ON DELETE NO ACTION ON UPDATE NO ACTION);
 