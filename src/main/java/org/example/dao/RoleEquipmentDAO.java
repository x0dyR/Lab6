package org.example.dao;

import org.example.db.DatabaseConnection;
import org.example.model.Equipment;
import org.example.model.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoleEquipmentDAO {

    public void assignEquipmentToRole(int roleId, int equipmentId) {
        String sql = "insert into role_equipment (role_id, equipment_id) values (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, equipmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeEquipmentFromRole(int roleId, int equipmentId) {
        String sql = "delete from role_equipment where role_id = ? and equipment_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ps.setInt(2, equipmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Equipment> getEquipmentByRole(int roleId) {
        List<Equipment> equipmentList = new ArrayList<>();
        String sql = """
            select e.id, e.name
            from role_equipment re
            join equipment e on re.equipment_id = e.id
            where re.role_id = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Equipment equipment = new Equipment(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return equipmentList;
    }
}
