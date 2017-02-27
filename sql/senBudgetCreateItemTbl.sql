create table senBudgetItemTbl
(
 item_id bigint NOT NULL AUTO_INCREMENT,
 item VARCHAR(50) NOT NULL,
 del_flag CHAR(1) NOT NULL,
 curr_user varchar(150) NOT NULL,
 trans_date datetime not null,
 primary key (item_id)
)
