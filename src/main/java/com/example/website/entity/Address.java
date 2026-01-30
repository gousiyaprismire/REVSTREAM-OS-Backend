package com.example.website.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buildingNameFloor;
    private String streetNameLaneRoadNo;
    private String cityTown;
    private String district;
    private String pinCode;
    private String country;

    //  link with User table
    @OneToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private User user;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBuildingNameFloor() { return buildingNameFloor; }
    public void setBuildingNameFloor(String buildingNameFloor) { this.buildingNameFloor = buildingNameFloor; }

    public String getStreetNameLaneRoadNo() { return streetNameLaneRoadNo; }
    public void setStreetNameLaneRoadNo(String streetNameLaneRoadNo) { this.streetNameLaneRoadNo = streetNameLaneRoadNo; }

    public String getCityTown() { return cityTown; }
    public void setCityTown(String cityTown) { this.cityTown = cityTown; }

    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public User getRegistration() { return user; }
    public void setRegistration(User user) { this.user = user; }
}
