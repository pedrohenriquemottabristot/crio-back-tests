package com.crio.api.repositories;

import com.crio.api.domain.evento.Evento;
import com.crio.api.repositorie.EventoRepository;
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
public class EventoRepositoryTests {
    @Autowired
    EntityManager entityManager;

    @Autowired
    EventoRepository eventoRepository;

    @Test
    @DisplayName("Should return Evento when it exists in the database")
    void shouldReturnEventoWhenItExists() {
        String titulo = "NovoEvento";
        Evento evento = this.createEvento(titulo);

        Optional<Evento> result = this.eventoRepository.findByTitulo(titulo);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getTitulo()).isEqualTo(titulo);
    }

    @Test
    @DisplayName("Should return empty when Evento does not exist")
    void shouldReturnEmptyWhenEventoDoesNotExist() {
        String titulo = "EventoInexistente";

        Optional<Evento> result = this.eventoRepository.findByTitulo(titulo);

        assertThat(result.isEmpty()).isTrue();
    }

    private Evento createEvento(String titulo) {
        Evento evento = new Evento(null, titulo, "Descrição do Evento", null, null, "Local do Evento", true, "http://linkdoevento.com", "Como chegar", "http://linkforms.com");
        this.entityManager.persist(evento);
        return evento;
    }
}
