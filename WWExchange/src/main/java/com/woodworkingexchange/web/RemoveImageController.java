package com.woodworkingexchange.web;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.woodworkingexchange.repositories.ListingRepository;

@RequestMapping("/removeImage")
@Controller
public class RemoveImageController
{
  Logger log = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private ListingRepository listingDao;
  
  @RequestMapping(value="/{listingInfo}", method=RequestMethod.GET)
  public String handleImageRemove(@PathVariable String listingInfo, ModelMap model, HttpServletRequest request) throws SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, IllegalAccessException, InvocationTargetException
  {
    String[] info = getListingIdAndImageNum(listingInfo);
    Long listingId = Long.parseLong(info[0]);
    Integer imageId = Integer.parseInt(info[1]);
    
    if (imageId.equals(0))
      listingDao.deleteListingImage1ById(listingId);
    else if (imageId.equals(1))
      listingDao.deleteListingImage2ById(listingId);
    else if (imageId.equals(2))
      listingDao.deleteListingImage3ById(listingId);
    else if (imageId.equals(3))
      listingDao.deleteListingImage4ById(listingId);
    
    return "redirect:/listings/add_listing?id="+listingId;
  }
  
  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllExceptions(Exception e,
      HttpServletRequest request)
  {
    ModelAndView model = new ModelAndView("error");
    model.addObject("error", e);
    log.error("Error in ListingController", e);
    return model;
  }
  
  private String[] getListingIdAndImageNum (String listingInfo)
  {
    if (listingInfo == null || listingInfo.indexOf("_") < 0)
    {
      log.info("Received malformed listingInfo: " + listingInfo);
      return null;
    }
    return listingInfo.split("_");
  }
}
