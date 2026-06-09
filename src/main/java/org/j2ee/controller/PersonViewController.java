package org.j2ee.controller;

import org.j2ee.model.entity.Person;
import org.j2ee.model.repository.PersonJpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/persons")
public class PersonViewController {

    private final PersonJpaRepository repo;

    public PersonViewController (PersonJpaRepository personJpaRepository){
        this.repo = personJpaRepository;
    }

    @GetMapping
    public String list(Model model){
        model.addAttribute("persons", repo.findAll());
        return "persons/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model){
        model.addAttribute("person", new Person());
        return "/persons/form";
    }

    @GetMapping
    public String showEditForm(@PathVariable Long id, Model model){
        Person person = repo.findById(id)
            .orElseThrow();
        
            model.addAttribute("Person", person);
            return "persons/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("person") Person person,
                        BindingResult result,
                    RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            return "persons/form";
        }
        repo.save(person);

        // redirectAttributes.addFlashAttribute("message", person.getId() == null ? "Person Created!" : "Person Updated!");
        

        return "redirect:/persons";
    }

    // public String delete(@PathVariable Long id
    //                     RedirectAttributes redirectAttributes
    // ){
    //     repo.deleteById(id);
    //     redirectAttributes.addFlashAttribute("message", "Person deleted.");
    //     return "rediect:/persons";

    // }
    
    







}
