package com.junit.api.controller;


import com.junit.api.dto.CelularDTO;
import com.junit.api.dto.EnderecoDTO;
import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.dto.UsuarioDTO;
import com.junit.api.model.Usuario;
import com.junit.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(this::convertToUsuarioDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterPorId(id);
        UsuarioDTO usuarioDTO = convertToUsuarioDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioCriado = usuarioService.criar(usuarioCreateDTO);
        UsuarioDTO usuarioDTO = convertToUsuarioDTO(usuarioCriado);
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuarioCreateDTO);
        UsuarioDTO usuarioDTO = convertToUsuarioDTO(usuarioAtualizado);
        return ResponseEntity.ok(usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok().build();
    }

    // Método auxiliar para converter Usuario em UsuarioDTO
    public UsuarioDTO convertToUsuarioDTO(Usuario usuario) {
        List<EnderecoDTO> enderecosDTO = usuario.getEnderecos().stream()
                .map(e -> new EnderecoDTO(e.getId(), e.getRua(), e.getCidade(), e.getEstado(), e.getCep()))
                .collect(Collectors.toList());

        List<CelularDTO> celularesDTO = usuario.getCelulares().stream()
                .map(c -> new CelularDTO(c.getId(), c.getNumero()))
                .collect(Collectors.toList());

        return new UsuarioDTO(usuario.getId(), usuario.getNome(), enderecosDTO, celularesDTO);
    }
}
