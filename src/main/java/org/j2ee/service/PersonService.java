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

import jakarta.transaction.Transactional;

@Service
public class PersonService {

    private final PersonJpaRepository personRepository;

    public PersonService(PersonJpaRepository personJpaRepository){
        this.personRepository = personJpaRepository;
    }

    // READ ALL
    public PagedResponse<PersonResponse> findAll(int page, int size){
        PageRequest pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Person> result = personRepository.findAll(pageable);

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

    public PersonResponse findById(Long id){
        Person person = personRepository.findById(id)
            .orElseThrow();

        return PersonResponse.from(person);

    }

    // CREATE
    @Transactional
    public PersonResponse create(CreatePersonRequest request) throws Exception{
        if (personRepository.existsByEmail(request.email())){
            throw new Exception(request.email() + " Duplicated");
        }

        Person person = new Person();
        person.setName(request.name());
        person.setFamily(request.family());
        person.setEmail(request.email());

        Person saved = personRepository.save(person);
        return PersonResponse.from(person);
    }


    // UPDATE
    @Transactional
    public PersonResponse update(Long id, UpdatePersonRequest request) throws Exception{
        Person person = personRepository.findById(id)
            .orElseThrow();
        
            if (request.name() != null){
                person.setName(request.name());
            }
            if (request.family() != null) {
                person.setFamily(request.family());
            }
            if (request.email() != null){
                if (personRepository.existsByEmail(request.email())){
                    throw new Exception(request.email() + " Duplicated");
                }
                person.setEmail(request.email());
            }
            return PersonResponse.from(person);
    }

    // DELETE
    @Transactional
    public void delete(Long id) throws Exception{
        if (!personRepository.existsById(id)){
            throw new Exception(id + " Not Existed");
        }
        personRepository.deleteById(id);
    }
    
    


}
