package edu.vsu.putin_p_a.springbootapp.controllers;

import edu.vsu.putin_p_a.springbootapp.models.Book;
import edu.vsu.putin_p_a.springbootapp.models.Person;
import edu.vsu.putin_p_a.springbootapp.service.PeopleService;
import edu.vsu.putin_p_a.springbootapp.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonValidator personValidator;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PersonValidator personValidator, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String showPeople(Model model) {
        List<Person> people = peopleService.getPeople();
        model.addAttribute("people", people);
        return "people/people";
    }

    @GetMapping("/{id}")
    public String showPersonById(@PathVariable("id") int id, Model model) {
        Optional<Person> optPerson = peopleService.getPersonByIdWithBooks(id);

        if (optPerson.isPresent()) {
            model.addAttribute("person", optPerson.get());

            //FIXME: возможно не будет работать, т.к за пределами транзакции
            List<Book> books = optPerson.get().getBooks();
            model.addAttribute("books", books);

            return "people/personById";
        }

        return "redirect:/people/{id}/notFound";
    }

    @GetMapping("/{id}/notFound")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String showNotFound(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);
        return "people/notFound";
    }

    @GetMapping("/new")
    public String showPersonCreationForm(@ModelAttribute("person") Person person) {
        return "people/personCreationForm";
    }

    @PostMapping
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/personCreationForm";
        }
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String showPersonEditingForm(@PathVariable("id") int id, Model model) {
        Optional<Person> optPerson = peopleService.getPersonById(id);
        if (optPerson.isPresent()) {
            model.addAttribute("person", optPerson.get());
            return "people/personEdit";
        }
        return "redirect:/people/{id}/notFound";
    }

    @PatchMapping("/{id}")
    public String editPerson(@PathVariable("id") int id,
                             @ModelAttribute("person") @Valid Person updatedPerson,
                             BindingResult bindingResult) {
        personValidator.validate(updatedPerson, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/personEdit";
        }
        peopleService.edit(id, updatedPerson);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleService.remove(id);
        return "redirect:/people";
    }
}
