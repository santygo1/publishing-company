package ru.danilspirin.publishingcompany.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter @Getter @ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class FullName {

    @Size(max = 64, message = "Имя должно быть короче 64 символов")
    @NotBlank(message = "Укажите имя")
    @Pattern(regexp = "[A-Z][a-z]*|[А-Я][а-я]*",
            message = "Поле должно содержать только символы латиницы или только символы кириллицы \s" +
                    "и начинаться с заглавной буквы")
    String name;

    @Size(max = 64, message = "Фамилия должно быть короче 64 символов")
    @NotBlank(message = "Укажите фамилию")
    @Pattern(regexp = "[A-Z][a-z]*|[А-Я][а-я]*",
            message = "Поле должно содержать только символы латиницы или только символы кириллицы \s" +
                    "и начинаться с заглавной буквы")
    String surname;

    @Size(max = 64, message = "Отчество должно быть короче 64 символов")
    @Pattern(regexp = "()|[A-Z][a-z]*|[А-Я][а-я]*",
            message = "Поле должно содержать только символы латиницы или только символы кириллицы \s" +
                    "и начинаться с заглавной буквы")
    String patronymic;

    public String initials() {
        return name.substring(0, 1) + '.' + ' ' +
                (patronymic.isEmpty() ?
                        "":
                        patronymic.substring(0, 1) + '.' + ' ')
                + surname;
    }
}
