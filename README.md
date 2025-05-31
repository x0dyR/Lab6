# Для запуска MySQL используется docker
``` bash
docker run --name MySQL -e MYSQL_ROOT_PASSWORD=Strong!Password -e MYSQL_DATABASE=lab6 -p 3306:3306 -d mysql:8.0
```
# Интерактивный режим MySQL
``` bash
docker exec -it MySQL mysql -uroot -p
```
### Для добавления ролей админу
``` SQL
use lab6;

select * from users;
select * from roles;
select * from user_roles;
select * from permissions;
select * from role_permissions;

insert into permissions (name) values
                                   ('view_users'),
                                   ('create_users'),
                                   ('edit_users'),
                                   ('delete_users'),
                                   ('view_roles'),
                                   ('create_roles'),
                                   ('edit_roles'),
                                   ('delete_roles'),
                                   ('view_permissions'),
                                   ('create_permissions'),
                                   ('edit_permissions'),
                                   ('delete_permissions'),
                                   ('place_orders'),
                                   ('process_orders');

INSERT IGNORE INTO role_permissions (role_id, permission_id) VALUES
                                                                 (1, 10),
                                                                 (1, 6),
                                                                 (1, 2),
                                                                 (1, 12),
                                                                 (1, 8),
                                                                 (1, 4),
                                                                 (1, 11),
                                                                 (1, 7),
                                                                 (1, 3),
                                                                 (1, 18),
                                                                 (1, 17),
                                                                 (1, 13),
                                                                 (1, 14),
                                                                 (1, 19),
                                                                 (1, 20),
                                                                 (1, 9),
                                                                 (1, 5),
                                                                 (1, 1);
```
