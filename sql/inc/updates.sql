-- push password hash updates for users 1 and 3, changed immediately
update users t1
set password_hash = t2.password_hash
from (
	values (1, '$argon2id$v=19$m=16384,t=2,p=1$RHxRPji8qmhIE2XSFCUBlA$y5weWAThhAxEMhZb8sVZSN+7ctFpAsOAV+ootvqZ18c'),
		(3, '$argon2id$v=19$m=16384,t=2,p=1$ZgkfroTVXkrlOJJph28NOA$wEKMZ4X288fWIe9uCD1Lrahw6Vzi3p9hIvAQe5hI7LM')
) as t2(user_id, password_hash)
where t1.user_id = t2.user_id;