package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.ContractNumberNonUniqueException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.exceptions.PassportDataNonUniqueException;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.repository.BookRepository;
import ru.danilspirin.publishingcompany.repository.ContractRepository;
import ru.danilspirin.publishingcompany.repository.WriterRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class WriterService {
    WriterRepository writerRepository;
    ContractRepository contractRepository;
    BookRepository bookRepository;

    @Transactional
    public Writer addWriterWithRelatedContract(Writer writer) {

        // Проверяем есть ли уже писатель с указанными паспортными данными в базе
        writerRepository.findByPassportSeriesAndPassportId(
                        writer.getPassportSeries(),
                        writer.getPassportId())
                // Если есть -> выбрасываем ошибку
                .ifPresent(writerDB -> {
                    throw new PassportDataNonUniqueException();
                });

        // Проверяем есть ли уже контракт с заданным номером контракта
        contractRepository.findByContractNumber(writer.getContract().getContractNumber())
                // Если есть -> выбрасываем ошибку
                .ifPresent(contractDB -> {
                    throw new ContractNumberNonUniqueException();
                });

        // Связываем сущности
        Contract contract = writer.getContract();
        contract.setWriter(writer);
        Writer created = writerRepository.save(writer);
        contractRepository.save(contract);

        return created;
    }

    public Set<Writer> getAll() {
        return new HashSet<>(writerRepository.findAll());
    }

    public Writer getWriter(String id) {
        return writerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Writer.class)
                );
    }

    @Transactional
    public Writer changeWriterInfo(String id, Writer update) {
        Writer updatedWriter = writerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Contract.class)
                );

        writerRepository.findByPassportSeriesAndPassportId(
                        update.getPassportSeries(),
                        update.getPassportId())
                .ifPresent(writerDB -> {
                    if (!writerDB.getId().equals(updatedWriter.getId())){
                        throw new PassportDataNonUniqueException();
                    }
                });

        updatedWriter.setFullName(update.getFullName());
        updatedWriter.setPassportSeries(update.getPassportSeries());
        updatedWriter.setPassportId(updatedWriter.getPassportId());
        updatedWriter.setAddress(update.getAddress());
        updatedWriter.setPhoneNumber(update.getPhoneNumber());

        return writerRepository.save(updatedWriter);
    }

    @Transactional
    public void deleteWriter(String id) {
        writerRepository.findById(id)
                .ifPresent(writerDB -> {
                    writerDB.getContract().setFinished(true);
                    writerRepository.delete(writerDB);
                });
    }

    @Transactional
    public Writer addBook(String id, String bookId) {
        Writer writer = writerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Writer.class)
                );
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(bookId, Book.class)
                );

        writer.getBooks().add(book);
        book.getWriters().add(writer);
        bookRepository.save(book);
        return writerRepository.save(writer);
    }

    @Transactional
    public Writer removeBook(String id, String bookId) {
        Writer writer = writerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Writer.class)
                );
        Book unbinding = writer.getBooks().stream()
                .filter(book -> book.getId().equals(bookId))
                .findAny()
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(bookId, Book.class)
                );

        writer.getBooks().remove(unbinding);
        unbinding.getWriters().remove(writer);
        return writerRepository.save(writer);
    }
}
