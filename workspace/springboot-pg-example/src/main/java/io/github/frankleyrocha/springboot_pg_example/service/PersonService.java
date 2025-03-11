package io.github.frankleyrocha.springboot_pg_example.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.frankleyrocha.springboot_pg_example.dto.PersonDTO;
import io.github.frankleyrocha.springboot_pg_example.mapper.PersonMapper;
import io.github.frankleyrocha.springboot_pg_example.repository.PersonRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final MailService mailService;

    /**
     * Find all persons
     *
     * @return List of all persons as DTOs
     */
    @Transactional(readOnly = true)
    public List<PersonDTO> findAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find a person by ID
     *
     * @param id The UUID of the person to find
     * @return The person as a DTO if found, otherwise null
     */
    @Transactional(readOnly = true)
    public PersonDTO findById(UUID id) {
        return personRepository.findById(id)
                .map(personMapper::toDTO)
                .orElse(null);
    }

    /**
     * Create a new person
     *
     * @param personDTO The person data to create
     * @return The created person as a DTO with generated ID
     */
    @Transactional
    public PersonDTO create(PersonDTO personDTO) {
        // Ensure a new entity is created by setting ID to null
        personDTO.setId(null);
        var person = personMapper.toEntity(personDTO);
        var savedPerson = personRepository.save(person);

        mailService.sendMail(
                personDTO.getEmail(),
                "Prezado(a) %s, seja bem vindo!".formatted(personDTO.getName()),
                "Boas vindas!");

        return personMapper.toDTO(savedPerson);
    }

    /**
     * Update an existing person
     *
     * @param id        The UUID of the person to update
     * @param personDTO The updated person data
     * @return The updated person as a DTO if found, otherwise null
     */
    @Transactional
    public PersonDTO update(UUID id, PersonDTO personDTO) {
        if (!personRepository.existsById(id)) {
            return null;
        }

        // Ensure we're updating the correct entity
        personDTO.setId(id);
        var person = personMapper.toEntity(personDTO);
        var updatedPerson = personRepository.save(person);
        return personMapper.toDTO(updatedPerson);
    }

    /**
     * Delete a person by ID
     *
     * @param id The UUID of the person to delete
     * @return true if deleted, false if not found
     */
    @Transactional
    public boolean deleteById(UUID id) {
        if (!personRepository.existsById(id)) {
            return false;
        }

        personRepository.deleteById(id);
        return true;
    }
}
