package com.woodworkingexchange.command;

import java.util.List;

import com.woodworkingexchange.domain.FeedbackSurvey;
import com.woodworkingexchange.domain.Listing;

public class MyListingsCommand
{
  private List<Listing> listings;
  private String action;
  private FeedbackSurvey feedbackSurvey;

  public MyListingsCommand(List<Listing> listings, String action,
      FeedbackSurvey feedbackSurvey)
  {
    this.setListings(listings);
    this.setAction(action);
    this.setFeedbackSurvey(feedbackSurvey);
  }

  public MyListingsCommand()
  {

  }

  public FeedbackSurvey getFeedbackSurvey()
  {
    return feedbackSurvey;
  }

  public void setFeedbackSurvey(FeedbackSurvey feedbackSurvey)
  {
    this.feedbackSurvey = feedbackSurvey;
  }

  public List<Listing> getListings()
  {
    return listings;
  }

  public void setListings(List<Listing> listings)
  {
    this.listings = listings;
  }

  public String getAction()
  {
    return action;
  }

  public void setAction(String action)
  {
    this.action = action;
  }
}
