package io.team10a.customer.entity.dao;

import io.team10a.customer.entity.Customer;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
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

    public void deleteCustomer(Long id){
        final Customer byID = findByID(id);
        if (byID!=null) {
            entityManager.remove(byID);
        }
    }

    public void updateCustomer(String firstName, String lastName, String email, Long id){
        Customer customer;
        customer = entityManager.find(Customer.class, id);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        entityManager.merge(customer);
    }

    public Customer findByID(Long id){
        final List<Customer> customers = customerList();
        return customers.stream()
                .filter(a->a.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public List<Customer> customerList() {
        return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }


    public Customer findByid(Long id) {
        return entityManager.find(Customer.class,id);
    }


}