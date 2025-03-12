package com.example.demo.vendor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepo;

    public Vendor createVendor(VendorDto vendorDto) {
        Vendor vendor = new Vendor();
        vendor.setVendorNumber(vendorDto.getVendorNumber());
        vendor.setCompany(vendorDto.getCompany());
        vendor.setFirstName(vendorDto.getFirstName());
        vendor.setLastName(vendorDto.getLastName());
        vendor.setSiteAddress(vendorDto.getSiteAddress());
        vendor.setVendorType(vendorDto.getVendorType());
        vendor.setCategory(vendorDto.getCategory());
        vendor.setVendorCode(vendorDto.getVendorCode());
        vendor.setAddress1(vendorDto.getAddress1());
        vendor.setAddress2(vendorDto.getAddress2());
        vendor.setCity(vendorDto.getCity()); 
        vendor.setState(vendorDto.getState());
        vendor.setPostalCode(vendorDto.getPostalCode());
        vendor.setCountry(vendorDto.getCountry());
        vendor.setContactVia(vendorDto.getContactVia());
        vendor.setPhone1(vendorDto.getPhone1());
        vendor.setPhone2(vendorDto.getPhone2());
        vendor.setFax(vendorDto.getFax());
        vendor.setEmail(vendorDto.getEmail());

        return vendorRepo.save(vendor);

    }

    public VendorResponseDto getAllVendors() {
    List<Vendor> vendors = vendorRepo.findAll(); 
    return new VendorResponseDto("Vendors retrieved successfully", vendors); 
}

public List<Vendor> searchVendors(String query) {
    return vendorRepo.findByCompanyContainingOrVendorNumberContaining(query, query);
    
}

public boolean deleteVendor(Long id) {
    if (vendorRepo.existsById(id)) { // Check if vendor exists before deleting
        vendorRepo.deleteById(id);
        System.out.println("DELETED");
        return true; // Successfully deleted
    }
    return false; // Vendor not found
}

public void deleteVendors(List<Long> vendorIds) {
    vendorRepo.deleteAllById(vendorIds);
}


    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Optional<Vendor> existingVendorOpt = vendorRepo.findById(id);

        if (existingVendorOpt.isPresent()) {
            Vendor existingVendor = existingVendorOpt.get();

            // 🔹 Update vendor fields
            existingVendor.setVendorNumber(updatedVendor.getVendorNumber());
            existingVendor.setCompany(updatedVendor.getCompany());
            existingVendor.setFirstName(updatedVendor.getFirstName());
            existingVendor.setLastName(updatedVendor.getLastName());
            existingVendor.setVendorType(updatedVendor.getVendorType());
            existingVendor.setVendorCode(updatedVendor.getVendorCode());
            existingVendor.setSiteAddress(updatedVendor.getSiteAddress());
            existingVendor.setCategory(updatedVendor.getCategory());
            existingVendor.setAddress1(updatedVendor.getAddress1());
            existingVendor.setAddress2(updatedVendor.getAddress2());
            existingVendor.setCity(updatedVendor.getCity());
            existingVendor.setState(updatedVendor.getState());
            existingVendor.setPostalCode(updatedVendor.getPostalCode());
            existingVendor.setCountry(updatedVendor.getCountry());
            existingVendor.setContactVia(updatedVendor.getContactVia());
            existingVendor.setPhone1(updatedVendor.getPhone1());
            existingVendor.setPhone2(updatedVendor.getPhone2());
            existingVendor.setEmail(updatedVendor.getEmail());
            existingVendor.setFax(updatedVendor.getFax());

            // 🔹 Save updated vendor
            return vendorRepo.save(existingVendor);
        } else {
            throw new RuntimeException("Vendor not found with id: " + id);
        }
    }

public void writeVendorsToCsv(HttpServletResponse response) throws IOException {
    List<Vendor> vendors = vendorRepo.findAll();
    try( PrintWriter writer = response.getWriter() ){
        writer.println("Id,Vendor Number,First Name,Last Name,Company, Site Address,Vendor Type,Category,Vendor Code,Address1,Address2,City,State,Country,Postal Code,Contact Via,Phone1,Phone2,Fax,Email");
        
        for(Vendor vendor : vendors) {
            writer.println(
                vendor.getId() + "," +
                vendor.getVendorNumber() + "," +
                vendor.getFirstName() + "," +
                vendor.getLastName() + "," +
                vendor.getCompany() + "," +
                vendor.getSiteAddress() + "," +
                vendor.getSiteAddress() + "," +
                vendor.getVendorType() + "," +
                vendor.getCategory() + "," +
                vendor.getVendorCode() + "," +
                vendor.getAddress1() + "," +
                vendor.getAddress2() + "," +
                vendor.getCity() + "," +
                vendor.getState() + "," +
                vendor.getCountry() + "," +
                vendor.getPostalCode() + "," +
                vendor.getContactVia() + "," +
                vendor.getPhone1() + "," +
                vendor.getPhone2() + "," +
                vendor.getFax() + "," +
                vendor.getEmail() + "," 
            );
        }
    }
}


    public void exportVendorsToXML(HttpServletResponse response) throws IOException, JAXBException {
        response.setContentType("application/xml");
        response.setHeader("Content-Disposition", "attachment; filename=vendors.xml");

        List<Vendor> vendors = vendorRepo.findAll();
        VendorWrapper vendorWrapper = new VendorWrapper(vendors);

        JAXBContext jaxbContext = JAXBContext.newInstance(VendorWrapper.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        OutputStream outputStream = response.getOutputStream();
        marshaller.marshal(vendorWrapper, outputStream);
    }



}

