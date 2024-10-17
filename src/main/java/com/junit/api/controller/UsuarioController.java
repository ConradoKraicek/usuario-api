package com.junit.api.controller;


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
                .map(u -> new UsuarioDTO(u.getId(), u.getNome()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obterPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obterPorId(id);
        if (usuario != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNome());
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCreateDTO.getNome());
        Usuario usuarioCriado = usuarioService.criar(usuario);
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioCriado.getId(), usuarioCriado.getNome());
        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioAtualizado = usuarioService.atualizar(id, usuarioCreateDTO);
        if (usuarioAtualizado != null) {
            UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioAtualizado.getId(), usuarioAtualizado.getNome());
            return ResponseEntity.ok(usuarioDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
