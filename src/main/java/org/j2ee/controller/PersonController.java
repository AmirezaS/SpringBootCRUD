package org.j2ee.controller;

import java.net.URI;
import java.util.List;

import org.j2ee.dto.CreatePersonRequest;
import org.j2ee.dto.PagedResponse;
import org.j2ee.dto.PersonResponse;
import org.j2ee.dto.UpdatePersonRequest;
import org.j2ee.service.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/persons")
@CrossOrigin(origins="*")
public class PersonController {

    private final PersonService personService;
    
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    // GET /api/v1/persons?page=0&size=20
    @GetMapping
    public ResponseEntity<PagedResponse<PersonResponse>> getAll(
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="20") int size){
        return ResponseEntity.ok(personService.findAll(page, size));
    }

   

    // POST /api/v1/persons
    @PostMapping
    public ResponseEntity<PersonResponse> create(
        @Valid @RequestBody CreatePersonRequest request) throws Exception{
            PersonResponse created = personService.create(request);

            URI location = URI.create("/ali/v1/persons/" + created.id());
            return ResponseEntity.created(location).body(created);
    }
    

    // PATCH /api/v1/persons/42
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdatePersonRequest request) throws Exception{
            return ResponseEntity.ok(personService.update(id,request));
    }

    // DELETE /api/v1/persons/42
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception{
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
    

    
}
