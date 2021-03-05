package com.pharmacymanager.pharmacy.repository;

import com.pharmacymanager.pharmacy.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface ShipmentRepository  extends JpaRepository<Shipment,Long> {

    List<Shipment> findAllByPharmacyId(Long pharmacyId);
    void deleteAllByPharmacyId(Long pharmacyId);
    void deleteAllByVendorId(Long vendorId);

}
