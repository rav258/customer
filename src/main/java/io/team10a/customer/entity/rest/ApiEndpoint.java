package io.team10a.customer.entity.rest;

import io.team10a.customer.entity.Customer;
import io.team10a.customer.entity.dao.CriteriaBean;
import io.team10a.customer.entity.dao.CustomerDaoBeanImpl;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Locale;

@Named
@RequestScoped
@Path("/demo")
public class ApiEndpoint {

    @Inject
    CustomerDaoBeanImpl customerBean;

    @Inject
    CriteriaBean criteriaBean;

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello world!";
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllCustomers() {
        return customerBean.customerList();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Customer findById(@PathParam("id") Long id, @QueryParam("caps") Boolean caps) {
        final Customer byid = customerBean.findByid(id);
        if (byid != null && Boolean.TRUE.equals(caps)) {
            byid.setFirstName(byid.getFirstName().toUpperCase(Locale.ROOT));
        }
        return byid;
    }

    @POST
    @Path("new")
    @Produces(MediaType.APPLICATION_JSON)
    public void saveCustomerApi(Customer customer) {
        customerBean.saveCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }

    @DELETE
    @Path("/{id}")
    public void deleteCustomer(@PathParam("id") Long id) {
        customerBean.deleteCustomerRest(id);
    }

    @PUT
    @Path("update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Customer updateUser(@PathParam("id")Long id, Customer customer){

        return customerBean.updateCustomer(customer);
    }

    @GET
    @Path("/allByCriteria")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getAllByCriteria(@QueryParam("name") String firstName){
        return criteriaBean.findAllCustomersWithoutPredicateSet(firstName);
    }

}
