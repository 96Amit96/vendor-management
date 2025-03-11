package com.example.demo.vendor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends JpaRepository<Vendor,Long> {

    List<Vendor> findByCompanyContainingOrVendorNumberContaining(String company, String vendorNumber); 
}
