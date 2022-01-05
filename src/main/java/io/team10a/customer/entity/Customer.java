package io.team10a.customer.entity;

import lombok.Data;


import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;

@Entity
@Table(name = "CUSTOMER")
@Data
@Access(AccessType.FIELD)
@NamedQuery(
        name = "Customer.findAll",
        query = "SELECT cc FROM Customer cc"
)
@NamedNativeQuery(
        name = "Customer.update",
        query = "update Customer set firstName=?,lastName=?,email=? where id = ?"
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    @JsonbProperty("FIRST_NAME")
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;
}
