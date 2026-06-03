package org.j2ee.dto;

import org.j2ee.model.entity.Person;

public record PersonResponse(Long id, String name, String family, String email) {

    public static PersonResponse from(Person person){
        return new PersonResponse(
            person.getId(),
            person.getName(),
            person.getFamily(),
            person.getEmail());
    }

}
