package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.EquipmentOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipmentOrderDAO {

    public void createOrder(EquipmentOrder order) {
        String sql = "insert into equipment_orders (user_id, equipment_id, status) values (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getEquipmentId());
            statement.setString(3, order.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderStatus(int orderId, String status) {
        String sql = "update equipment_orders set status = ? where id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setInt(2, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EquipmentOrder> getPendingOrders() {
        List<EquipmentOrder> orders = new ArrayList<>();
        String sql = "select id, user_id, equipment_id, status from equipment_orders where status = 'создан'";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                EquipmentOrder order = new EquipmentOrder(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("equipment_id"),
                        resultSet.getString("status")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
