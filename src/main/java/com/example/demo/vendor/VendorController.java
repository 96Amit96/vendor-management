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

import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/download-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=vendors.xlsx");

        vendorService.writeVendorsToExcel(response);
    }
    
    @PostMapping("/upload")
public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
        // Get file type and validate
        String fileType = Objects.requireNonNull(file.getContentType(), "File type cannot be null");
        System.out.println("Detected file type: " + fileType);

        // Process based on file type
        if (fileType.contains("csv")) {
            vendorService.processCSV(file);
        } else if (fileType.contains("xml")) {
            vendorService.processXML(file);
        } else if (fileType.contains("spreadsheetml")) {
            vendorService.processExcel(file);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unsupported file type: " + fileType);
        }

        return ResponseEntity.ok("File uploaded and processed successfully!");
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("File processing failed: " + e.getMessage());
    }
}


}
