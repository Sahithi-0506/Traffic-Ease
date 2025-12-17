package com.codewithraman.trafficease.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TrafficData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "junction_id")
    private Junction junction;

    private int northLane;
    private int eastLane;
    private int southLane;
    private int westLane;

    private int totalQueue;

    private LocalDateTime timestamp;

    // This method will be called automatically before the entity is persisted
    @PrePersist
    public void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    // Getters & setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Junction getJunction() {
        return junction;
    }
    public void setJunction(Junction junction) {
        this.junction = junction;
    }

    public int getNorthLane() {
        return northLane;
    }
    public void setNorthLane(int northLane) {
        this.northLane = northLane;
    }

    public int getEastLane() {
        return eastLane;
    }
    public void setEastLane(int eastLane) {
        this.eastLane = eastLane;
    }

    public int getSouthLane() {
        return southLane;
    }
    public void setSouthLane(int southLane) {
        this.southLane = southLane;
    }

    public int getWestLane() {
        return westLane;
    }
    public void setWestLane(int westLane) {
        this.westLane = westLane;
    }

    public int getTotalQueue() {
        return totalQueue;
    }
    public void setTotalQueue(int totalQueue) {
        this.totalQueue = totalQueue;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
