package com.example.demo.vendor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vendors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;
private String vendorNumber;
private String company;
private String firstName;
private String lastName;
private String siteAddress;
private String vendorType;
private String category;
private String vendorCode;
private String address1;
private String address2;
private String city;
private String state;
private String postalCode;
private String country;
private String contactVia;
private String phone1;
private String phone2;
private String fax;
private String email;

}
