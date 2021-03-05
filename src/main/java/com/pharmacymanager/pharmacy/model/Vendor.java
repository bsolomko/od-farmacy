package com.pharmacymanager.pharmacy.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendor")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private List<Shipment> shipmentList;

    
    public Vendor(){ }

    public Vendor(Long id, String title, String address, String phone) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.phone = phone;
    }
    public List<Shipment> getShipmentList() {
        return shipmentList;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
