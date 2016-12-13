package com.woodworkingexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.woodworkingexchange.jobs.NotifyUserOfListingRenewal;


@Service
public class ScheduledServiceJob
{
  @Autowired
  @Qualifier("notifyUserOfListingRenewal")
  private NotifyUserOfListingRenewal listingRenewal;
  
  
  @Scheduled(cron="0 55 9 * * *")
  public void doDailyJobs() 
  {
    listingRenewal.remindOfRenewal();
  }
  
}
