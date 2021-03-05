package com.pharmacymanager.pharmacy.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shipment")
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "medicine_title")
    private String medicineTitle;


    @Column(name = "shipment_date")
    private LocalDate shipmentDate;


    @Column(name = "expiration_date")
    private LocalDate expirationDate;



    @Column(name = "medicine_quantity")
    private Long medicineQuantity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    public Shipment(){}
    public Shipment(Long id, String medicineTitle, LocalDate shipmentDate, LocalDate expirationDate, Long medicineQuantity) {
        this.id = id;
        this.medicineTitle = medicineTitle;
        this.shipmentDate = shipmentDate;
        this.expirationDate = expirationDate;
        this.medicineQuantity = medicineQuantity;

    }


    public LocalDate getShipmentDate() {
        return shipmentDate;
    }

    public void setShipmentDate(LocalDate shipmentDate) {
        this.shipmentDate = shipmentDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicineTitle() {
        return medicineTitle;
    }

    public void setMedicineTitle(String medicineTitle) {
        this.medicineTitle = medicineTitle;
    }

    public Long getMedicineQuantity() {
        return medicineQuantity;
    }

    public void setMedicineQuantity(Long medicineQuantity) {
        this.medicineQuantity = medicineQuantity;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }
}
