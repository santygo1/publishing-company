package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "books")
public class Book {

    @Id
    String id = UUID.randomUUID().toString();

    @NotBlank(message = "Укажите ISBN книги")
    @Length(
            min = 17,
            max = 17,
            message = "ISBN должен состоять из 13 цифр"
    )
    @Pattern(
            regexp = "(\\d+[-]){4}\\d",
            message = "ISBN должен быть указан в формате EAN-13"
    )
    @Column(name  = "isbn", unique = true)
    String ISBN;

    @NotBlank(message = "Укажите название книги")
    String title;

    @Min(value = 0, message = "Тираж должен быть положительным либо равен 0")
    int circulation;

    @NotNull(message = "Укажите дату выпуска")
    @Column(name = "issue_date")
    LocalDate issueDate;

    @Min(value = 0, message = "Цена должна быть положительной.")
    @Column(name = "cost_price")
    int costPrice;

    @Min(value = 0, message = "Цена должна быть положительной.")
    @Column(name = "selling_price")
    int sellingPrice;

    @Min(value = 0, message = "Гонорар должен быть положительным")
    @Column(name = "absolute_fee")
    int absoluteFee;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "writers_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "writer_id"))
    Set<Writer> writers;

    @OneToMany(mappedBy = "book")
    private List<Order> orders;

}

