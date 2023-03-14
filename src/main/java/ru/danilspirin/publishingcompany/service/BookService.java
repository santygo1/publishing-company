package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.exceptions.IsbnNonUniqueException;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.repository.BookRepository;
import ru.danilspirin.publishingcompany.repository.WriterRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class BookService {

    BookRepository bookRepository;
    WriterRepository writerRepository;

    @Transactional
    public Book addBook(Book book) throws IsbnNonUniqueException {
        // Если добавляемая книга имеет не уникальный ISBN
        bookRepository.findByISBN(book.getISBN())
                // То выбрасываем ошибку
                .ifPresent(bookDB -> {
                    throw new IsbnNonUniqueException();
                });

        return bookRepository.save(book);
    }

    public Set<Book> getAll() {
        return new HashSet<>(bookRepository.findAll());
    }

    public Book getBook(String id) throws EntityWithIdIsNotExistsException {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );
    }

    @Transactional
    public Book changeBookInfo(String id, Book update) {
        Book updatedBook =  bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );

        bookRepository.findByISBN(update.getISBN())
                .ifPresent( bookDB -> {
                    if (!bookDB.getId().equals(updatedBook.getId())){
                        throw new IsbnNonUniqueException();
                    }
                });

        updatedBook.setISBN(update.getISBN());
        updatedBook.setTitle(update.getTitle());
        updatedBook.setCirculation(update.getCirculation());
        updatedBook.setIssueDate(update.getIssueDate());
        updatedBook.setCostPrice(update.getCostPrice());
        updatedBook.setSellingPrice(update.getSellingPrice());
        updatedBook.setAbsoluteFee(update.getAbsoluteFee());

        return bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book addWriter(String id, String writerId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );
        Writer writer = writerRepository.findById(writerId)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(writerId, Writer.class)
                );
        writer.getBooks().add(book);
        book.getWriters().add(writer);

        return bookRepository.save(book);
    }

    @Transactional
    public Book removeWriter(String id, String writerId) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );

        Writer unbinding = book.getWriters().stream()
                .filter(writer -> writer.getId().equals(writerId))
                .findAny()
                .orElseThrow(() -> {
                    log.info("Здесь");
                    return new EntityWithIdIsNotExistsException(writerId, Writer.class);
                });

        book.getWriters().remove(unbinding);
        unbinding.getBooks().remove(book);

        return bookRepository.save(book);
    }
}
