package org.example.ui;

import org.example.dao.EquipmentOrderDAO;
import org.example.dao.EquipmentDAO;
import org.example.model.Equipment;
import org.example.model.EquipmentOrder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EquipmentOrderCreateFrame extends JFrame {

    private final EquipmentOrderDAO orderDAO = new EquipmentOrderDAO();
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();

    private final JComboBox<Equipment> equipmentComboBox = new JComboBox<>();
    private final int userId;

    public EquipmentOrderCreateFrame(int userId) {
        this.userId = userId;

        setTitle("Создать заказ");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        loadEquipment();

        JPanel panel = new JPanel();
        panel.add(new JLabel("Оборудование:"));
        panel.add(equipmentComboBox);
        add(panel, BorderLayout.CENTER);

        JButton createButton = new JButton("Создать заказ");
        createButton.addActionListener(e -> createOrder());
        add(createButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadEquipment() {
        List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
        for (Equipment equipment : equipmentList) {
            equipmentComboBox.addItem(equipment);
        }
    }

    private void createOrder() {
        Equipment equipment = (Equipment) equipmentComboBox.getSelectedItem();
        if (equipment != null) {
            EquipmentOrder order = new EquipmentOrder(userId, equipment.getId(), "новый");
            orderDAO.createOrder(order);
            JOptionPane.showMessageDialog(this, "Заказ создан");
            dispose();
        }
    }
}
