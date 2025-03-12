package com.example.demo.vendor;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "vendors")
public class VendorWrapper {
    private List<Vendor> vendors;

    public VendorWrapper() {}

    public VendorWrapper(List<Vendor> vendors) {
        this.vendors = vendors;
    }

    @XmlElement(name = "vendor")
    public List<Vendor> getVendors() {
        return vendors;
    }

    public void setVendors(List<Vendor> vendors) {
        this.vendors = vendors;
    }
}

