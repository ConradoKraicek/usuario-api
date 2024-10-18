package com.junit.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A rua é obrigatória")
    @Size(max = 100)
    private String rua;

    @NotBlank(message = "A cidade é obrigatória")
    @Size(max = 50)
    private String cidade;

    @NotBlank(message = "O estado é obrigatório")
    @Size(max = 2)
    private String estado;

    @NotBlank(message = "O CEP é obrigatório")
    @Size(max = 8)
    private String cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    public Endereco() {}

    public Endereco(Long id, String rua, String cidade, String estado, String cep, Usuario usuario) {
        this.id = id;
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
