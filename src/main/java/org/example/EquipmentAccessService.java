package org.example.service;

import org.example.dao.UserRoleDAO;
import org.example.dao.RoleEquipmentDAO;
import org.example.model.Role;

import java.util.List;

public class EquipmentAccessService {

    private final UserRoleDAO userRoleDAO = new UserRoleDAO();
    private final RoleEquipmentDAO roleEquipmentDAO = new RoleEquipmentDAO();

    public boolean hasAccessToEquipment(int userId, int equipmentId) {
        // Получаем все роли пользователя
        List<Role> userRoles = userRoleDAO.getRolesByUser(userId);

        for (Role role : userRoles) {
            // Проверяем, есть ли у данной роли доступ к этому оборудованию
            boolean roleHasAccess = roleEquipmentDAO
                    .getEquipmentByRole(role.getId())
                    .stream()
                    .anyMatch(equipment -> equipment.getId() == equipmentId);

            if (roleHasAccess) {
                return true;
            }
        }

        return false;
    }
}
