package com.pharmacymanager.pharmacy.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pharmacy")
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pharmacy")
    private List<Shipment> shipmentList;

    public  Pharmacy(){}
    public Pharmacy(Long id, String address, String phone) {
        this.id = id;
        this.address = address;
        this.phone = phone;
    }



    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", shipmentList=" + shipmentList +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Shipment> getShipmentList() {
        return shipmentList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
