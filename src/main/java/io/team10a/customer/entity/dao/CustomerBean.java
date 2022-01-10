package io.team10a.customer.entity.dao;

import io.team10a.customer.entity.Customer;
import lombok.Data;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;


@Named
@ViewScoped
@Data
public class CustomerBean implements Serializable {

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @EJB
    CustomerDaoBeanImpl customerDaoBeanImpl;
    @Size(min = 2)
    String firstNameProvidedByUser;
    String lastNameProvidedByUser;
    @Email
    String emailProvidedByUser;

    Long id;

    public List<Customer> getAllCustomersList() {
        return customerDaoBeanImpl.customerList();
    }

    public void saveCustomer() {
        customerDaoBeanImpl.saveCustomer(firstNameProvidedByUser, lastNameProvidedByUser, emailProvidedByUser);
        firstNameProvidedByUser = null;
        lastNameProvidedByUser = null;
        emailProvidedByUser = null;
    }

    public void deleteCustomer(Long id) {
        customerDaoBeanImpl.deleteCustomerRest(id);
    }

    public void updateCustomer() {
        customerDaoBeanImpl.saveCustomer(customer);

    }


}
