create table senpageTest
(
 row_id bigint NOT NULL auto_increment,
 
 language varchar(30) NOT NULL,
 primary key (row_id)
 
)


create table senBudgetAlluserinfo
(
 id int primary key,
 username varchar(30) NOT NULL,
 pass     varchar(50) NOT NULL
)

ALTER USER "TOM" RENAME TO "THOMAS"
ALTER USER "THOMAS" SET PASSWORD 'rioyxlgt'


create table senBudgetTransactionTbl
(
 trans_id bigint NOT NULL auto_increment,
 trans_date datetime NOT NULL,
 curr_user varchar(20) NOT NULL,

 top_wu DECIMAL NOT NULL,
 top_bb DECIMAL NOT NULL,
 item VARCHAR(50) NOT NULL,
 cfa DECIMAL NOT NULL,
 dol DECIMAL NOT NULL,
 diff_rate DECIMAL,
 del_flag CHAR(1)

 primary key (trans_id)
)

create table senBudgetItemTbl
(
 item_id bigint NOT NULL AUTO_INCREMENT,
 item VARCHAR(50) NOT NULL,
 del_flag CHAR(1) ,
 curr_user varchar(30) NOT NULL,
  trans_date date not null,
 primary key (item_id)
)
