package com.junit.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EnderecoCreateDTO {

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

    public EnderecoCreateDTO() {
    }

    public EnderecoCreateDTO(String rua, String cidade, String estado, String cep) {
        this.rua = rua;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
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
}
