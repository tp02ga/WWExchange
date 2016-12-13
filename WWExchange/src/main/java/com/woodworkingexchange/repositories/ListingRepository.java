package com.woodworkingexchange.repositories;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woodworkingexchange.domain.Listing;
import com.woodworkingexchange.domain.Users;

@Transactional
public interface ListingRepository extends JpaRepository<Listing, Long>
{
  @Query("select l from Listing l where l.dateAdded <= :date and l.renewalReminderSent = false")
  List<Listing> getlistingForRenewal(@Param(value="date") Date time);

  List<Listing> findByUsers(Users user);
  
  @Query("update Listing l set l.dateAdded = current_date(), l.renewalReminderSent = false where l.id = :id")
  void renewListingById(@Param(value="id") Long id);

  @Query("select l from Listing l where l in (:incompleteListings)")
  List<Listing> findByListingsIn(@Param(value = "incompleteListings") List<Listing> incompleteListings);
  
  //TODO: this query is incomplete and will need to be corrected
  @Query("select l from Listing l")
  Page<Listing> getListingsByKeyword(String searchTerm, Pageable pageRequest);
  
  @Query("select l from Listing l where l.live = true or (l.users = :user and l.live = false) order by l.live asc, l.dateAdded desc")
  Page<Listing> getListingsByUser(@Param(value="user") Users user, Pageable pageRequest);
  
  @Query("select l.manufacturer from Listing l group by l.manufacturer")
  List<? extends String> getBrands();
  
  @Modifying(clearAutomatically = true)
  @Query("update Listing set image1 = null where id = :id")
  void deleteListingImage1ById(@Param(value="id") Long id);
  
  @Modifying(clearAutomatically = true)
  @Query("update Listing set image2 = null where id = :id")
  void deleteListingImage2ById(@Param(value="id") Long id);
  
  @Modifying(clearAutomatically = true)
  @Query("update Listing set image3 = null where id = :id")
  void deleteListingImage3ById(@Param(value="id") Long id);
  
  @Modifying(clearAutomatically = true)
  @Query("update Listing set image4 = null where id = :id")
  void deleteListingImage4ById(@Param(value="id") Long id);
  
}
