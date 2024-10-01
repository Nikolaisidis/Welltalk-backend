package com.communicators.welltalk.dto;

public class NotificationsDTO {
    private int receiverId;
    private int serviceId;

    public NotificationsDTO() {
    }
    
    // appointment
    public NotificationsDTO(int receiverId, int serviceId) {
        this.receiverId = receiverId;
        this.serviceId = serviceId;
    }

    public int getReceiverId() {
        return receiverId;
    }
    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }


}
