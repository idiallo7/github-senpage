declare @userId varchar(7)
declare @userPass varchar(25)

set     @userId = ?
set     @userPass = ?

update  theDeskAuth
set     dt_lst_login = getdate()
where   id_user   = @userId
and     user_pass = @userPass

select  user_init, 
        case
         when combo_grp is not null then combo_grp
         else user_grp
        end as user_grp
from    theDeskAuth
where   id_user   = @userId
and     user_pass = @userPass