package com.crio.api.controllers;

import com.crio.api.controller.EventoController;
import com.crio.api.domain.evento.EventoReponseDTO;
import com.crio.api.domain.evento.EventoRequestDTO;
import com.crio.api.service.EventoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventoController.class)
public class EventoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventoService eventoService;

    @Autowired
    private ObjectMapper objectMapper;

    private EventoReponseDTO eventoReponseDTO;

    @BeforeEach
    void setUp() {
        eventoReponseDTO = new EventoReponseDTO(
                1L,
                "Evento Teste",
                "Descrição do evento",
                LocalDateTime.of(2024, 11, 22, 10, 0),
                LocalDateTime.of(2024, 11, 22, 15, 0),
                "Local do Evento",
                true,
                "http://linkdoevento.com",
                "Como chegar",
                "http://linkforms.com"
        );
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        List<EventoReponseDTO> list = Arrays.asList(eventoReponseDTO);
        Page<EventoReponseDTO> page = new PageImpl<>(list);

        when(eventoService.findAllPaged(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/eventos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].titulo").value(eventoReponseDTO.titulo()))
                .andExpect(jsonPath("$.content[0].descricao").value(eventoReponseDTO.descricao()));
    }

    @Test
    void findByIdShouldReturnEvento() throws Exception {
        when(eventoService.findById(1L)).thenReturn(eventoReponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/eventos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(eventoReponseDTO.titulo()))
                .andExpect(jsonPath("$.descricao").value(eventoReponseDTO.descricao()));
    }

    @Test
    void insertShouldReturnEventoResponseDTOCreated() throws Exception {
        when(eventoService.insert(any(EventoRequestDTO.class))).thenReturn(eventoReponseDTO);

        EventoRequestDTO eventoRequestDTO = new EventoRequestDTO(
                "Novo Evento",
                "Descrição do novo evento",
                LocalDateTime.of(2024, 12, 10, 9, 0),
                LocalDateTime.of(2024, 12, 10, 18, 0),
                "Local Novo",
                false,
                "http://novoevento.com",
                "Detalhes de como chegar",
                "http://novolinkforms.com",
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/eventos")
                        .content(objectMapper.writeValueAsString(eventoRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value(eventoReponseDTO.titulo()))
                .andExpect(jsonPath("$.descricao").value(eventoReponseDTO.descricao()));
    }

    @Test
    void updateShouldReturnEventoResponseDTO() throws Exception {
        when(eventoService.update(any(Long.class), any(EventoRequestDTO.class))).thenReturn(eventoReponseDTO);

        EventoRequestDTO eventoRequestDTO = new EventoRequestDTO(
                "Evento Atualizado",
                "Descrição atualizada",
                LocalDateTime.of(2024, 12, 15, 10, 0),
                LocalDateTime.of(2024, 12, 15, 16, 0),
                "Local Atualizado",
                true,
                "http://eventoatualizado.com",
                "Atualização de como chegar",
                "http://linkformsatualizado.com",
                null,
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/eventos/1")
                        .content(objectMapper.writeValueAsString(eventoRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value(eventoReponseDTO.titulo()))
                .andExpect(jsonPath("$.descricao").value(eventoReponseDTO.descricao()));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/eventos/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
