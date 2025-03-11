package com.example.demo.State;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.example.demo.City.City;
import com.example.demo.Country.Country;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "state")
    @JsonIgnore
    private List<City> cities;
}

