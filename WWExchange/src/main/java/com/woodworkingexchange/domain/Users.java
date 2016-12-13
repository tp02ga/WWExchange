package com.woodworkingexchange.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="users", uniqueConstraints= {@UniqueConstraint(columnNames={"email"})})
@SQLDelete(sql="update Users set deleted = 1  where id = ?")
@Where(clause="deleted <> 1")
public class Users implements Serializable
{
  private static final long serialVersionUID = -5134833736454151535L;
    private Long id;
    private Boolean deleted = false;
    private Date deletedDate;
    private Address address;
    private String name;
    private String title;
    private String company;
    private String email;
    private String password;
    private String primaryBusinessActivity;
    private Set<Listing> listings = new HashSet<Listing>();
    private Set<Authorities> authorities = new HashSet<Authorities>();
    
    public Users(Users user)
    {
      this.id = user.getId();
      this.deleted = user.getDeleted();
      this.deletedDate = user.getDeletedDate();
      this.address = user.getAddress();
      this.name = user.getName();
      this.title = user.getTitle();
      this.company = user.getCompany();
      this.email = user.getEmail();
      this.password = user.getPassword();
      this.primaryBusinessActivity = user.getPrimaryBusinessActivity();
      this.listings = user.getListings();
      this.authorities = user.getAuthorities();
    }
    
    public Users()
    {
    }

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

    public Boolean getDeleted()
    {
      return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
      this.deleted = deleted;
    }

    @Column(name="deleted_date")
    public Date getDeletedDate()
    {
      return deletedDate;
    }

    public void setDeletedDate(Date deletedDate)
    {
      this.deletedDate = deletedDate;
    }

    @OneToOne(cascade=CascadeType.ALL, mappedBy="user", fetch=FetchType.EAGER)
    public Address getAddress()
    {
      return address;
    }

    public void setAddress(Address address)
    {
      this.address = address;
    }

    public String getName()
    {
      return name;
    }

    public void setName(String name)
    {
      this.name = name;
    }

    public String getEmail()
    {
      return email;
    }

    public void setEmail(String email)
    {
      this.email = email;
    }
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy="users")
    public Set<Listing> getListings()
    {
      return listings;
    }

    public void setListings(Set<Listing> listings)
    {
      this.listings = listings;
    }

    public String getPassword()
    {
      return password;
    }

    public void setPassword(String password)
    {
      this.password = password;
    }

    @OneToMany(mappedBy="users", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    public Set<Authorities> getAuthorities()
    {
      return authorities;
    }
    
    public void setAuthorities(Set<Authorities> authorities)
    {
      this.authorities = authorities;
    }

    public String getTitle()
    {
      return title;
    }

    public void setTitle(String title)
    {
      this.title = title;
    }

    public String getCompany()
    {
      return company;
    }

    public void setCompany(String company)
    {
      this.company = company;
    }

    @Column(name="primary_business_activity")
    public String getPrimaryBusinessActivity()
    {
      return primaryBusinessActivity;
    }

    public void setPrimaryBusinessActivity(String primaryBusinessActivity)
    {
      this.primaryBusinessActivity = primaryBusinessActivity;
    }

    @Override
    public int hashCode()
    {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? 0 : id.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj)
    {
      if (this == obj) return true;
      if (obj == null) return false;
      if (getClass() != obj.getClass()) return false;
      Users other = (Users) obj;
      if (id == null)
      {
        if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      return true;
    }
}
