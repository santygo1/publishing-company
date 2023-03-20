package ru.danilspirin.publishingcompany.models;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @ManyToOne()
    @JoinColumn(name = "book_id", nullable = false)
    Book book;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    Customer customer;

    @Size(max = 64, message = "Номер заказа должен быть короче 64 символов")
    @NotBlank(message = "Укажите номер заказа")
    @Column(name = "order_number", unique = true, nullable = false)
    String orderNumber;

    @NotNull(message = "Укажите дату создания заказа")
    @Column(name = "create_date")
    LocalDate createDate;

    @Column(name = "finish_date")
    LocalDate finishDate;

    @Min(value = 1, message = "Количество заказываемых книг должно быть больше 1")
    @Max(value = 10_000, message = "Количество заказываемых книг должно быть меньше 10 000")
    @Column(name = "books_count")
    int booksCount;
}
