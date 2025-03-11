package io.github.frankleyrocha.springboot_pg_example.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.frankleyrocha.springboot_pg_example.dto.PersonDTO;
import io.github.frankleyrocha.springboot_pg_example.service.PersonService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Get all persons
     * @return List of all persons
     */
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        return ResponseEntity.ok(personService.findAll());
    }

    /**
     * Get a person by ID
     * @param id The UUID of the person to retrieve
     * @return The person if found, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable UUID id) {
        PersonDTO personDTO = personService.findById(id);
        if (personDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(personDTO);
    }

    /**
     * Create a new person
     * @param personDTO The person data to create
     * @return The created person with 201 Created status
     */
    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.create(personDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    /**
     * Update an existing person
     * @param id The UUID of the person to update
     * @param personDTO The updated person data
     * @return The updated person if found, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable UUID id, @RequestBody PersonDTO personDTO) {
        PersonDTO updatedPerson = personService.update(id, personDTO);
        if (updatedPerson == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPerson);
    }

    /**
     * Delete a person by ID
     * @param id The UUID of the person to delete
     * @return 204 No Content if deleted, or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id) {
        boolean deleted = personService.deleteById(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
