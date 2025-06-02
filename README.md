# Для запуска MySQL используется docker
``` bash
docker run --name MySQL -e MYSQL_ROOT_PASSWORD=Strong!Password -e MYSQL_DATABASE=lab6 -p 3306:3306 -d mysql:8.0
```
# Интерактивный режим MySQL
``` bash
docker exec -it MySQL mysql -uroot -p
``` 
### Заполнение всех таблиц 
``` SQL
USE lab6;

-- Очистка данных
DELETE FROM role_permissions;
DELETE FROM user_roles;
DELETE FROM permissions;
DELETE FROM roles;
DELETE FROM users;
DELETE FROM equipment;

-- Создание пользователей
INSERT INTO users (id, username, password) VALUES
                                               (1, 'admin', 'admin123'),
                                               (2, 'engineer', 'eng123'),
                                               (3, 'technician', 'tech123');

-- Создание ролей
INSERT INTO roles (id, name, level) VALUES
                                        (1, 'admin', 100),
                                        (2, 'Engineer', 50),
                                        (3, 'Technician', 30);

-- Создание разрешений (permissions)
INSERT INTO permissions (id, name) VALUES
                                       (1, 'view_users'),
                                       (2, 'create_users'),
                                       (3, 'edit_users'),
                                       (4, 'delete_users'),
                                       (5, 'view_roles'),
                                       (6, 'create_roles'),
                                       (7, 'edit_roles'),
                                       (8, 'delete_roles'),
                                       (9, 'view_permissions'),
                                       (10, 'create_permissions'),
                                       (11, 'edit_permissions'),
                                       (12, 'delete_permissions'),
                                       (13, 'place_orders'),
                                       (14, 'process_orders'),
                                       (17, 'manage_user_roles'),
                                       (18, 'manage_role_permissions'),
                                       (19, 'rbac_overview'),
                                       (20, 'user_access_overview');

-- Создание оборудования
INSERT INTO equipment (id, name) VALUES
                                     (1, '3D Printer'),
                                     (2, 'Laser Cutter'),
                                     (3, 'CNC Machine'),
                                     (4, 'Welding Machine');

-- Привязка ролей к пользователям
INSERT INTO user_roles (user_id, role_id) VALUES
                                              (1, 1),
                                              (2, 2),
                                              (3, 3);

-- Привязка разрешений к ролям

-- Права для admin (полный доступ)
INSERT INTO role_permissions (role_id, permission_id) VALUES
                                                          (1, 1),
                                                          (1, 2),
                                                          (1, 3),
                                                          (1, 4),
                                                          (1, 5),
                                                          (1, 6),
                                                          (1, 7),
                                                          (1, 8),
                                                          (1, 9),
                                                          (1, 10),
                                                          (1, 11),
                                                          (1, 12),
                                                          (1, 13),
                                                          (1, 14),
                                                          (1, 17),
                                                          (1, 18),
                                                          (1, 19),
                                                          (1, 20);

-- Права для Engineer (только на создание заказов)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (2, 13);

-- Права для Technician (только обработка заказов)
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (3, 14);

```