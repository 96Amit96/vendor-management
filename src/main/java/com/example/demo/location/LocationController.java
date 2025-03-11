package com.example.demo.location;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.City.City;
import com.example.demo.Country.Country;
import com.example.demo.State.State;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "http://localhost:5173") 
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/countries")
    public List<Country> getCountries() {
        return locationService.getAllCountries();
    }

    @GetMapping("/states/{countryId}")
    public List<State> getStatesByCountry(@PathVariable Long countryId) {
        return locationService.getStatesByCountry(countryId);
    }

    @GetMapping("/cities/{stateId}")
    public List<City> getCitiesByState(@PathVariable Long stateId) {
        return locationService.getCitiesByState(stateId);
    }
}