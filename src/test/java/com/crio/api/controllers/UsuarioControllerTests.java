package com.crio.api.controllers;

import com.crio.api.controller.UsuarioController;
import com.crio.api.domain.usuario.UsuarioResponseDTO;
import com.crio.api.domain.usuario.UsuarioRequestDTO;
import com.crio.api.service.UsuarioService;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private UsuarioResponseDTO usuarioResponseDTO;

    @BeforeEach
    void setUp() {
        usuarioResponseDTO = new UsuarioResponseDTO(
                UUID.randomUUID(),
                "Pedro",
                "pedro@teste.com",
                "lvjvj",
                1
        );
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        List<UsuarioResponseDTO> list = Arrays.asList(usuarioResponseDTO);
        Page<UsuarioResponseDTO> page = new PageImpl<>(list);

        when(usuarioService.findAllPaged(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nomeCompleto").value(usuarioResponseDTO.nomeCompleto()))
                .andExpect(jsonPath("$.content[0].email").value(usuarioResponseDTO.email()));
    }

    @Test
    void findByIdShouldReturnUsuario() throws Exception {
        when(usuarioService.findById(any(UUID.class))).thenReturn(usuarioResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/" + usuarioResponseDTO.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value(usuarioResponseDTO.nomeCompleto()))
                .andExpect(jsonPath("$.email").value(usuarioResponseDTO.email()));
    }

    @Test
    void insertShouldReturnUsuarioResponseDTOCreated() throws Exception {
        when(usuarioService.insert(any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(
                "Filipe",
                "filipe@teste.com",
                "srjydr",
                2
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCompleto").value(usuarioResponseDTO.nomeCompleto()))
                .andExpect(jsonPath("$.email").value(usuarioResponseDTO.email()));
    }

    @Test
    void updateShouldReturnUsuarioResponseDTO() throws Exception {
        when(usuarioService.update(any(UUID.class), any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO(
                "Pedro Henrique",
                "ph@teste.com",
                "senha",
                1
        );

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/" + usuarioResponseDTO.id())
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeCompleto").value(usuarioResponseDTO.nomeCompleto()))
                .andExpect(jsonPath("$.email").value(usuarioResponseDTO.email()));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/" + usuarioResponseDTO.id())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
