SELECT trans_date, top_wu, top_bb, diff_rate FROM SENBUDGETTRANSACTIONTBL
 where trans_date=(SELECT max(trans_date) FROM SENBUDGETTRANSACTIONTBL)
ORDER BY top_wu DESC
