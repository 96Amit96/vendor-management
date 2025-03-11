package com.example.demo.vendor;



import java.util.List;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class VendorResponseDto {
    private String message;
    private List<Vendor> vendors;
}
