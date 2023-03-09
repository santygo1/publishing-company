package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.repository.ContractRepository;
import ru.danilspirin.publishingcompany.repository.WriterRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class ContractService {

    ContractRepository contractRepository;
    WriterRepository writerRepository;

    @Transactional
    public Contract create(Contract contract){
        contractRepository.findById(contract.getId()).ifPresent(contract1 -> {
            throw new EntityAlreadyExistsException(contract.getId(), Contract.class);
        });
        return contractRepository.save(contract);
    }

    public List<Contract> getAll(){
        return contractRepository.findAll();
    }

    public Contract get(String id)
            throws EntityWithIdIsNotExistsException
    {
        return contractRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Contract.class)
                );
    }

    public Writer getWriterByContractId(String id)
            throws EntityWithIdIsNotExistsException
    {
        //TODO: вынести запрос в репозиторий
        return contractRepository.findById(id)
                .map(Contract::getWriter)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Contract.class)
                );
    }

    @Transactional
    public Contract replace(String updatedContractId, Contract update){
        Optional<Contract> updatedContract = contractRepository.findById(updatedContractId);
        return updatedContract
                // Обновляем данные о контракте если контракт с id существует
                .map(contract -> {
                    LocalDate updateContractCreateDate
                            = updatedContract.get().getCreateDate();
                    contract.setCreateDate(updateContractCreateDate);

                    int updateContractDuration = update.getDuration();
                    contract.setDuration(updateContractDuration);

                    contract.setFinished(update.isFinished());

                    contract.setFinishDate(
                            updateContractCreateDate.plusYears(updateContractDuration));
                    return contract;
                })
                // Иначе ошибка
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(
                                updatedContractId, Contract.class)
                );
    }

    @Transactional
    public void delete(String id){
        Optional<Contract> contract = contractRepository.findById(id);
        if (contract.isPresent()) {
            Contract getContract = contract.get();
            contractRepository.delete(getContract);
            writerRepository.delete(getContract.getWriter());
        }
        else
            throw new EntityWithIdIsNotExistsException(id, Contract.class);
    }
}
