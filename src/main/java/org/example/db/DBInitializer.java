package org.example.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {
    public DBInitializer() {

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement())
        {
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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
