package org.j2ee.controller;

import java.net.URI;

import org.j2ee.dto.CreatePersonRequest;
import org.j2ee.dto.PagedResponse;
import org.j2ee.dto.PersonResponse;
import org.j2ee.dto.UpdatePersonRequest;
import org.j2ee.model.entity.Person;
import org.j2ee.model.repository.PersonJpaRepository;
import org.j2ee.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/v1/persons")
@CrossOrigin(origins="*")
public class PersonController {

    
    private final PersonService service;

    public PersonController(PersonService service){
        this.service = service;
    }

    //GET /api/v1/persons?page=0&size=20
    @GetMapping
    public ResponseEntity<PagedResponse<PersonResponse>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue= "20") int size) {
        return ResponseEntity.ok(service.findAll(page, size));
    }


    //GET /api/v1/persons/42
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    //POST /api/v1/persons
    @PostMapping
    public ResponseEntity<PersonResponse> create(
        @Valid @RequestBody CreatePersonRequest request) throws Exception {
        PersonResponse created = service.create(request);
        URI location = URI.create("/api/v1/persons/" + created.id());

        return ResponseEntity.created(location).body(created);
        
    }
    
        // PATCH /api/v1/persons/42
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePersonRequest request) throws Exception {

        return ResponseEntity.ok(service.update(id, request));
    }

    // DELETE /api/v1/perons/42
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    
    

}
