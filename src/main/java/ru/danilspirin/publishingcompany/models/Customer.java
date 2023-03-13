package ru.danilspirin.publishingcompany.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.UUID;

@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "customers")
public class Customer {

    @Id
    final String id = UUID.randomUUID().toString();

    String company;

    @Column(name = "company_address")
    String address;

    @Valid
    @Embedded
    FullName fullName;

    @OneToMany(mappedBy = "customer")
    List<Order> orders;
}
