package com.crio.api.domain.evento;

import com.crio.api.domain.endereco.Endereco;
import com.crio.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "evento")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {
    @Id
    @GeneratedValue
    private UUID id;
    private String titulo;
    private String descricao;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private String local;
    private boolean privado;
    private String linkEvento;
    private String comoChegar;
    private String linkForms;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name="endereco_id")
    private Endereco endereco;

    public Evento(Object o, String titulo, String descriçãoDoEvento, Object o1, Object o2, String localDoEvento, boolean b, String url, String comoChegar, String url1) {
    }

    public void setTitulo(String titulo) {
    }

    public void setDescricao(String descricao) {
    }

    public void setInicio(LocalDateTime inicio) {
    }

    public void setFim(LocalDateTime fim) {
    }

    public void setLocal(String local) {
    }

    public void setPrivado(boolean privado) {
    }

    public void setLinkEvento(String s) {
    }

    public void setComoChegar(String s) {
    }

    public void setLinkForms(String s) {
    }

    public void setUsuario(Usuario usuario) {
    }

    public void setEndereco(Endereco endereco) {
    }
}
