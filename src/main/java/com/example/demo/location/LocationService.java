package com.example.demo.location;
import org.springframework.stereotype.Service;

import com.example.demo.City.City;
import com.example.demo.City.CityRepository;
import com.example.demo.Country.Country;
import com.example.demo.Country.CountryRepository;
import com.example.demo.State.State;
import com.example.demo.State.StateRepository;

import java.util.List;

@Service
public class LocationService {
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    public LocationService(CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    public List<Country> getAllCountries() {
        System.out.println("===============countries++++++++++++++++++");
        return countryRepository.findAll();
    }

    public List<State> getStatesByCountry(Long countryId) {
        return stateRepository.findByCountryId(countryId);
    }

    public List<City> getCitiesByState(Long stateId) {
        return cityRepository.findByStateId(stateId);
    }
}
