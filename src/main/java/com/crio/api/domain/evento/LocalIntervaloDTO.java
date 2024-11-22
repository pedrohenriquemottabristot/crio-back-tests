package com.crio.api.domain.evento;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class LocalIntervaloDTO {
    private String local;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    // Getters e setters
}
