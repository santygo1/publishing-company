package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.SQLWarningException;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.exceptions.PassportDataNonUniqueException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.repository.ContractRepository;
import ru.danilspirin.publishingcompany.repository.WriterRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@Slf4j
public class WriterService {
    final WriterRepository writerRepository;
    final ContractRepository contractRepository;

    @Transactional
    public Writer addWriterWithContract(Writer writer, Contract contract){
        // TODO: Оптимизация запросов к базе. (Обработка ошибок вместо обращения с проверкой)
        // TODO: Проверка пользователя(маловероятно что UUID v4 сгенерирует одинаковый ключ
        //  но все же)

        // Проверка уникальности пользователя по паспортным данным
        if (writerRepository
                .findByPassportSeriesAndPassportId(
                        writer.getPassportSeries(),
                        writer.getPassportId())
                .isPresent()) {
            throw new PassportDataNonUniqueException();
        }

        // Проверка существования контракта по заданному номеру
        if (contractRepository.findById(contract.getId()).isPresent()) {
            throw new EntityAlreadyExistsException(contract.getId(), Contract.class);
        }

        writer.setContract(contract);
        return writerRepository.save(writer);
    }

    public List<Writer> getAll(){
        return writerRepository.findAll();
    }

    public Writer getById(String id){
        return writerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Writer.class)
                );
    }

    public Contract getContractByWriterId(String id){
        return writerRepository.findById(id)
                .map(Writer::getContract)
                .orElseThrow(() ->
                       new EntityWithIdIsNotExistsException(id, Writer.class)
                );
    }

    @Transactional
    public Writer updateWriterInfoById(String writerId, Writer writer){
        // Ищем сущность из базы, для обновления
        Writer findByID = writerRepository.findById(writerId)
                .map(writerDB -> {
                    writerDB.setAddress(writer.getAddress());
                    writerDB.setFullName(writer.getFullName());
                    writerDB.setPassportId(writer.getPassportId());
                    writerDB.setPassportSeries(writer.getPassportSeries());
                    writerDB.setPhoneNumber(writer.getPhoneNumber());
                    return writerDB;
                })
                // Если не нашли кидаем ошибку об отсутствии сущности с таким id
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(writerId,Writer.class)
                );
        // Сохраняем изменения измененной сущности в базу
        return writerRepository.save(findByID);
    }

    @Transactional
    public Contract replaceContractByWriterId(String id, Contract contract){
        Writer updatedWriter = writerRepository.findById(id)
                .map(writer -> {
                    Contract oldContract = writer.getContract();
                    // если id контрактов разные изменяем
                    if (!oldContract.equals(contract)){
                        log.info(String.format("contract ids are %s",
                                oldContract.equals(contract)));
                        writer.setContract(contract);
                    }
                    return writer;
                })
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Writer.class)
                );
        return writerRepository.save(updatedWriter).getContract();
    }

    @Transactional
    public void deleteWriterWithContract(String id){
        Optional<Writer> findById = writerRepository.findById(id);
        if (findById.isPresent()){
            Writer writer = findById.get();
            contractRepository.delete(writer.getContract());
            writerRepository.delete(writer);
        }else{
            throw new EntityWithIdIsNotExistsException(id, Writer.class);
        }
    }
}
