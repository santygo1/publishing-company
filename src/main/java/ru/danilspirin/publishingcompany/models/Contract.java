package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.*;
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

    @Column(name = "contract_number", unique = true)
    String contractNumber;

    @Column(name="create_date")
    LocalDate createDate; // Дата создания контракта

    int duration; // Длительность контракта (в годах)

    @Column(name="is_finished")
    boolean isFinished;

    @Column(name="finish_date")
    LocalDate finishDate;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    Writer writer;

}
