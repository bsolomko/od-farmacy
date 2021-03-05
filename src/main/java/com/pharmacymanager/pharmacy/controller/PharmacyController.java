package com.pharmacymanager.pharmacy.controller;


import com.pharmacymanager.pharmacy.model.*;
import com.pharmacymanager.pharmacy.repository.PharmacyRepository;
import com.pharmacymanager.pharmacy.repository.ShipmentRepository;
import com.pharmacymanager.pharmacy.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Controller
public class PharmacyController {

    @Autowired
    private PharmacyRepository pharmacyRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;

    @GetMapping("/")
    public String mainMenu(){
        return "mainMenu";
    }

    @GetMapping("/pharmacies")
    public String getPharmacies(Model model){
        List<Pharmacy> pharmacyList = pharmacyRepository.findAll();
        model.addAttribute("pharmacies",pharmacyList);
        return "showPharmacies";
    }

    @GetMapping("/pharmacies/{id}")
    public String showPharmacy(@PathVariable("id") Long id, Model model,
                               @ModelAttribute("formID") FormForID formID){

        Pharmacy pharmacy = pharmacyRepository.getOne(id);
        model.addAttribute("pharmacy",pharmacy);
        List<Shipment> result = shipmentRepository.findAllByPharmacyId(id);
        model.addAttribute("result",result);
        List<Long> listIdVendor = vendorRepository.findAll()
                .stream()
                .map(Vendor::getId)
                .collect(Collectors.toList());

        model.addAttribute("listIdVendor",listIdVendor);
        return "showPharmacy";
}

    @GetMapping("/new/pharmacy")
    private String newPharmacy(@ModelAttribute("newPharmacy") Pharmacy pharmacy){
        return "newPharmacy";
    }

    @PostMapping("/pharmacies")
    public String createPharmacy(@ModelAttribute("newPharmacy") Pharmacy pharmacy){
        pharmacyRepository.save(pharmacy);
        return "redirect:/pharmacies";
    }

    @GetMapping("/vendors")
    public String showVendors(Model model){
        List<Vendor> vendorList = vendorRepository.findAll();
        model.addAttribute("vendors",vendorList);
        return "showVendors";
    }

    @GetMapping("/vendors/{id}")
    public String getVendor(@PathVariable("id") Long id ,Model model){
        Vendor vendor = new Vendor();
        vendor = vendorRepository.getOne(id);
        model.addAttribute("vendor",vendor);
        return "showVendor";

    }

    @GetMapping("/new/vendor")
    public String newVendor(@ModelAttribute("newVendor") Vendor vendor){
        return "newVendor";
    }

    @PostMapping("/vendors")
    public String createVendor(@ModelAttribute("newVendor") Vendor vendor){
        vendorRepository.save(vendor);
        return "redirect:/vendors";
    }
    @GetMapping("/vendors/{id}/edit")
    public String editVendor(Model model,@PathVariable("id") Long id){
        model.addAttribute("vendor",vendorRepository.getOne(id));
        return "editVendor";
    }

    @PutMapping ("/vendors/{id}")
    public String updateVendor(@ModelAttribute("vendor") Vendor vendor,@PathVariable Long id){

        Vendor vendorForUpdate = vendorRepository.getOne(id);
        vendorForUpdate.setAddress(vendor.getAddress());
        vendorForUpdate.setPhone(vendor.getPhone());
        vendorForUpdate.setTitle(vendor.getTitle());
        vendorRepository.save(vendorForUpdate);
        return "redirect:/vendors";
    }


    @GetMapping("/pharmacies/{id}/edit")
    public String editPharmacy(Model model,@PathVariable("id") Long id){

        model.addAttribute("pharmacy", pharmacyRepository.getOne(id));
        return "editPharmacy";
    }

    @PutMapping ("/pharmacies/{id}")
    public String updatePharmacy(@ModelAttribute("pharmacy") Pharmacy pharmacy,@PathVariable Long id){


      Pharmacy pharmacyForUpdate = pharmacyRepository.getOne(id);
      pharmacyForUpdate.setAddress(pharmacy.getAddress());
      pharmacyForUpdate.setPhone(pharmacy.getPhone());
      pharmacyForUpdate.setTitle(pharmacy.getTitle());
      pharmacyRepository.save(pharmacyForUpdate);
        return "redirect:/pharmacies";
    }

    @GetMapping("/pharmacies/{id}/newshipment")
    public String newShipment(@ModelAttribute("newShipment") Shipment shipment
            ,@PathVariable Long id,Model model,@ModelAttribute("newVendor") Vendor vendor){
        List<Long> listIdVendor = vendorRepository.findAll().stream().map(Vendor::getId).collect(Collectors.toList());
        model.addAttribute("listIdVendor",listIdVendor);
        model.addAttribute("pharmacy",pharmacyRepository.getOne(id));
        return "newShipment";
}


    @PostMapping("/add/shipment")
    public String addShipment(@ModelAttribute("newShipment") ShipmentModel shipment
            ,@ModelAttribute("newVendor") Vendor vendor){
        Shipment shipmentForSave = new Shipment();
        shipmentForSave.setMedicineTitle(shipment.getMedicineTitle());
        shipmentForSave.setExpirationDate(shipment.getExpirationDate());
        shipmentForSave.setShipmentDate(shipment.getShipmentDate());
        shipmentForSave.setMedicineQuantity(shipment.getMedicineQuantity());
        shipmentForSave.setVendor(shipment.getVendor());
        shipmentForSave.setPharmacy(shipment.getPharmacy());
        shipmentRepository.save(shipmentForSave);
        return "redirect:/pharmacies";
    }

    @Transactional
    @DeleteMapping("/pharmacies/{id}/delete")
    public String deletePharmacy(@PathVariable("id") Long id){
        shipmentRepository.deleteAllByPharmacyId(id);
        pharmacyRepository.deleteById(id);
        return "redirect:/pharmacies";
    }

    @DeleteMapping("/shipment/{idShipment}/delete")
    public String deleteShipment(@PathVariable("idShipment") Long idShipment){
        shipmentRepository.deleteById(idShipment);
        return "redirect:/pharmacies";
    }

    @GetMapping ("/shipment/{idShipment}/edit")
    public String showShipmentForEdit( @PathVariable("idShipment") Long idShipment
            ,Shipment shipment,Model model){
        shipment = shipmentRepository.getOne(idShipment);
        model.addAttribute("shipmentForEdit",shipment);
        return "editShipment";
    }

    @PutMapping("/shipment/{idShipment}/edit/update")
    public String shipmentEdit( @PathVariable("idShipment") Long idShipment,@ModelAttribute("shipmentForEdit")
                                Shipment shipment){

        Shipment shipmentForUpdate = shipmentRepository.getOne(idShipment);
        shipmentForUpdate.setMedicineTitle(shipment.getMedicineTitle());
        shipmentForUpdate.setMedicineQuantity(shipment.getMedicineQuantity());
        shipmentForUpdate.setShipmentDate(shipment.getShipmentDate());
        shipmentForUpdate.setExpirationDate(shipment.getExpirationDate());

        shipmentRepository.save(shipmentForUpdate);
        return "redirect:/pharmacies";

    }
    @Transactional
    @DeleteMapping("/vendors/{idVendor}/delete")
    public String deleteVendor(@PathVariable("idVendor") Long idVendor){
        shipmentRepository.deleteAllByVendorId(idVendor);
        vendorRepository.deleteById(idVendor);
        return "redirect:/vendors";
    }

    @GetMapping("/shipments/by/title")
    public String getAllShipmentByTitle(Model model,
                                        @ModelAttribute("formForTitle") FormForFind formForTitle) {

       return "showCountShipments";
    }
    @GetMapping("/show/shipments/by/title")
    public  String showAllShipmentByTitle(Model model,
                                          @ModelAttribute("formForTitle") FormForFind formForTitle){

     long countTitle   =  shipmentRepository.findAll()
                .stream()
                .map(Shipment::getMedicineTitle)
                .filter(s -> s!=null && s.equals(formForTitle.getValue()))
                .count();

        model.addAttribute("resultCount",countTitle);
        return "showCountShipmentResult";
    }

    @GetMapping("/show/shipments/expire")
    public String findShipmentsExpireResult(Model model,
                                          @ModelAttribute("formForExpireShip") FormForFind formForExpireShip) {
        LocalDate currentDate = LocalDate.now();

         List<Shipment> result = shipmentRepository.findAll().stream().filter(shipment -> shipment!=null
                 && shipment.getExpirationDate().isAfter(currentDate.plusDays(-7))
                 && shipment.getExpirationDate().isBefore(currentDate))
                 .collect(Collectors.toList());
         model.addAttribute("result",result);
         System.out.println(result.size());
        return "shipmentsExpireResult";
    }

    @GetMapping("/countshipmentsbyvendor")
    public String getCountShipmentsByVendor(Model model,
                                            @ModelAttribute("formID") FormForID formID){

        System.out.println(formID.getIdPharmacy());
        System.out.println(formID.getIdVendor());
        long resultCount =  shipmentRepository.findAll()
                .stream()
                .filter(shipment -> shipment!= null
                && shipment.getPharmacy().getId() == formID.getIdPharmacy()
                && shipment.getVendor().getId() == formID.getIdVendor())
                .count();
        model.addAttribute("resultCount",resultCount);

        return "countShipmentByVendorView";
    }
}

