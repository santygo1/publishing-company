package ru.danilspirin.publishingcompany.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.exceptions.EntitiesNotFoundException;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.service.ContractService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/contracts")
@Slf4j
@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ContractController {

    ContractService contractService;

    @GetMapping
    public ResponseEntity<Iterable<Contract>> getAllContracts()
            throws EntitiesNotFoundException
    {
        List<Contract> contracts = contractService.getAll();

        if (contracts.isEmpty())
            throw new EntitiesNotFoundException(Contract.class);

        return ResponseEntity.ok(contracts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(
            @PathVariable String id)
            throws EntityWithIdIsNotExistsException
    {
        return ResponseEntity.ok(contractService.get(id));
    }

    @GetMapping("/{id}/writer")
    public ResponseEntity<Writer> getWriterByContractId(
            @PathVariable String id)
        throws EntityWithIdIsNotExistsException
    {
        return ResponseEntity.ok(contractService.getWriterByContractId(id));
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract)
            throws EntityAlreadyExistsException {
        Contract created = contractService.create(contract);
        return ResponseEntity
                .created(URI.create("api/contracts/" + created.getId()))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> replaceContract(
            @PathVariable String id,
            @RequestBody Contract contract)
            throws EntityWithIdIsNotExistsException
    {
        contractService.replace(id, contract);
        return ResponseEntity.ok().build();
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContract(@PathVariable String id){
        contractService.delete(id);
        return ResponseEntity.ok().build();
    }
}
