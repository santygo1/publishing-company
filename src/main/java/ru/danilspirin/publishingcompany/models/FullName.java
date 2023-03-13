package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Embeddable
@Setter @Getter @ToString @FieldDefaults(level = AccessLevel.PRIVATE)
public class FullName {
    String name, surname, patronymic;
    public String initials(){
        return name.substring(0, 1) + '.' + ' ' +
                patronymic.substring(0, 1) + '.' + ' ' +
                surname;
    }
}
