package ru.danilspirin.publishingcompany.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.danilspirin.publishingcompany.exceptions.EntityWithIdIsNotExistsException;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.repository.CustomerRepository;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CustomerService {
    
    CustomerRepository customerRepository;

    @Transactional
    public Customer create(Customer customer){
        return customerRepository.save(customer);
    }

    public Set<Customer> getAll(){
        return new HashSet<>(customerRepository.findAll());
    }

    public Customer getCustomer(String id){
        return customerRepository
                .findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Customer.class)
                );
    }

    @Transactional
    public Customer changeCustomerInfo(String id, Customer update){
        Customer updatedCustomer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new EntityWithIdIsNotExistsException(id, Customer.class)
                );

        updatedCustomer.setCompany(update.getCompany());
        updatedCustomer.setAddress(update.getAddress());
        updatedCustomer.setPhoneNumber(update.getPhoneNumber());
        updatedCustomer.setOwnerFullName(update.getOwnerFullName());

        return customerRepository.save(updatedCustomer);
    }

    @Transactional
    public void deleteIfHasNotOrders(Customer customer){
        if (customer.getOrders().isEmpty()){
            customerRepository.delete(customer);
        }
    }
    @Transactional
    public void deleteCustomer(String id){
        customerRepository.deleteById(id);
    }
}
