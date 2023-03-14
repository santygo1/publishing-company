package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "books-view/book_edit";
    }

    @PatchMapping("/{id}")
    public String editBook(@PathVariable String id, @ModelAttribute Book update) {
        Book updated = bookService.changeBookInfo(id, update);
        return "redirect:/books/" + updated.getId();
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/writers")
    public String bindWriter(
            @PathVariable String id,
            @RequestParam("selectedWriterId") String writerId) {
        if (!writerId.equals("")) {
            id = bookService.addWriter(id, writerId)
                    .getId();
        }
        return "redirect:/books/" + id + "#writers";
    }

    @DeleteMapping("/{id}/writers/{writerId}")
    public String unbindingWriter(@PathVariable String id, @PathVariable String writerId) {
        log.info("Удаление писателя с id {} из книги с id {}", writerId, id);
        id = bookService.removeWriter(id, writerId)
                .getId();
        return "redirect:/books/" + id + "#writers";
    }


}
