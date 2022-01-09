package io.team10a.customer.entity.dao;

import io.team10a.customer.entity.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerDaoBeanImpl {

    @PersistenceContext(unitName = "default")
    EntityManager entityManager;


    public Customer saveCustomer(String firstName, String lastName, String email) {
        final Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        entityManager.persist(customer);
        return customer;
    }

    public Customer update(Customer current){
        return entityManager.merge(current);
    }



    public void deleteCustomerRest(Long id) {
        Customer customer = entityManager.find(Customer.class, id);
        entityManager.remove(customer);
    }

    public List<Customer> customerList() {
        return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    public Customer findByid(Long id) {
        return entityManager.find(Customer.class, id);
    }



}
