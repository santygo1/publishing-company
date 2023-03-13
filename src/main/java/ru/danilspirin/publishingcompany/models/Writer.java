package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "writers")
public class Writer {

    @Id
    final String id = UUID.randomUUID().toString();

    @Valid
    @OneToOne(mappedBy = "writer")
    Contract contract;

    @Column(name = "passport_series")
    String passportSeries; // серия паспорта

    @Column(name = "passport_id")
    String passportId; // номер паспорта

    @Valid
    @Embedded
    FullName fullName;

    String address;

    @Column(name = "phone_number")
    String phoneNumber;

    @ManyToMany(mappedBy = "writers")
    Set<Book> books;
}

