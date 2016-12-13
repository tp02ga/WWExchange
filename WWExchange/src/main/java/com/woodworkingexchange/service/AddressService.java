package com.woodworkingexchange.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AddressService
{
  final List<String> provinces = new ArrayList<String>();
  final List<String> countries = new ArrayList<String>();
  
  public List<String> getCountries()
  {
    if (countries.size() > 0)
      return countries;
    else
    {
      countries.add("");
      countries.add("USA");
      countries.add("Canada");
      return countries;
    }
  }
  
  public List<String> getProvinces()
  {
    if (provinces.size() > 0)
      return provinces;
    else
    {
      provinces.add("");
      provinces.add("Alabama");provinces.add("Alaska");provinces.add("Arizona");provinces.add("Arkansas");provinces.add("California");provinces.add("Colorado");provinces.add("Connecticut");provinces.add("Delaware");provinces.add("District of Columbia");provinces.add("Florida");provinces.add("Georgia");provinces.add("Hawaii");provinces.add("Idaho");provinces.add("Illinois");provinces.add("Indiana");provinces.add("Iowa");provinces.add("Kansas");provinces.add("Kentucky");provinces.add("Louisiana");
      provinces.add("Maine");provinces.add("Maryland");provinces.add("Massachusetts");provinces.add("Michigan");provinces.add("Minnesota");provinces.add("Mississippi");provinces.add("Missouri");provinces.add("Montana");provinces.add("Nebraska");provinces.add("Nevada");provinces.add("New Hampshire");provinces.add("New Jersey");provinces.add("New Mexico");provinces.add("New York");provinces.add("North Carolina");provinces.add("North Dakota");provinces.add("Ohio");provinces.add("Oklahoma");
      provinces.add("Oregon");provinces.add("Pennsylvania");provinces.add("Rhode Island");provinces.add("South Carolina");provinces.add("South Dakota");provinces.add("Tennessee");provinces.add("Texas");provinces.add("Utah");provinces.add("Vermont");provinces.add("Virginia");provinces.add("Virgin Islands");provinces.add("Washington");provinces.add("West Virginia");provinces.add("Wisconsin");provinces.add("Wyoming");provinces.add("Alberta");provinces.add("British Columbia");provinces.add("Manitoba");
      provinces.add("New Brunswick");provinces.add("Newfoundland");provinces.add("Northwest Territories");provinces.add("Nova Scotia");provinces.add("Nunavut");provinces.add("Ontario");provinces.add("Prince Edward Island");provinces.add("Quebec");provinces.add("Saskatchewan");provinces.add("Yukon");
      return provinces;
    }
  }
}
