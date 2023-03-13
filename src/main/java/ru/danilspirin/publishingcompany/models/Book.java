package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "books")
public class Book {

    @Id
    String id = UUID.randomUUID().toString();

    @Column(name  = "isbn", unique = true)
    String ISBN;

    String title;

    int circulation;

    @Column(name = "issue_date")
    LocalDate issueDate;

    @Column(name = "cost_price")
    int costPrice;

    @Column(name = "selling_price")
    int sellingPrice;

    @Column(name = "absolute_fee")
    int absoluteFee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "writers_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id"))
    Set<Writer> writers;

    @OneToMany(mappedBy = "book")
    private List<Order> orders;

}

