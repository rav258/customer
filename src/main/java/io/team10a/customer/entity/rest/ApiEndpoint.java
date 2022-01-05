package io.team10a.customer.entity.rest;

import io.team10a.customer.entity.Customer;
import io.team10a.customer.entity.dao.CustomerBean;
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
    public void saveCustomerApi(Customer customer) {
        customerBean.saveCustomer(customer.getFirstName(), customer.getLastName(), customer.getEmail());
    }



}
