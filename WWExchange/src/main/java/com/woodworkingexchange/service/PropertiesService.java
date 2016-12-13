package com.woodworkingexchange.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Service;

@Service
public class PropertiesService
{
  static Properties woodworkingProps = new Properties();
  
  public static Properties getWoodworkingProperties ()
  {
    // Read properties file.
    File file = new File("WoodworkingExchange.properties");
    try 
    {
      file.createNewFile();
      woodworkingProps.load(new FileInputStream(file));
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    
    return woodworkingProps;
  }
}
