package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.exceptions.IsbnNonUniqueException;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.repository.BookRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class BookService {
    
    BookRepository bookRepository;

    @Transactional
    public Book addBook(Book book) throws  IsbnNonUniqueException {
        // Если добавляемая книга имеет не уникальный ISBN
        bookRepository.findByISBN(book.getISBN())
                // То выбрасываем ошибку
                .ifPresent(bookDB -> {throw new IsbnNonUniqueException();});

        return bookRepository.save(book);
    }

    public Set<Book> getAll(){
        return new HashSet<>(bookRepository.findAll());
    }

    public Book getBook(String id) throws EntityWithIdIsNotExistsException{
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );
    }

    public Set<Writer> getWriters(String id) {
        return bookRepository.findById(id)
                .map(Book::getWriters)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Book.class)
                );
    }

    @Transactional
    public void delete(String id){
        bookRepository.deleteById(id);
    }

    public void bindWriter(String bookId, String writerId) {
    }

    public Book changeBookInfo(String id, Book book) {
        return null;
    }
}
