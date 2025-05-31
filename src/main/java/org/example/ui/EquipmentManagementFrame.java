package org.example.ui;

import org.example.dao.EquipmentDAO;
import org.example.model.Equipment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EquipmentManagementFrame extends JFrame {
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public EquipmentManagementFrame() {
        setTitle("Управление оборудованием");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Название"}, 0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Добавить");
        JButton editButton = new JButton("Редактировать");
        JButton deleteButton = new JButton("Удалить");
        JButton refreshButton = new JButton("Обновить");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addEquipment());
        editButton.addActionListener(e -> editEquipment());
        deleteButton.addActionListener(e -> deleteEquipment());
        refreshButton.addActionListener(e -> refreshTable());

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
        for (Equipment equipment : equipmentList) {
            tableModel.addRow(new Object[]{equipment.getId(), equipment.getName()});
        }
    }

    private void addEquipment() {
        JTextField nameField = new JTextField();
        Object[] fields = {"Название оборудования:", nameField};
        int option = JOptionPane.showConfirmDialog(null, fields, "Добавить оборудование", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            equipmentDAO.addEquipment(new Equipment(nameField.getText()));
            refreshTable();
        }
    }

    private void editEquipment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для редактирования");
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String currentName = (String) tableModel.getValueAt(selectedRow, 1);

        JTextField nameField = new JTextField(currentName);
        Object[] fields = {"Название оборудования:", nameField};
        int option = JOptionPane.showConfirmDialog(null, fields, "Редактировать оборудование", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            equipmentDAO.updateEquipment(new Equipment(id, nameField.getText()));
            refreshTable();
        }
    }

    private void deleteEquipment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите оборудование для удаления");
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Удалить оборудование?", "Подтверждение", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            equipmentDAO.deleteEquipment(id);
            refreshTable();
        }
    }
}
