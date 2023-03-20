package ru.danilspirin.publishingcompany.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.exceptions.BookServiceError;
import ru.danilspirin.publishingcompany.exceptions.OrderNumberNonUniqueException;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.models.Order;
import ru.danilspirin.publishingcompany.requests.OrderCustomerRequest;
import ru.danilspirin.publishingcompany.service.BookService;
import ru.danilspirin.publishingcompany.service.CustomerService;
import ru.danilspirin.publishingcompany.service.OrderService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;
    CustomerService customerService;
    BookService bookService;

    @GetMapping()
    public String showAllOrders(Model model){
        model.addAttribute("orders", orderService.getAll());
        return "orders-view/orders";
    }

    @GetMapping("/{id}")
    public String showOrder(@PathVariable String id, Model model){
        model.addAttribute("order", orderService.getOrder(id));
        return "orders-view/order";
    }

    private void loadCreateData(Model model) throws BookServiceError{
        Set<Book> existedBooks = bookService.getAll();
        if (existedBooks.isEmpty()){
            throw new BookServiceError();
        }
        model.addAttribute("books", existedBooks);

        Set<Customer> existedCustomers = customerService.getAll();
        model.addAttribute("customers", existedCustomers);
    }
    @GetMapping("/create")
    public String showCreateNewOrderWithRelatedCustomerForm(Model model)
            throws BookServiceError {

        OrderCustomerRequest orderCustomerRequest =
                new OrderCustomerRequest(new Customer(), new Order());
        model.addAttribute("request", orderCustomerRequest);

        loadCreateData(model);

        return "orders-view/order_create";
    }

    @PostMapping()
    public String createNewOrderWithRelatedCustomerForm(
            @ModelAttribute("request") @Valid OrderCustomerRequest orderCustomerRequest,BindingResult bindingResult,
            @RequestParam("selectedCustomerId") String selectedCustomerId,
            @RequestParam("selectedBookId") String selectedBookId,
            Model model
    ){
        loadCreateData(model);
        model.addAttribute("selectedCustomerId", selectedCustomerId);
        model.addAttribute("selectedBookId", selectedBookId);
        model.addAttribute("orderIdError", null);

        Customer binding;
        if (!selectedCustomerId.equals("")){
            // Заказчик был выбран из списка заказчиков
            binding = customerService.getCustomer(selectedCustomerId);
            orderCustomerRequest.setCustomer(new Customer()); // Очищаем поля ввода заказчика
            // Удаляем все ошибки проверок наложенные на этот объект
            List<FieldError> errorsToKeep = bindingResult.getFieldErrors().stream()
                    .filter(fer -> !fer.getField().contains("customer"))
                    .toList();

            bindingResult = new BeanPropertyBindingResult(orderCustomerRequest, "request");

            for (FieldError fieldError : errorsToKeep) {
                bindingResult.addError(fieldError);
            }
        }else {
            // Заказчик был создан
            // Делаем проверку на поля ввода
            if (bindingResult.hasErrors()){
                return "orders-view/order_create";
            }

            binding = customerService.create(orderCustomerRequest.getCustomer());
        }
        // Делаем проверку всех вводимых полей
        if (bindingResult.hasErrors()){
            log.info("Обьекты :{}" , bindingResult.getFieldErrors().stream().map(FieldError::toString).toList());
            return "orders-view/order_create";
        }
        Order order = orderCustomerRequest.getOrder();
        order.setCustomer(binding);

        order.setBook(bookService.getBook(selectedBookId));
        Order created;
        try{
            created = orderService.addOrder(order);
        }catch(OrderNumberNonUniqueException ex){
            model.addAttribute("orderIdError", ex.getMessage());
            return "orders-view/order_create";
        }
        return "redirect:/orders/" + created.getId();
    }

    @GetMapping("/{id}/edit")
    public String showEditOrderForm(@PathVariable String id, Model model){
        Order updated = orderService.getOrder(id);
        model.addAttribute("order", updated);
        return "orders-view/order_edit";
    }
    @PatchMapping("/{id}")
    public String editOrder(
            @PathVariable String id,
            @ModelAttribute("order") Order update){

        Order updated = orderService.changeOrderInfo(id, update);
        return "redirect:/orders/" + updated.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
        return "redirect:/orders";
    }
}
