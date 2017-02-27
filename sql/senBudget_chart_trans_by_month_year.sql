SELECT MONTH(trans_date) as mon, YEAR(trans_date) as year,  concat(cast(MONTHNAME(trans_date) as varchar(3)), cast(YEAR(trans_date) as CHAR(4))) as monyear,
                  sum(dol) as dol
FROM SENBUDGETTRANSACTIONTBL
Group by MONTH(trans_date), YEAR(trans_date), concat(cast(MONTHNAME(trans_date) as varchar(3)), cast(YEAR(trans_date) as CHAR(4)))
order by YEAR(trans_date) , MONTH(trans_date) asc
