package ru.danilspirin.publishingcompany.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.service.CustomerService;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/customers")
public class CustomerController {

    CustomerService customerService;

    @GetMapping()
    public String showAllCustomers(Model model){
        model.addAttribute("customers", customerService.getAll());
        return "customers-view/customers";
    }

    @GetMapping("/{id}")
    public String showCustomer(@PathVariable String id,
                               Model model){
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "customers-view/customer";
    }

    @GetMapping("/{id}/edit")
    public String showEditCustomerForm(@PathVariable String id,
                                       Model model){
        Customer customer = customerService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "customers-view/customer_edit";
    }

    @PatchMapping("{id}")
    public String editCustomer(
            @PathVariable String id,
            @ModelAttribute("customer") @Valid Customer update,
            BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "customers-view/customer_edit";
        }
        // Не проверяю try/catch т.к. выкидывается на верхний обработчик
        Customer updated = customerService.changeCustomerInfo(id, update);
        return "redirect:/customers/"+ updated.getId();
    }

    @DeleteMapping("{id}")
    public String deleteContract(@PathVariable String id){
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }
}
