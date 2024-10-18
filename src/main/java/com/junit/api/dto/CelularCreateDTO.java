package com.junit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CelularCreateDTO {

    @NotBlank(message = "O número é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "O número do celular deve ter 11 dígitos")
    private String numero;

    public CelularCreateDTO() {
    }

    public CelularCreateDTO(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
