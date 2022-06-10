package com.javaschool.microecare.contractmanagement.dao;

import com.javaschool.microecare.catalogmanagement.dao.Option;
import com.javaschool.microecare.catalogmanagement.dao.Tariff;
import com.javaschool.microecare.commonentitymanagement.dao.BaseEntity;
import com.javaschool.microecare.customermanagement.dao.Customer;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "CONTRACTS")
public class Contract extends BaseEntity {
   @ManyToOne
   @JoinColumn(name = "customer_id")
   private Customer customer;
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "phone_number_id", referencedColumnName = "id")
   private MobileNumber phoneNumber;
   @ManyToOne
   @JoinColumn(name = "tariff_id")
   private Tariff tariff;
   @ManyToMany
   @JoinTable(name = "contract_option",
           joinColumns = @JoinColumn(name = "contract_id"),
           inverseJoinColumns = @JoinColumn(name = "option_id"))
   private Set<Option> options;

   public Contract() {
      super();
   }

   public Contract(Customer customer, MobileNumber phoneNumber, Tariff tariff, Set<Option> options) {
      super();
      this.customer = customer;
      this.phoneNumber = phoneNumber;
      this.tariff = tariff;
      this.options = options;
   }

   public MobileNumber getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(MobileNumber phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public Tariff getTariff() {
      return tariff;
   }

   public void setTariff(Tariff tariff) {
      this.tariff = tariff;
   }

   public Set<Option> getOptions() {
      return options;
   }

   public void setOptions(Set<Option> options) {
      this.options = options;
   }

   public Customer getCustomer() {
      return customer;
   }

   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
}
