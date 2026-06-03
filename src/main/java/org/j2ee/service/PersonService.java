package org.j2ee.service;

import java.util.List;
import org.j2ee.dto.CreatePersonRequest;
import org.j2ee.dto.PagedResponse;
import org.j2ee.dto.PersonResponse;
import org.j2ee.dto.UpdatePersonRequest;
import org.j2ee.model.entity.Person;
import org.j2ee.model.repository.PersonJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;

@Service
public class PersonService {


    private final PersonJpaRepository personJpaRepository;

    public PersonService(PersonJpaRepository repository){
        this.personJpaRepository = repository;
    }


    //READ ONE
    public PersonResponse findById(Long id){
        Person person = personJpaRepository.findById(id)
        .orElseThrow();

        return PersonResponse.from(person);
    }

    //CREATE
    @Transactional
    public PersonResponse create(CreatePersonRequest request) throws Exception{
        if (personJpaRepository.existsByEmail(request.email())){
            throw new Exception();
        }
        Person person = new Person();
        person.setName(request.name());
        person.setFamily(request.family());
        person.setEmail(request.email());

        Person savedPerson = personJpaRepository.save(person);
        return PersonResponse.from(savedPerson);
    }

    //READ ALL
    public PagedResponse<PersonResponse> findAll(int page, int size){
        Pageable pageable = (Pageable) PageRequest.of(page, size, Sort.by("name"));
        Page<Person> result = personJpaRepository.findAll(pageable);

        List<PersonResponse> data = result.getContent()
        .stream()
        .map(PersonResponse::from)
        .toList();

        return new PagedResponse<>(
            data,
            result.getNumber(),
            result.getSize(),
            result.getTotalElements(),
            result.getTotalPages()
        );
        
    }

    //UPDATE 
    @Transactional
    public PersonResponse update(Long id, UpdatePersonRequest request) throws Exception{
        Person person = personJpaRepository.findById(id)
        .orElseThrow();

        if (request.name() != null){
            person.setName(request.name());
        }
        if (request.email() != null){
            if (personJpaRepository.existsByEmail(request.email())){
                throw new Exception();
            }
            person.setEmail(request.email());
        }

        return PersonResponse.from(person);
    }


    //DELETE
    @Transactional
    public void delete(Long id) throws Exception{
        if (!personJpaRepository.existsById(id)){
            throw new Exception();
            
        }
        personJpaRepository.deleteById(id);
    }


}
