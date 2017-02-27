

SELECT * FROM SENBUDGETTRANSACTIONTBL
where Month(trans_date)=month((SELECT max(trans_date) FROM SENBUDGETTRANSACTIONTBL))
and  year(trans_date)=year((SELECT max(trans_date) FROM SENBUDGETTRANSACTIONTBL))
and  day(trans_date)=day((SELECT max(trans_date) FROM SENBUDGETTRANSACTIONTBL))

/*SELECT            item,
                  cfa,
                  dol
FROM       SENBUDGETTRANSACTIONTBL
where month(trans_date)=month(now())
*/
