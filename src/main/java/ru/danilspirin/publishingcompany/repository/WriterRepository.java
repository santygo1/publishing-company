package ru.danilspirin.publishingcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilspirin.publishingcompany.models.Writer;

import java.util.Optional;

@Repository
public interface WriterRepository extends JpaRepository<Writer, String> {
    Optional<Writer> findByPassportSeriesAndPassportId(
             String passportSeries, String passportId
    );
}
