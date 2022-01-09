package io.team10a.customer.entity.dao;

import io.team10a.customer.entity.Customer;
import io.team10a.customer.entity.Customer_;
import lombok.extern.java.Log;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.UserTransaction;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Log
@Named
@RequestScoped
public class CriteriaUpdateDemo {


    @PersistenceContext
    EntityManager entityManager;

    @Resource
    private UserTransaction userTransaction;


    public Customer updateCustomersFirstName(Long id, String firstName) {

        final Customer customer = entityManager.find(Customer.class, id);
        log.info(customer.getFirstName());
        try {
            userTransaction.begin();
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final CriteriaUpdate<Customer> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Customer.class);
            final Root<Customer> root = criteriaUpdate.from(Customer.class);

            criteriaUpdate.set(Customer_.firstName, firstName);

            criteriaUpdate.where(criteriaBuilder.equal(root.get(Customer_.id), customer.getId()));

            final Query query = entityManager.createQuery(criteriaUpdate);
            query.executeUpdate();
            userTransaction.commit();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return customer;
    }


    public List<Customer> listAllCustomers(String name) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
        Root<Customer> root = query.from(Customer.class);

        Set<Predicate> predicate = new LinkedHashSet<>();

        if (name != null && !name.isEmpty()) {
            predicate.add(

                    builder.like(root.get(Customer_.firstName), "%" + name + "%")
            );
        }


        if (!predicate.isEmpty()) {
            query.where(predicate.toArray(new Predicate[0]));
        }
        return entityManager.createQuery(query).getResultList();


    }


}
