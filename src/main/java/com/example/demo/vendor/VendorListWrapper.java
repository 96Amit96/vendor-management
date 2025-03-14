package com.example.demo.vendor;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vendors") // Root element of XML
@XmlAccessorType(XmlAccessType.FIELD) // Use field-based access
public class VendorListWrapper {

    @XmlElement(name = "vendor") // Each vendor element
    private List<Vendor> vendors;

    // ✅ Default constructor (important for JAXB)
    public VendorListWrapper() {}

    // ✅ Constructor for initialization
    public VendorListWrapper(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    // ✅ Getter
    public List<Vendor> getVendors() {
        return vendors;
    }

    // ✅ Setter
    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}
