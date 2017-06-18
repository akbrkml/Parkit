package com.example.isyandra.parkit.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Arief11 on 5/13/2017.
 */

public class Data {

    private String lokasi, nama, key;
    private Integer availableSlot;
    private Double latitude, longitude;
    private Long sensor1, sensor2, slot;

    public Data(String lokasi, String nama, Integer availableSlot, Double latitude, Double longitude, Long sensor1, Long sensor2, Long slot) {
        this.lokasi = lokasi;
        this.nama = nama;
        this.availableSlot = availableSlot;
        this.latitude = latitude;
        this.longitude = longitude;
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.slot = slot;
    }

    public Data() {
    }

    @Exclude
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public Integer getAvailableSlot() {
        return availableSlot;
    }

    public void setAvailableSlot(Integer availableSlot) {
        this.availableSlot = availableSlot;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getSensor1() {
        return sensor1;
    }

    public void setSensor1(Long sensor1) {
        this.sensor1 = sensor1;
    }

    public Long getSensor2() {
        return sensor2;
    }

    public void setSensor2(Long sensor2) {
        this.sensor2 = sensor2;
    }

    public Long getSlot() {
        return slot;
    }

    public void setSlot(Long slot) {
        this.slot = slot;
    }

    public void setValues(Data newData){
        this.lokasi = newData.lokasi;
        this.nama = newData.nama;
        this.availableSlot = newData.availableSlot;
        this.latitude = newData.latitude;
        this.longitude = newData.longitude;
        this.sensor1 = newData.sensor1;
        this.sensor2 = newData.sensor2;
        this.slot = newData.slot;
    }
}