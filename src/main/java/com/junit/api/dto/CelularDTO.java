package com.junit.api.dto;

public class CelularDTO {

    private Long id;
    private String numero;


    public CelularDTO() {}

    public CelularDTO(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
