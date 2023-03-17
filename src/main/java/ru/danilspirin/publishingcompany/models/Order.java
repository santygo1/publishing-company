package ru.danilspirin.publishingcompany.models;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;
@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "orders")
public class Order {

    @Id
    String id = UUID.randomUUID().toString();

    @Column(name = "order_number")
    String orderNumber;

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Column(name = "create_date")
    LocalDate createDate;

    @Column(name = "finish_date")
    LocalDate finishDate;

    @Column(name = "books_count")
    int booksCount;
}
