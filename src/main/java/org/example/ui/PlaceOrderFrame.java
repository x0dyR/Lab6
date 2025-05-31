package org.example.ui;

import org.example.dao.EquipmentDAO;
import org.example.dao.EquipmentOrderDAO;
import org.example.model.Equipment;
import org.example.model.EquipmentOrder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PlaceOrderFrame extends JFrame {
    private final EquipmentDAO equipmentDAO = new EquipmentDAO();
    private final EquipmentOrderDAO orderDAO = new EquipmentOrderDAO();
    private final int userId;

    public PlaceOrderFrame(int userId) {
        this.userId = userId;

        setTitle("Создать заказ на оборудование");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JComboBox<Equipment> equipmentComboBox = new JComboBox<>();
        loadEquipment(equipmentComboBox);

        JButton createOrderButton = new JButton("Создать заказ");
        createOrderButton.addActionListener(e -> {
            Equipment selectedEquipment = (Equipment) equipmentComboBox.getSelectedItem();
            if (selectedEquipment != null) {
                EquipmentOrder order = new EquipmentOrder(userId, selectedEquipment.getId(), "создан");
                orderDAO.createOrder(order);
                JOptionPane.showMessageDialog(this, "Заказ успешно создан");
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("Оборудование:"));
        panel.add(equipmentComboBox);

        add(panel, BorderLayout.CENTER);
        add(createOrderButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadEquipment(JComboBox<Equipment> comboBox) {
        List<Equipment> equipmentList = equipmentDAO.getAllEquipment();
        for (Equipment equipment : equipmentList) {
            comboBox.addItem(equipment);
        }
    }
}
