package com.junit.api.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class UsuarioCreateDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(max = 100, message = "O nome não pode exceder 100 caracteres")
    private String nome;

    @Valid
    private List<EnderecoCreateDTO> enderecos;

    @Valid
    private List<CelularCreateDTO> celulares;



    public UsuarioCreateDTO() {}

    public UsuarioCreateDTO(String nome, List<EnderecoCreateDTO> enderecos, List<CelularCreateDTO> celulares) {
        this.nome = nome;
        this.enderecos = enderecos;
        this.celulares = celulares;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<EnderecoCreateDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoCreateDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public List<CelularCreateDTO> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<CelularCreateDTO> celulares) {
        this.celulares = celulares;
    }
}