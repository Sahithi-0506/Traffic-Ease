 package com.codewithraman.trafficease.dto;

import java.time.LocalDateTime;

public class TrafficSnapshotDto {
    private int north;
    private int east;
    private int south;
    private int west;
    private int total;
    private LocalDateTime timestamp;

    public TrafficSnapshotDto(){}
    public TrafficSnapshotDto(int north,int east,int south,int west,int total,LocalDateTime ts){
        this.north=north;this.east=east;this.south=south;this.west=west;this.total=total;this.timestamp=ts;
    }
    // getters/setters
    public int getNorth(){return north;}
    public void setNorth(int north){this.north=north;}
    public int getEast(){return east;}
    public void setEast(int east){this.east=east;}
    public int getSouth(){return south;}
    public void setSouth(int south){this.south=south;}
    public int getWest(){return west;}
    public void setWest(int west){this.west=west;}
    public int getTotal(){return total;}
    public void setTotal(int total){this.total=total;}
    public LocalDateTime getTimestamp(){return timestamp;}
    public void setTimestamp(LocalDateTime timestamp){this.timestamp=timestamp;}
}
