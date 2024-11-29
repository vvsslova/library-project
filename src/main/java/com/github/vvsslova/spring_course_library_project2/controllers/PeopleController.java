package com.github.vvsslova.spring_course_library_project2.controllers;


import com.github.vvsslova.spring_course_library_project2.models.Book;
import com.github.vvsslova.spring_course_library_project2.models.Person;
import com.github.vvsslova.spring_course_library_project2.services.LibraryService;
import com.github.vvsslova.spring_course_library_project2.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final LibraryService libraryService;

    @Autowired
    public PeopleController(PersonService personService, LibraryService libraryService) {
        this.personService = personService;
        this.libraryService = libraryService;
    }

    @GetMapping()
    public String getAllPeople(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        return "people/show-person";
    }

    @GetMapping("/{id}/libraryCard")
    public String showPersonCard(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findOne(id));
        model.addAttribute("books", libraryService.lentBooks(personService.findOne(id)));
        return "people/show-person-card";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new-person";
    }

    @PostMapping("/addNewPerson")
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/new-person";
        }
        personService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personService.findOne(id));
        return "people/edit-person";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "people/edit-person";
        }
        personService.update(id, person);
        return "redirect:/people/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }
}
