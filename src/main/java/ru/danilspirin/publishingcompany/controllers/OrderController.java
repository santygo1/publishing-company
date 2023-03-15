package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.danilspirin.publishingcompany.service.OrderService;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;

    @GetMapping()
    public String showAll(Model model){
        model.addAttribute("orders", orderService.getAll());
        return "orders-view/orders";
    }

    @GetMapping("/{id}")
    public String showAll(@PathVariable String id, Model model){
        return null;
    }

}
