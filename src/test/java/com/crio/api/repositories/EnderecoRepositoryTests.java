package com.crio.api.repositories;

import com.crio.api.domain.endereco.Endereco;
import com.crio.api.domain.endereco.EnderecoResponseDTO;
import com.crio.api.repositorie.EnderecoRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class EnderecoRepositoryTests {

    @Autowired
    EntityManager entityManager;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Test
    @DisplayName("Should return Endereco when it exists in the database")
    void shouldReturnEnderecoWhenItExists() {
        String city = "NovoEndereco";
        Endereco endereco = this.createEndereco(city);

        Optional<Endereco> result = this.enderecoRepository.findByCity(city);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getCity()).isEqualTo(city);
    }

    @Test
    @DisplayName("Should return empty when Endereco does not exist")
    void shouldReturnEmptyWhenEnderecoDoesNotExist() {
        String city = "EnderecoInexistente";

        Optional<Endereco> result = this.enderecoRepository.findByCity(city);

        assertThat(result.isEmpty()).isTrue();
    }

    private Endereco createEndereco(String city) {
        Endereco endereco = new Endereco(null, city, "SC");  // Ajuste no construtor da entidade
        this.entityManager.persist(endereco);
        return endereco;
    }
}
