package com.crio.api.domain.evento;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class IntervaloDataDTO {
    private LocalDateTime inicio;
    private LocalDateTime fim;
}
