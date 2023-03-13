package ru.danilspirin.publishingcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.danilspirin.publishingcompany.models.Book;

import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByISBN(String ISBN);
}
