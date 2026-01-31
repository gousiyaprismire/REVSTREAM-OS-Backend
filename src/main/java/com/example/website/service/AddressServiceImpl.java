package com.example.website.service;

import com.example.website.entity.User;
import org.springframework.stereotype.Service;

import com.example.website.dto.AddressRequest;
import com.example.website.entity.Address;
import com.example.website.repository.AddressRepository;
import com.example.website.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository,
                              UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String saveAddress(Long registrationId, AddressRequest request) {

        User reg = userRepository.findById(registrationId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with ID: " + registrationId));

       
        if (addressRepository.existsByUserId(registrationId)) {
            return "Address already added for this registration";
        }

        Address address = new Address();
        address.setBuildingNameFloor(request.getBuildingNameFloor());
        address.setStreetNameLaneRoadNo(request.getStreetNameLaneRoadNo());
        address.setCityTown(request.getCityTown());
        address.setDistrict(request.getDistrict());
        address.setPinCode(request.getPinCode());
        address.setCountry(request.getCountry());
        address.setUser(reg);

        addressRepository.save(address);

        return "Address saved successfully";
    }
}
