package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.repository.CustomerRepository;

import java.util.List;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CustomerService {
    
    CustomerRepository repository;

    @Transactional
    public void create(Customer customer){
        repository.save(customer);
    }

    public List<Customer> getAll(){
        return repository.findAll();
    }

    public Customer getById(String id){
        return repository.findById(id)
                .orElseThrow(() -> new EntityWithIdIsNotExistsException(id, Customer.class));
    }

    @Transactional
    public void update(Customer update){
        repository.save(update);
    }

    @Transactional
    public void delete(String id){
        repository.deleteById(id);
    }
}
