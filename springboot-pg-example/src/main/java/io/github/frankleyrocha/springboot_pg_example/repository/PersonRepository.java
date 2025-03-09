package io.github.frankleyrocha.springboot_pg_example.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.frankleyrocha.springboot_pg_example.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, UUID> {
    // Spring Data JPA provides basic CRUD operations by default
    // Additional custom query methods can be added here if needed

}
