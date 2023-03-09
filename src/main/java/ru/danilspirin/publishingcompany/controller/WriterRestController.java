package ru.danilspirin.publishingcompany.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.exceptions.EntitiesNotFoundException;
import ru.danilspirin.publishingcompany.exceptions.EntityAlreadyExistsException;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.request.WriterRequest;
import ru.danilspirin.publishingcompany.service.WriterService;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("api/writers")
public class WriterRestController {
    WriterService writerService;

    @GetMapping
    public ResponseEntity<Iterable<Writer>> present()
            throws EntitiesNotFoundException
    {
        List<Writer> writers = writerService.getAll();

        if (writers.isEmpty())
            throw new EntitiesNotFoundException(Writer.class);
        return ResponseEntity.ok(writers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Writer> getById(
            @PathVariable String id)
            throws EntityWithIdIsNotExistsException
    {
        return ResponseEntity.ok(writerService.getById(id));
    }

    @GetMapping("/{id}/contract")
    public ResponseEntity<Contract> getContractByWriterId(
            @PathVariable String id)
            throws EntityWithIdIsNotExistsException
    {
        return ResponseEntity
                .ok(writerService.getContractByWriterId(id));
    }


    @PostMapping
    public ResponseEntity<Writer> addNewWriterWithContract(
            @RequestBody WriterRequest request)
            throws EntityAlreadyExistsException
    {
        Writer writer =
                writerService.addWriterWithContract(request.getWriter(),request.getContract());

        return ResponseEntity
                .created(URI.create("api/writers/" + writer.getId()))
                .build();
    }

    @PutMapping("/{id}/contract")
    public ResponseEntity<Contract> replaceContractByWriterId(
            @PathVariable String id,
            @RequestBody Contract contract
    ) throws EntityWithIdIsNotExistsException
    {
        Contract createdContract =
                writerService.replaceContractByWriterId(id, contract);

        return ResponseEntity
                .created(URI.create(String.format(
                        "api/contracts/",
                        createdContract.getId()
                )))
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Writer> changeInfoAboutWriter(
            @RequestBody Writer writer,
            @PathVariable String id)
            throws EntityWithIdIsNotExistsException
    {
        writerService.updateWriterInfoById(id, writer);

        return ResponseEntity
                .ok()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Writer> deleteWriterWithContract(@PathVariable String id){
        writerService.deleteWriterWithContract(id);

        return ResponseEntity
                .ok()
                .build();
    }
}
