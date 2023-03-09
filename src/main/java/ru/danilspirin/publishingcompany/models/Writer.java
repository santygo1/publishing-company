package ru.danilspirin.publishingcompany.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "writers")
public class Writer {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Id
    final String id = UUID.randomUUID().toString();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    Contract contract;

    @Column(name = "passport_series")
    short passportSeries; // серия паспорта

    @Column(name = "passport_id")
    int passportId; // номер паспорта

    @Valid
    @Embedded
    FullName fullName;

    String address;

    @Column(name = "phone_number")
    String phoneNumber;

    @JsonIgnore
    @ManyToMany(mappedBy = "writers")
    Set<Book> books;
}

