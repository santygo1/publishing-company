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

import java.util.List;
import java.util.UUID;

@Setter @Getter @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity @Table(name = "customers")
public class Customer {

    @Id
    String id = UUID.randomUUID().toString();

    @Size(max = 64, message = "Название компании должно быть короче 64 символов")
    @NotBlank(message = "Укажите название компании")
    String company;

    @Size(max = 128, message = "Адрес компании должен быть короче 128 символов")
    @NotBlank(message = "Укажите адрес компании")
    @Column(name = "company_address")
    String address;

    @Valid
    @Embedded
    FullName ownerFullName;

    @Size(max = 18, message = "Номер телефона должен содержать меньше 18 символов")
    @NotBlank(message = "Укажите номер телефона")
    @Pattern(regexp = "[+][1-9]\\s[(][1-9][0-9]{2}[)]\\s[0-9]{3}([-][0-9]{2}){2}",
            message = "Номер телефона должен удовлетворять шаблону: +7 (000) 000-00-00")
    @Column(name = "phone_number")
    String phoneNumber;

    @OneToMany(mappedBy = "customer")
    List<Order> orders;
}
