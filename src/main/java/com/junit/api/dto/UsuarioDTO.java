package com.junit.api.dto;

import java.util.List;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private List<EnderecoDTO> enderecos;
    private List<CelularDTO> celulares;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, List<EnderecoDTO> enderecos, List<CelularDTO> celulares) {
        this.id = id;
        this.nome = nome;
        this.enderecos = enderecos;
        this.celulares = celulares;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<EnderecoDTO> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<EnderecoDTO> enderecos) {
        this.enderecos = enderecos;
    }

    public List<CelularDTO> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<CelularDTO> celulares) {
        this.celulares = celulares;
    }
}
