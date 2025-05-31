package org.example.ui;

import org.example.dao.RoleDAO;
import org.example.dao.EquipmentDAO;
import org.example.dao.RoleEquipmentDAO;
import org.example.model.Role;
import org.example.model.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoleEquipmentManagementFrame extends JFrame {

    private final RoleDAO roleDAO = new RoleDAO();
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    private final RoleEquipmentDAO roleEquipmentDAO = new RoleEquipmentDAO();

    private final JComboBox<Role> roleComboBox = new JComboBox<>();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"ID", "Оборудование"}, 0);
    private final JTable table = new JTable(tableModel);

    public RoleEquipmentManagementFrame() {
        setTitle("Доступ ролей к оборудованию");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadRoles();

        roleComboBox.addActionListener(e -> refreshTable());
        add(roleComboBox, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton addButton = new JButton("Добавить доступ");
        JButton removeButton = new JButton("Удалить доступ");

        addButton.addActionListener(e -> addAccess());
        removeButton.addActionListener(e -> removeAccess());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadRoles() {
        List<Role> roles = roleDAO.getAllRoles();
        for (Role role : roles) {
            roleComboBox.addItem(role);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        Role role = (Role) roleComboBox.getSelectedItem();
        if (role != null) {
            List<Equipment> equipmentList = roleEquipmentDAO.getEquipmentByRole(role.getId());
            for (Equipment equipment : equipmentList) {
                tableModel.addRow(new Object[]{equipment.getId(), equipment.getName()});
            }
        }
    }

    private void addAccess() {
        Role role = (Role) roleComboBox.getSelectedItem();
        if (role == null) return;

        List<Equipment> allEquipment = equipmentDAO.getAllEquipment();
        Equipment selected = (Equipment) JOptionPane.showInputDialog(
                this,
                "Выберите оборудование:",
                "Назначить доступ",
                JOptionPane.PLAIN_MESSAGE,
                null,
                allEquipment.toArray(),
                null
        );

        if (selected != null) {
            roleEquipmentDAO.assignEquipmentToRole(role.getId(), selected.getId());
            refreshTable();
        }
    }

    private void removeAccess() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) return;

        Role role = (Role) roleComboBox.getSelectedItem();
        int equipmentId = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Удалить доступ?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            roleEquipmentDAO.removeEquipmentFromRole(role.getId(), equipmentId);
            refreshTable();
        }
    }
}
