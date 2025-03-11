package com.example.demo.Country;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import com.example.demo.State.State;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;
}

