create table senBudgetTransactionTbl
(
 id int primary key NOT NULL,
 trans_date datetime NOT NULL,
 item VARCHAR(50) NOT NULL,
 cfa DECIMAL NOT NULL,
 dol DECIMAL NOT NULL,
 wu_rate DECIMAL,
 bb_rate DECIMAL,
 diff_rate DECIMAL,
 curr_user varchar(30) NOT NULL

 /*trans_type CHAR(2) NOT NULL,*/
)