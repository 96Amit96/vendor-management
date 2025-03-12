package com.example.demo.vendor;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/vendors")
@CrossOrigin(origins = "http://localhost:5173")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    public Vendor createVendor(@RequestBody VendorDto vendorDto) {
        return vendorService.createVendor(vendorDto);
    }

    @GetMapping
    public VendorResponseDto  getAllVendors() {
        return vendorService.getAllVendors();
    }

    // @GetMapping("/search")
    // public VendorResponseDto  searchVendors(@RequestParam("query") String query) {
    //     return vendorService.searchVendors(query);
    // }

    @GetMapping("/search")
     public List<Vendor>  searchVendors(@RequestParam("query") String query) {
        return vendorService.searchVendors(query);
    }
    

      @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(@PathVariable Long id, @RequestBody Vendor updatedVendor) {
        Vendor updated = vendorService.updateVendor(id, updatedVendor);
        System.out.println("VENDOR UPDATED");
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/bulk-delete")
    public ResponseEntity<String> deleteVendors(@RequestBody List<Long> vendorIds) {
        vendorService.deleteVendors(vendorIds);
        return ResponseEntity.ok("Vendors deleted successfully.");
    }

 @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        boolean isDeleted = vendorService.deleteVendor(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

     @GetMapping("/download-xml")
    public void downloadXML(HttpServletResponse response) throws IOException, JAXBException {
        vendorService.exportVendorsToXML(response);
    }

    @GetMapping("/download-csv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"vendors.csv\"");
        vendorService.writeVendorsToCsv(response);
    }
    
    
}
