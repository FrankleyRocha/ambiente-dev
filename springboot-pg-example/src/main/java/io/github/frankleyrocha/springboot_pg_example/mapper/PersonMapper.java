package io.github.frankleyrocha.springboot_pg_example.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import io.github.frankleyrocha.springboot_pg_example.domain.Person;
import io.github.frankleyrocha.springboot_pg_example.dto.PersonDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PersonMapper {

    PersonDTO toDTO(Person person);

    Person toEntity(PersonDTO personDTO);

    // MapStruct will automatically implement these methods
}
