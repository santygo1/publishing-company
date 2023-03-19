package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity @Table(name = "contracts")
public class Contract{

    @Id
    String id = UUID.randomUUID().toString();

    @Size(max = 64, message = "Номер контракта должен быть короче 64 символов")
    @NotBlank(message = "Укажите номер контракта")
    @Column(name = "contract_number", unique = true)
    String contractNumber;

    @NotNull(message = "Укажите дату заключения контракта")
    @Column(name="create_date")
    LocalDate createDate; // Дата заключения контракта

    @NotNull(message = "Укажите длительность контракта")
    @Max(value = 100, message = "Длительность контракта должна быть меньше 100 лет")
    @Min(value = 1, message = "Длительность контракта должна быть больше 1 года")
    int duration; // Длительность контракта (в годах)

    @Column(name="is_finished")
    boolean isFinished;

    @Column(name="finish_date")
    LocalDate finishDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    Writer writer;
}
