package com.woodworkingexchange.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name="address")
@SQLDelete(sql="update Address set deleted = 1 where id = ?")
@Where(clause="deleted <> 1")
public class Address implements Serializable {

  private static final long serialVersionUID = 8658988825402532524L;
    private Long id;
    private Users user;
    private Boolean deleted = false;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String prov;
    private String postalCode;
    private String country;
    
    @Id  
    @GeneratedValue(generator="myGenerator")  
    @GenericGenerator(name="myGenerator", strategy="foreign", parameters=@Parameter(value="user", name = "property"))  
    public Long getId()
    {
      return id;
    }

    public void setId(Long id)
    {
      this.id = id;
    }
    
    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    public Users getUser()
    {
      return user;
    }

    public void setUser(Users user)
    {
      this.user = user;
    }

    public Boolean getDeleted()
    {
      return deleted;
    }

    public void setDeleted(Boolean deleted)
    {
      this.deleted = deleted;
    }

    @Column(name="address_line1")
    public String getAddressLine1()
    {
      return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
      this.addressLine1 = addressLine1;
    }

    @Column(name="address_line2")
    public String getAddressLine2()
    {
      return addressLine2;
    }

    public void setAddressLine2(String addressLine2)
    {
      this.addressLine2 = addressLine2;
    }

    public String getCity()
    {
      return city;
    }

    public void setCity(String city)
    {
      this.city = city;
    }

    public String getProv()
    {
      return prov;
    }

    public void setProv(String prov)
    {
      this.prov = prov;
    }

    @Column(name="postal_code")
    public String getPostalCode()
    {
      return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
      this.postalCode = postalCode;
    }

    public String getCountry()
    {
      return country;
    }

    public void setCountry(String country)
    {
      this.country = country;
    }
}
