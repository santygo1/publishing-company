package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.ContractNumberNonUniqueException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.repository.ContractRepository;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
public class ContractService {

    ContractRepository contractRepository;

    @Transactional
    public Contract create(Contract contract) {
        return contractRepository.save(contract);
    }

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContract(String id)
            throws EntityWithIdIsNotExistsException {
        return contractRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Contract.class)
                );
    }

    @Transactional
    public Contract changeContractInfo(String id, Contract update) {
        Contract updatedContract = contractRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Contract.class)
                );

        contractRepository.findByContractNumber(update.getContractNumber())
                .ifPresent(contractDB -> {
                    if (!contractDB.getId().equals(updatedContract.getId())) {
                        throw new ContractNumberNonUniqueException();
                    }
                });

        updatedContract.setContractNumber(update.getContractNumber());
        updatedContract.setCreateDate(update.getCreateDate());
        updatedContract.setDuration(update.getDuration());
        updatedContract.setFinishDate(updatedContract.getCreateDate().plusYears(update.getDuration()));

        return contractRepository.save(updatedContract);
    }

    @Transactional
    public void delete(String id) {
        contractRepository.deleteById(id);
    }
}
