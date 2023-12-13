package com.ocrooms.safetynet.entities;

public class Firestation {

    @NotNull
    @Length(min=1, max=20)
    private String address;
    private int station;

    public Firestation() {
    }

    public Firestation(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public void trimProperties() {
        this.address = this.address.trim();
    }
}
