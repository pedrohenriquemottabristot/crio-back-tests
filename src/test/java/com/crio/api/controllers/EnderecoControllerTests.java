package com.crio.api.controllers;

import com.crio.api.controller.EnderecoController;
import com.crio.api.domain.endereco.EnderecoResponseDTO;
import com.crio.api.domain.endereco.EnderecoRequestDTO;
import com.crio.api.service.EnderecoService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EnderecoController.class)
public class EnderecoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnderecoService enderecoService;

    @Autowired
    private ObjectMapper objectMapper;

    private EnderecoResponseDTO enderecoResponseDTO;

    @BeforeEach
    void setUp() {
        enderecoResponseDTO = new EnderecoResponseDTO(1L, "Criciuma" ,"SC" );
    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        List<EnderecoResponseDTO> list = Arrays.asList(enderecoResponseDTO);
        Page<EnderecoResponseDTO> page = new PageImpl<>(list);

        when(enderecoService.findAllPaged(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/endereco")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].city").value(enderecoResponseDTO.getCity()))
                .andExpect(jsonPath("$.content[0].uf").value(enderecoResponseDTO.getUf()));
    }

    @Test
    void findByIdShouldReturnEndereco() throws Exception {
        when(enderecoService.findById("Criciuma")).thenReturn(enderecoResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/endereco/Criciuma")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(enderecoResponseDTO.getCity()))
                .andExpect(jsonPath("$.uf").value(enderecoResponseDTO.getUf()));
    }

    @Test
    void insertShouldReturnEnderecoResponseDTOCreated() throws Exception {
        when(enderecoService.insert(any(EnderecoRequestDTO.class))).thenReturn(enderecoResponseDTO);

        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO("Criciuma", "SC");

        mockMvc.perform(MockMvcRequestBuilders.post("/endereco")
                        .content(objectMapper.writeValueAsString(enderecoRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value(enderecoResponseDTO.getCity()))
                .andExpect(jsonPath("$.uf").value(enderecoResponseDTO.getUf()));
    }

    @Test
    void updateShouldReturnEnderecoResponseDTO() throws Exception {
        when(enderecoService.update(any(String.class), any(EnderecoRequestDTO.class))).thenReturn(enderecoResponseDTO);

        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO("Criciuma", "SC");

        mockMvc.perform(MockMvcRequestBuilders.put("/endereco/Criciuma")
                        .content(objectMapper.writeValueAsString(enderecoRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value(enderecoResponseDTO.getCity()))
                .andExpect(jsonPath("$.uf").value(enderecoResponseDTO.getUf()));
    }

    @Test
    void deleteShouldReturnNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/endereco/Criciuma")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
