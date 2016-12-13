package com.woodworkingexchange.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feedback_survey")
public class FeedbackSurvey implements Serializable
{
  private static final long serialVersionUID = 8098621094948376971L;
  private Long id;
  private Boolean successful;
  private Boolean contributedToSuccess;
  private String suggestions;
//  private Listing listing;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public Long getId()
  {
    return id;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public Boolean getSuccessful()
  {
    return successful;
  }
  public void setSuccessful(Boolean successful)
  {
    this.successful = successful;
  }
  @Column(name="contributed_to_success")
  public Boolean getContributedToSuccess()
  {
    return contributedToSuccess;
  }
  public void setContributedToSuccess(Boolean contributedToSuccess)
  {
    this.contributedToSuccess = contributedToSuccess;
  }
  @Column(name="suggestions", length=750)
  public String getSuggestions()
  {
    return suggestions;
  }
  public void setSuggestions(String suggestions)
  {
    this.suggestions = suggestions;
  }
  
//  @OneToOne
//  @JoinColumn(name="listing_id", nullable=false)
//  public Listing getListing()
//  {
//    return listing;
//  }
//  public void setListing(Listing listing)
//  {
//    this.listing = listing;
//  }
  
}
