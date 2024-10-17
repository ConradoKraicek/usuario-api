package com.junit.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.dto.UsuarioDTO;
import com.junit.api.model.Usuario;
import com.junit.api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @Test
    public void testListarTodos() {
        // Configuração
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(2L, "Maria");
        List<Usuario> listaUsuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioService.listarTodos()).thenReturn(listaUsuarios);

        // Execução
        ResponseEntity<List<UsuarioDTO>> response = usuarioController.listarTodos();

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
        List<UsuarioDTO> usuariosDTO = response.getBody();
        assertNotNull(usuariosDTO);
        assertEquals(2, usuariosDTO.size());
        assertEquals("João", usuariosDTO.get(0).getNome());
    }

    @Test
    public void testObterPorIdExistente() {
        // Configuração
        Long id = 1L;
        Usuario usuario = new Usuario(id, "João");

        when(usuarioService.obterPorId(id)).thenReturn(usuario);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.obterPorId(id);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
        UsuarioDTO usuarioDTO = response.getBody();
        assertNotNull(usuarioDTO);
        assertEquals(id, usuarioDTO.getId());
        assertEquals("João", usuarioDTO.getNome());
    }

    @Test
    public void testObterPorIdInexistente() {
        // Configuração
        Long id = 1L;

        when(usuarioService.obterPorId(id)).thenReturn(null);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.obterPorId(id);

        // Verificação
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCriarUsuario() {
        // Configuração
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João");
        Usuario usuarioCriado = new Usuario(1L, "João");

        when(usuarioService.criar(any(Usuario.class))).thenReturn(usuarioCriado);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.criar(usuarioCreateDTO);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
        UsuarioDTO usuarioDTO = response.getBody();
        assertNotNull(usuarioDTO);
        assertEquals(1L, usuarioDTO.getId());
        assertEquals("João", usuarioDTO.getNome());
    }

    @Test
    public void testAtualizarUsuarioExistente() {
        // Configuração
        Long id = 1L;
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João Atualizado");
        Usuario usuarioAtualizado = new Usuario(id, "João Atualizado");

        when(usuarioService.atualizar(eq(id), any(UsuarioCreateDTO.class))).thenReturn(usuarioAtualizado);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.atualizar(id, usuarioCreateDTO);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
        UsuarioDTO usuarioDTO = response.getBody();
        assertNotNull(usuarioDTO);
        assertEquals(id, usuarioDTO.getId());
        assertEquals("João Atualizado", usuarioDTO.getNome());
    }

    @Test
    public void testAtualizarUsuarioInexistente() {
        // Configuração
        Long id = 1L;
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João Atualizado");

        when(usuarioService.atualizar(eq(id), any(UsuarioCreateDTO.class))).thenReturn(null);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.atualizar(id, usuarioCreateDTO);

        // Verificação
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testDeletarUsuario() {
        // Configuração
        Long id = 1L;

        doNothing().when(usuarioService).deletar(id);

        // Execução
        ResponseEntity<Void> response = usuarioController.deletar(id);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
    }


}