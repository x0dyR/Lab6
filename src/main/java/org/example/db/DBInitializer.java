package org.example.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public DBInitializer() {

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("create table if not exists users(" +
                    "id int auto_increment primary key," +
                    " username varchar(50) not null unique," +
                    " password varchar(255) not null)"
            );

            statement.execute("create table if not exists roles(" +
                    "id int auto_increment primary key," +
                    " name varchar(50) not null unique)"
            );

            statement.execute("create table if not exists permissions(" +
                    "id int auto_increment primary key," +
                    " name varchar(50) not null unique)"
            );

            statement.execute("create table if not exists user_roles(" +
                    "user_id int," +
                    " role_id int," +
                    " primary key (user_id, role_id)," +
                    " foreign key (user_id) references users(id)," +
                    " foreign key (role_id) references roles(id))"
            );

            statement.execute("create table if not exists role_permissions(" +
                    "role_id int," +
                    " permission_id int," +
                    " primary key (role_id, permission_id)," +
                    " foreign key (role_id) references roles(id)," +
                    " foreign key (permission_id) references permissions(id))"
            );

            statement.execute("create table if not exists equipment(" +
                    "id int auto_increment primary key," +
                    "name varchar(100) not null unique)"
            );

            statement.execute("create table if not exists role_equipment(" +
                    "role_id int not null," +
                    "equipment_id int not null," +
                    "primary key (role_id, equipment_id)," +
                    "foreign key (role_id) references roles(id)," +
                    "foreign key (equipment_id) references equipment(id))"
            );

            statement.execute("create table if not exists equipment_orders (" +
                    "id int auto_increment primary key," +
                    "user_id int not null," +
                    "equipment_id int not null," +
                    "status enum('pending', 'serviced') default 'pending'," +
                    "foreign key (user_id) references users(id)," +
                    "foreign key (equipment_id) references equipment(id))"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
