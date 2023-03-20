package ru.danilspirin.publishingcompany.controllers;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.danilspirin.publishingcompany.exceptions.ContractNumberNonUniqueException;
import ru.danilspirin.publishingcompany.exceptions.PassportDataNonUniqueException;
import ru.danilspirin.publishingcompany.models.Book;
import ru.danilspirin.publishingcompany.models.Contract;
import ru.danilspirin.publishingcompany.models.Writer;
import ru.danilspirin.publishingcompany.service.BookService;
import ru.danilspirin.publishingcompany.service.WriterService;

import java.util.Set;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/writers")
public class WriterController {

    WriterService writerService;
    BookService bookService;

    @GetMapping()
    public String showAllWriters(Model model) {
        model.addAttribute("writers", writerService.getAll());
        return "writers-view/writers";
    }

    @GetMapping("/{id}")
    public String showWriter(@PathVariable String id, Model model) {
        Writer writer = writerService.getWriter(id);
        model.addAttribute("writer", writer);

        Set<Book> allBooks = bookService.getAll();
        allBooks.removeAll(writer.getBooks());

        model.addAttribute("books", allBooks);

        return "writers-view/writer";
    }

    @GetMapping("/create")
    public String showCreateNewWriterAndContractForm(Model map) {
        Writer writer = new Writer();
        writer.setContract(new Contract());
        map.addAttribute("writer", writer);

        return "writers-view/writer_create";
    }

    @PostMapping
    public String createNewWriterAndContract(
            @ModelAttribute @Valid Writer writer,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "writers-view/writer_create";
        }

        Writer created;
        try{
            created = writerService.addWriterWithRelatedContract(writer);
        }catch (PassportDataNonUniqueException ex){
            FieldError passportIdError = new FieldError(
                    "Writer",
                    "passportId",
                    writer.getPassportId(),
                    false, null, null,
                    ex.getMessage()
            );
            bindingResult.addError(passportIdError);

            FieldError passportSeriesError = new FieldError(
                    "Writer",
                    "passportSeries",
                    writer.getPassportSeries(),
                    false, null,null,
                    ex.getMessage()
            );
            bindingResult.addError(passportSeriesError);

            return "writers-view/writer_create";
        }catch (ContractNumberNonUniqueException ex){
            bindingResult.addError(
                    new FieldError(
                            "Writer",
                            "contract.contractNumber",
                            writer.getContract().getContractNumber(),
                            false,null,null,
                            ex.getMessage()
                    )
            );
            return "writers-view/writer_create";
        }
        return "redirect:/writers/" + created.getId();
    }

    @GetMapping("/{id}/edit")
    public String showEditWriterForm(@PathVariable String id, Model model) {
        Writer writer = writerService.getWriter(id);
        model.addAttribute("writer", writer);

        return "writers-view/writer_edit";
    }

    @PatchMapping("/{id}")
    public String editWriter(
            @PathVariable String id,
            @ModelAttribute("writer") @Valid Writer update,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "writers-view/writer_edit";
        }

        Writer updated;
        try {
            updated = writerService.changeWriterInfo(id, update);
        }catch (PassportDataNonUniqueException ex){
            FieldError passportIdError = new FieldError(
                    "Writer",
                    "passportId",
                    update.getPassportId(),
                    false, null, null,
                    ex.getMessage()
            );
            bindingResult.addError(passportIdError);

            FieldError passportSeriesError = new FieldError(
                    "Writer",
                    "passportSeries",
                    update.getPassportSeries(),
                    false, null,null,
                    ex.getMessage()
            );
            bindingResult.addError(passportSeriesError);

            return "writers-view/writer_edit";
        }
        return "redirect:/writers/" + updated.getId();
    }

    @DeleteMapping("/{id}")
    public String deleteWriter(@PathVariable String id) {
        writerService.deleteWriter(id);
        return "redirect:/writers";
    }

    @PatchMapping("/{id}/books")
    public String bindBook(
            @PathVariable String id,
            @RequestParam("selectedBookId") String bookId) {
        if (!bookId.equals("")) {
            id = writerService.addBook(id, bookId)
                    .getId();
        }
        return "redirect:/writers/" + id + "#books";
    }

    @DeleteMapping("/{id}/books/{bookId}")
    public String unbindingBook(@PathVariable String id, @PathVariable String bookId) {
        id = writerService.removeBook(id, bookId)
                .getId();
        return "redirect:/writers/" + id + "#books";
    }

}