package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.service.BookService;
import ru.danilspirin.publishingcompany.service.WriterService;

import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Controller
@RequestMapping("/books")
public class BookController {

    BookService bookService;
    WriterService writerService;

    @GetMapping
    public String showAllContracts(Model model) {
        model.addAttribute("books", bookService.getAll());
        return "books-view/books";
    }

    @GetMapping("{id}")
    public String showContract(@PathVariable String id, Model model) {
        Book book = bookService.getBook(id);
        model.addAttribute("book", book);

        Set<Writer> allWriters = writerService.getAll();
        allWriters.removeAll(book.getWriters());
        model.addAttribute("writers", allWriters);

        return "books-view/book";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "books-view/book_create";
    }

    @PostMapping()
    public String createBook(@ModelAttribute Book book) {
        Book created = bookService.addBook(book);
        return "redirect:/books/" + created.getId();
    }


}
