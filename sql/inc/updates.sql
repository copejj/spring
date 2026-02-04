-- push password hash updates for users 1 and 3, change immediately
update users t1
set password_hash = t2.password_hash
from (
	values (1, '$argon2id$v=19$m=16384,t=2,p=1$1/DzKh81rXYBR40+G9GlYw$6i5lgtB+OXgXpAOzGP677gb86lRvjKKj9QNzf4h73bo'),
		(3, '$argon2id$v=19$m=16384,t=2,p=1$1/DzKh81rXYBR40+G9GlYw$6i5lgtB+OXgXpAOzGP677gb86lRvjKKj9QNzf4h73bo')
) as t2(user_id, password_hash)
where t1.user_id = t2.user_id;