package org.example.ui;

import org.example.dao.EquipmentDAO;
import org.example.dao.EquipmentOrderDAO;
import org.example.dao.UserDAO;
import org.example.model.Equipment;
import org.example.model.EquipmentOrder;
import org.example.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProcessOrdersFrame extends JFrame {
    private final EquipmentOrderDAO orderDAO = new EquipmentOrderDAO();
    private final UserDAO userDAO = new UserDAO();
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ProcessOrdersFrame() {
        setTitle("Обработка заказов на оборудование");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Пользователь", "Оборудование", "Статус"}, 0);
        table = new JTable(tableModel);
        refreshTable();

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton markProcessedButton = new JButton("Отметить как обслужено");
        markProcessedButton.addActionListener(e -> markAsProcessed());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(markProcessedButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<EquipmentOrder> orders = orderDAO.getPendingOrders();

        for (EquipmentOrder order : orders) {
            User user = userDAO.getUserById(order.getUserId());
            Equipment equipment = equipmentDAO.getEquipmentById(order.getEquipmentId());
            tableModel.addRow(new Object[]{
                    order.getId(),
                    user != null ? user.getUsername() : "unknown",
                    equipment != null ? equipment.getName() : "unknown",
                    order.getStatus()
            });
        }
    }

    private void markAsProcessed() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Выберите заказ для обработки.");
            return;
        }
        int orderId = (int) tableModel.getValueAt(selectedRow, 0);
        orderDAO.updateOrderStatus(orderId, "обслужено");
        refreshTable();
    }
}
