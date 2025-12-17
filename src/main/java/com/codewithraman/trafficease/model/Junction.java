package com.codewithraman.trafficease.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Junction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int nsSignal = 30;
    private int ewSignal = 30;
    private boolean congestionAlert = false;

    @Column(name = "last_alert_time") // optional: maps explicitly in DB
    private LocalDateTime lastAlertTime;

    public Junction() {}

    public Junction(String name) {
        this.name = name;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getNsSignal() {
        return nsSignal;
    }
    public void setNsSignal(int nsSignal) {
        this.nsSignal = nsSignal;
    }

    public int getEwSignal() {
        return ewSignal;
    }
    public void setEwSignal(int ewSignal) {
        this.ewSignal = ewSignal;
    }

    public boolean isCongestionAlert() {
        return congestionAlert;
    }
    public void setCongestionAlert(boolean congestionAlert) {
        this.congestionAlert = congestionAlert;
    }

    public LocalDateTime getLastAlertTime() {
        return lastAlertTime;
    }
    public void setLastAlertTime(LocalDateTime lastAlertTime) {
        this.lastAlertTime = lastAlertTime;
    }

    @Override
    public String toString() {
        return "Junction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nsSignal=" + nsSignal +
                ", ewSignal=" + ewSignal +
                ", congestionAlert=" + congestionAlert +
                ", lastAlertTime=" + lastAlertTime +
                '}';
    }
}
