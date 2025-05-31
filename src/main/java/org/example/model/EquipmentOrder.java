package org.example.model;

public class EquipmentOrder {
    private int id;
    private int userId;
    private int equipmentId;
    private String status;

    public EquipmentOrder(int id, int userId, int equipmentId, String status) {
        this.id = id;
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.status = status;
    }

    public EquipmentOrder(int userId, int equipmentId, String status) {
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.status = status;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getEquipmentId() { return equipmentId; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
