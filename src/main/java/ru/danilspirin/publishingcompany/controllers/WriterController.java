package ru.danilspirin.publishingcompany.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.service.BookService;
import ru.danilspirin.publishingcompany.service.WriterService;

import java.util.Set;

@RequiredArgsConstructor @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/writers")
public class WriterController {

    WriterService writerService;
    BookService bookService;

    @GetMapping()
    public String showAllWriters(Model model){
        model.addAttribute("writers", writerService.getAll());
        return "writers-view/writers";
    }

    @GetMapping("/{id}")
    public String showWriter(@PathVariable String id, Model model){
        Writer writer = writerService.getWriter(id);
        model.addAttribute("writer", writer);

        Set<Book> allBooks = bookService.getAll();
        allBooks.removeAll(writer.getBooks());

        model.addAttribute("books", allBooks);

        return "writers-view/writer";
    }

    @GetMapping("/create")
    public String showEditForm(Model map){
        Writer writer = new Writer();
        writer.setContract(new Contract());
        map.addAttribute("writer", writer);

        return "writers-view/writer_create";
    }

    @PostMapping
    public String createNewWriterWithRelatedContract(@ModelAttribute Writer writer){
        Writer created = writerService.addWriterWithRelatedContract(writer);
        log.info("Дата : {}", writer.getContract().getFinishDate());
        return "redirect:/writers/" + created.getId();
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model){
        Writer writer = writerService.getWriter(id);
        model.addAttribute("writer", writer);

        return "writers-view/writer_edit";
    }

    @PatchMapping("/{id}")
    public String editWriter(@PathVariable String id, @ModelAttribute("writer") Writer update){
        writerService.changeWriterInfo(id,update);
        return "redirect:/writers/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteWriter(@PathVariable String id ){
        writerService.deleteWriter(id);
        return "redirect:/writers";
    }

    @PatchMapping("/{id}/books")
    public String bindBook(
            @PathVariable String id,
            @RequestParam("selectedBookId") String bookId){
        if (!bookId.equals("")) {
            id = writerService.addBook(id, bookId)
                    .getId();
        }
        return "redirect:/writers/" + id + "#books";
    }

    @DeleteMapping("/{id}/books/{bookId}")
    public String unbindingBook(@PathVariable String id, @PathVariable String bookId){
        id = writerService.removeBook(id, bookId)
                .getId();
        return "redirect:/writers/" + id +"#books";
    }

}