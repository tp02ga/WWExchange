package com.woodworkingexchange.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woodworkingexchange.domain.Listing;
import com.woodworkingexchange.domain.Users;
import com.woodworkingexchange.repositories.ListingRepository;
import com.woodworkingexchange.service.MailService;

@Component("notifyUserOfListingRenewal")
public class NotifyUserOfListingRenewal
{
  ListingRepository listingDao;
  MailService mailService;
  
  public void remindOfRenewal()
  {
    Calendar cal = Calendar.getInstance();
    cal.add(Calendar.DATE, -45);
    List<Listing> listingsByDate = listingDao.getlistingForRenewal(cal.getTime());
    
    for (Listing listing : listingsByDate)
    {
      listing.setRenewalReminderSent(true);
    }
    
    listingDao.save(listingsByDate);
    
    Map<Users, List<Listing>> usersToEmail = new HashMap<Users, List<Listing>>();
    
    populateUsersToEmailMap(listingsByDate, usersToEmail);
    
    mailService.sendRenewalEmail(usersToEmail);
  }

  private void populateUsersToEmailMap(List<Listing> listingsByDate,
      Map<Users, List<Listing>> usersToEmail)
  {
    for (Listing listing : listingsByDate)
    {
      if (usersToEmail.containsKey(listing.getUsers()))
      {
        usersToEmail.get(listing.getUsers()).add(listing);
      }
      else
      {
        ArrayList<Listing> usersListing = new ArrayList<Listing>();
        usersListing.add(listing);
        usersToEmail.put(listing.getUsers(), usersListing);
      }
    }
  }

  @Autowired
  public void setListingDao(ListingRepository listingDao)
  {
    this.listingDao = listingDao;
  }
  
  @Autowired
  public void setMailService(MailService mailService)
  {
    this.mailService = mailService;
  }
  
  
  
}
