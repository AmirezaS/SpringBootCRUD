package org.j2ee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePersonRequest(
    @NotBlank(message="Name is Required")
    @Size(min=2, max=100,message="Name must be between 2 and 100 characters")
    String name,

    @NotBlank(message="Family is Required")
    @Size(min=2, max=100, message="Family must bebetween 2 and 100 characters")
    String family,

    @NotBlank(message="Email is Required")
    @Email(message="Email must be a valid email address")
    String email) 
    {}
