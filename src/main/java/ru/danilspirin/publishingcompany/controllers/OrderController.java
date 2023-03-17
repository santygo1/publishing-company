package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Customer;
import ru.danilspirin.publishingcompany.models.Order;
import ru.danilspirin.publishingcompany.service.BookService;
import ru.danilspirin.publishingcompany.service.CustomerService;
import ru.danilspirin.publishingcompany.service.OrderService;

import java.util.Set;

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

    @GetMapping("/create")
    public String showCreateNewOrderWithRelatedCustomerForm(Model model){
        Order order = new Order();
        model.addAttribute("order", order);

        Set<Book> existedBooks = bookService.getAll();
        model.addAttribute("books", existedBooks);

        Set<Customer> existedCustomers = customerService.getAll();
        model.addAttribute("customers", existedCustomers);

        if (existedBooks.isEmpty()){
            return "redirect:/book-service-error";
        }

        return "orders-view/order_create";
    }

    @PostMapping()
    public String createNewOrderWithRelatedCustomerForm(
            @ModelAttribute("order") Order order,
            @RequestParam("selectedCustomerId") String selectedCustomerId,
            @RequestParam("selectedBookId") String selectedBookId
    ){
        if (!selectedCustomerId.equals("")){
            order.setCustomer(customerService.getCustomer(selectedCustomerId));
        }

        //TODO: Обработка exeption
        order.setBook(bookService.getBook(selectedBookId));

        Order created = orderService.addOrder(order);

        return "redirect:/orders/" + created.getId();
    }

    @GetMapping("/edit/{id}")
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
