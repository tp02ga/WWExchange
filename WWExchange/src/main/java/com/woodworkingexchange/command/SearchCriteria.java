package com.woodworkingexchange.command;

public class SearchCriteria
{
  String manufacturer;
  String[] condition;
  String region;
  String keyword;
  String machineType;
  
  public String getKeyword()
  {
    return keyword;
  }
  public void setKeyword(String keyword)
  {
    this.keyword = keyword;
  }
  public String getManufacturer()
  {
    return manufacturer;
  }
  public void setManufacturer(String manufacturer)
  {
    this.manufacturer = manufacturer;
  }
  public String[] getCondition()
  {
    return condition;
  }
  public void setCondition(String[] condition)
  {
    this.condition = condition;
  }
  public String getRegion()
  {
    return region;
  }
  public void setRegion(String region)
  {
    this.region = region;
  }
  public String getMachineType()
  {
    return machineType;
  }
  public void setMachineType(String machineType)
  {
    this.machineType = machineType;
  }
}
