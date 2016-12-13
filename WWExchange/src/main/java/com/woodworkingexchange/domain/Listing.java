package com.woodworkingexchange.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql="update Listing set deleted = 1 where id = ?")
@Where(clause="deleted <> 1")
public class Listing implements Serializable
{
  private static final long serialVersionUID = -9032320762430168976L;
    private Long id;
    private Boolean deleted = false;
    private Date deletedDate;
    private Boolean live;
    private Users users;
    private String machineType;
    private String manufacturer;
    private String model;
    private Integer yearManufactured;
    private Integer voltage;
    private Integer cycles;
    private Integer phases;
    private String machineCondition;
    private String description;
    private String country;
    private String prov;
    private BigDecimal price;
    private String methodOfContact;
    private String contactInfo;
    private String contactPerson;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String action;
    private Date dateAdded;
    private Boolean renewalReminderSent;
    
    public Boolean getLive()
    {
      return live;
    }

    public void setLive(Boolean live)
    {
      this.live = live;
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

    @Column(name="date_added")
    public Date getDateAdded()
    {
      return dateAdded;
    }

    public void setDateAdded(Date dateAdded)
    {
      this.dateAdded = dateAdded;
    }

    @Transient
    public String getAction()
    {
      return action;
    }

    public void setAction(String action)
    {
      this.action = action;
    }

    public String getImage1()
    {
      return image1;
    }

    public void setImage1(String image1)
    {
      this.image1 = image1;
    }

    public String getImage2()
    {
      return image2;
    }

    public void setImage2(String image2)
    {
      this.image2 = image2;
    }

    public String getImage3()
    {
      return image3;
    }

    public void setImage3(String image3)
    {
      this.image3 = image3;
    }

    public String getImage4()
    {
      return image4;
    }

    public void setImage4(String image4)
    {
      this.image4 = image4;
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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    public Users getUsers()
    {
      return users;
    }

    public void setUsers(Users users)
    {
      this.users = users;
    }

    @Column(name="machine_type")
    public String getMachineType()
    {
      return machineType;
    }

    public void setMachineType(String machineType)
    {
      this.machineType = machineType;
    }

    @Transient
    public Long getUserId()
    {
      return this.users.getId();
    }
    
    public String getManufacturer()
    {
      return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
      this.manufacturer = manufacturer;
    }

    public String getModel()
    {
      return model;
    }

    public void setModel(String model)
    {
      this.model = model;
    }

    @Column(name="year_manufactured")
    public Integer getYearManufactured()
    {
      return yearManufactured;
    }

    public void setYearManufactured(Integer yearManufactured)
    {
      this.yearManufactured = yearManufactured;
    }
    
    public Integer getVoltage()
    {
      return voltage;
    }

    public void setVoltage(Integer voltage)
    {
      this.voltage = voltage;
    }

    public Integer getCycles()
    {
      return cycles;
    }

    public void setCycles(Integer cycles)
    {
      this.cycles = cycles;
    }

    public Integer getPhases()
    {
      return phases;
    }

    public void setPhases(Integer phases)
    {
      this.phases = phases;
    }

    @Column(name="machine_condition")
    public String getMachineCondition()
    {
      return machineCondition;
    }

    public void setMachineCondition(String machineCondition)
    {
      this.machineCondition = machineCondition;
    }

    @Column(name="description", length=800)
    public String getDescription()
    {
      return description;
    }

    public void setDescription(String description)
    {
      this.description = description;
    }

    public String getCountry()
    {
      return country;
    }

    public void setCountry(String country)
    {
      this.country = country;
    }

    public String getProv()
    {
      return prov;
    }

    public void setProv(String prov)
    {
      this.prov = prov;
    }

    public BigDecimal getPrice()
    {
      return price;
    }

    public void setPrice(BigDecimal price)
    {
      this.price = price;
    }

    @Column(name="method_of_contact")
    public String getMethodOfContact()
    {
      return methodOfContact;
    }

    public void setMethodOfContact(String methodOfContact)
    {
      this.methodOfContact = methodOfContact;
    }

    @Column(name="contact_person")
    public String getContactPerson()
    {
      return contactPerson;
    }

    public void setContactPerson(String contactPerson)
    {
      this.contactPerson = contactPerson;
    }

    @Column(name="contact_info")
    public String getContactInfo()
    {
      return contactInfo;
    }

    public void setContactInfo(String contactInfo)
    {
      this.contactInfo = contactInfo;
    }

    @Column(name="renewal_reminder_sent")
    public Boolean getRenewalReminderSent()
    {
      return renewalReminderSent;
    }

    public void setRenewalReminderSent(Boolean renewalReminderSent)
    {
      this.renewalReminderSent = renewalReminderSent;
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
      Listing other = (Listing) obj;
      if (id == null)
      {
        if (other.id != null) return false;
      } else if (!id.equals(other.id)) return false;
      return true;
    }

    @Override
    public String toString()
    {
      return "Listing [id=" + id + "]";
    }
    
    
}
