package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    String id = UUID.randomUUID().toString();

    @Valid
    @OneToOne(mappedBy = "writer")
    Contract contract;

    @NotBlank(message = "Укажите серию паспорта")
    @Pattern(regexp = "\\d{4}", message = "Серия паспорта должна состоять из 4 цифр")
    @Column(name = "passport_series")
    String passportSeries; // серия паспорта

    @NotBlank(message = "Укажите номер паспорта")
    @Pattern(regexp = "\\d{6}", message = "Номер паспорта должен состоять из 6 цифр")
    @Column(name = "passport_id")
    String passportId; // номер паспорта

    @Valid
    @Embedded
    FullName fullName;

    @Size(max = 128, message = "Адрес должен быть короче 128 символов")
    @NotBlank(message = "Укажите адрес писателя")
    String address;

    @Size(max = 18, message = "Номер телефона должен содержать меньше 18 символов")
    @NotBlank(message = "Укажите номер телефона")
    @Pattern(regexp = "[+][1-9]\\s[(][1-9][0-9]{2}[)]\\s[0-9]{3}([-][0-9]{2}){2}",
            message = "Номер телефона должен удовлетворять шаблону: +7 (000) 000-00-00")
    @Column(name = "phone_number")
    String phoneNumber;

    @ManyToMany(mappedBy = "writers", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Book> books;
}

