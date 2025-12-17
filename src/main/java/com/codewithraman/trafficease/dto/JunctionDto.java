package com.codewithraman.trafficease.dto;

public class JunctionDto {
    private Long id;
    private String name;
    private int nsSignal;
    private int ewSignal;
    private boolean congestionAlert;

    public JunctionDto(){}
    public JunctionDto(Long id, String name, int nsSignal, int ewSignal, boolean congestionAlert){
        this.id = id; this.name = name; this.nsSignal = nsSignal; this.ewSignal = ewSignal; this.congestionAlert = congestionAlert;
    }
    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}
    public String getName(){return name;}
    public void setName(String name){this.name = name;}
    public int getNsSignal(){return nsSignal;}
    public void setNsSignal(int nsSignal){this.nsSignal = nsSignal;}
    public int getEwSignal(){return ewSignal;}
    public void setEwSignal(int ewSignal){this.ewSignal = ewSignal;}
    public boolean isCongestionAlert(){return congestionAlert;}
    public void setCongestionAlert(boolean congestionAlert){this.congestionAlert = congestionAlert;}
}
