package ru.danilspirin.publishingcompany.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;

@JsonIgnoreProperties
@Getter @FieldDefaults(level = AccessLevel.PRIVATE)
public class WriterRequest {
    Writer writer;
    Contract contract;
}
