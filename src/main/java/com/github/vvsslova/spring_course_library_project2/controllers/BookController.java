package com.github.vvsslova.spring_course_library_project2.controllers;


import com.github.vvsslova.spring_course_library_project2.models.Book;
import com.github.vvsslova.spring_course_library_project2.models.Person;
import com.github.vvsslova.spring_course_library_project2.services.BookService;
import com.github.vvsslova.spring_course_library_project2.services.LibraryService;
import com.github.vvsslova.spring_course_library_project2.services.PersonService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final LibraryService libraryService;
    private final PersonService personService;

    @Autowired
    public BookController(BookService bookService, LibraryService libraryService, PersonService personService) {
        this.bookService = bookService;
        this.libraryService = libraryService;
        this.personService = personService;
    }

    @GetMapping()
    public String getAllBooks(@RequestParam(value = "Sort.by", required = false) String sort,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(value = "itemsPerPage", required = false) Integer itemsPerPage, Model model) {
        if (sort == null & page != null & itemsPerPage != null) {
            model.addAttribute("books", bookService.findAll(PageRequest.of(page, itemsPerPage)).getContent());
        } else if (sort != null & page == null & itemsPerPage == null) {
            model.addAttribute("books", bookService.findAll(Sort.by(sort)));
        } else if (sort != null & page != null & itemsPerPage != null) {
            model.addAttribute("books", bookService.findAll(PageRequest.of(page, itemsPerPage, Sort.by(sort))).getContent());
        } else {
            model.addAttribute("books", bookService.findAll());
        }
        return "books/books";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("persons", libraryService.getLentPerson(id));
        model.addAttribute("allPeople", personService.findAll());
        return "books/show-book";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new-book";
    }

    @PostMapping("/addNewBook")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new-book";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit-book";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "books/edit-book";
        }
        book.setLentPerson(libraryService.getLentPerson(id));
        bookService.update(id, book);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/returnBook/{id}")
    public String returnBook(@PathVariable("id") int id) {
        libraryService.returnBook(id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/lendBook/{id}")
    public String lendBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        libraryService.lendBook(id, person.getId());
        return "redirect:/books/{id}";
    }

    @GetMapping("/search")
    public String search() {
        return "/books/search-book";
    }

    @PostMapping("/searchBook")
    public String searchBook(@RequestParam(value = "title", required = false) String title,
                             Model model) {
        System.out.println(title);
        model.addAttribute("fountBooks", bookService.searchBook(title));
        return "/books/searchResult";
    }
}
