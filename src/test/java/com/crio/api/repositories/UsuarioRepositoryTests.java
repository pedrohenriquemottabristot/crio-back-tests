package com.crio.api.repositories;

import com.crio.api.domain.usuario.Usuario;
import com.crio.api.repositorie.UsuarioRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTests {
    @Autowired
    EntityManager entityManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Should return Usuario when it exists in the database")
    void shouldReturnUsuarioWhenItExists() {
        String email = "usuario@teste.com";
        Usuario usuario = this.createUsuario(email);

        Optional<Usuario> result = this.usuarioRepository.findByEmail(email);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("Should return empty when Usuario does not exist")
    void shouldReturnEmptyWhenUsuarioDoesNotExist() {
        String email = "emailinexistente@teste.com";

        Optional<Usuario> result = this.usuarioRepository.findByEmail(email);

        assertThat(result.isEmpty()).isTrue();
    }

    private Usuario createUsuario(String email) {
        Usuario usuario = new Usuario(
                null,
                "Nome do Usuário",
                email,
                "senha123",
                1
        );
        this.entityManager.persist(usuario);
        return usuario;
    }
}
