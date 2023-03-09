package ru.danilspirin.publishingcompany.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity @Table(name = "contracts")
public class Contract{

    @Id
    String id;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name="create_date")
    LocalDate createDate; // Дата создания контракта

    int duration; // Длительность контракта (в годах)

    @JsonProperty("is_finished")
    @Column(name="is_finished")
    boolean isFinished;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name="finish_date")
    LocalDate finishDate;

    @JsonIgnore
    @OneToOne(mappedBy = "contract")
    Writer writer;
    public Contract(){}

    @JsonCreator
    public Contract(LocalDate createDate, int duration){
        this.createDate = createDate;
        this.duration = duration;
        this.finishDate = createDate.plusYears(duration);
    }
}
