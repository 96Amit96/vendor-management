package com.example.demo.vendor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.opencsv.CSVReader;
import java.util.ArrayList;
import org.springframework.web.multipart.MultipartFile;
import jakarta.xml.bind.Unmarshaller;

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



    // ✅ Process CSV File
    public void processCSV(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                List<String[]> records = csvReader.readAll();
                List<Vendor> vendors = new ArrayList<>();

                for (String[] record : records) {
                    Vendor vendor = new Vendor();
                    vendor.setVendorNumber(record[0]);
                    vendor.setCompany(record[1]);
                    vendor.setFirstName(record[2]);
                    vendor.setLastName(record[3]);
                    vendor.setSiteAddress(record[4]);
                    vendor.setVendorType(record[5]);
                    vendor.setCategory(record[6]);
                    vendor.setVendorCode(record[7]);
                    vendor.setAddress1(record[8]);
                    vendor.setAddress2(record[9]);
                    vendor.setCity(record[10]);
                    vendor.setState(record[11]);
                    vendor.setPostalCode(record[12]);
                    vendor.setCountry(record[13]);
                    vendor.setContactVia(record[14]);
                    vendor.setPhone1(record[15]);
                    vendor.setPhone2(record[16]);
                    vendor.setFax(record[17]);
                    vendor.setEmail(record[18]);

                    vendors.add(vendor);
                }
                vendorRepo.saveAll(vendors);
            }
        }
    }



    public void writeVendorsToExcel(HttpServletResponse response) throws IOException {
        List<Vendor> vendors = vendorRepo.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Vendors");

        // Header row
        Row headerRow = sheet.createRow(0);
        String[] headers = { "Vendor Number", "Company", "First Name", "Last Name", "Email", "Phone", "Country" };
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Data rows
        int rowNum = 1;
        for (Vendor vendor : vendors) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(vendor.getVendorNumber());
            row.createCell(1).setCellValue(vendor.getCompany());
            row.createCell(2).setCellValue(vendor.getFirstName());
            row.createCell(3).setCellValue(vendor.getLastName());
            row.createCell(4).setCellValue(vendor.getEmail());
            row.createCell(5).setCellValue(vendor.getPhone1());
            row.createCell(6).setCellValue(vendor.getCountry());
        }

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }




    // ✅ Process XML File
    public void processXML(MultipartFile file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(VendorListWrapper.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        VendorListWrapper vendorList = (VendorListWrapper) unmarshaller.unmarshal(file.getInputStream());
    
        // Ensure IDs are null so Hibernate does not try to update existing vendors
        List<Vendor> vendors = vendorList.getVendors();
        for (Vendor vendor : vendors) {
            vendor.setId(null); // Prevents conflict with existing rows
        }
    
        vendorRepo.saveAll(vendors);
    }

    // ✅ Process Excel File
    public void processExcel(MultipartFile file) throws Exception {
        List<Vendor> vendors = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row
                Vendor vendor = new Vendor();
                vendor.setVendorNumber(row.getCell(0).getStringCellValue());
                vendor.setCompany(row.getCell(1).getStringCellValue());
                vendor.setFirstName(row.getCell(2).getStringCellValue());
                vendor.setLastName(row.getCell(3).getStringCellValue());
                vendor.setSiteAddress(row.getCell(4).getStringCellValue());
                vendor.setVendorType(row.getCell(5).getStringCellValue());
                vendor.setCategory(row.getCell(6).getStringCellValue());
                vendor.setVendorCode(row.getCell(7).getStringCellValue());
                vendor.setAddress1(row.getCell(8).getStringCellValue());
                vendor.setAddress2(row.getCell(9).getStringCellValue());
                vendor.setCity(row.getCell(10).getStringCellValue());
                vendor.setState(row.getCell(11).getStringCellValue());
                vendor.setPostalCode(row.getCell(12).getStringCellValue());
                vendor.setCountry(row.getCell(13).getStringCellValue());
                vendor.setContactVia(row.getCell(14).getStringCellValue());
                vendor.setPhone1(row.getCell(15).getStringCellValue());
                vendor.setPhone2(row.getCell(16).getStringCellValue());
                vendor.setFax(row.getCell(17).getStringCellValue());
                vendor.setEmail(row.getCell(18).getStringCellValue());

                vendors.add(vendor);
            }
        }
        vendorRepo.saveAll(vendors);
    }


}

