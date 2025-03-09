package io.github.frankleyrocha.springboot_pg_example.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
    private UUID id;
    private String name;
    private String email;
}
