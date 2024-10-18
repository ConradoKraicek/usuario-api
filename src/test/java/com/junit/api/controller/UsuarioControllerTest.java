package com.junit.api.controller;

import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.dto.UsuarioDTO;
import com.junit.api.model.Celular;
import com.junit.api.model.Endereco;
import com.junit.api.model.Usuario;
import com.junit.api.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        Usuario usuario1 = new Usuario(1L, "João", new ArrayList<>(), new ArrayList<>());
        Usuario usuario2 = new Usuario(2L, "Maria", new ArrayList<>(), new ArrayList<>());
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
        Usuario usuario = new Usuario(id, "João",new ArrayList<>(), new ArrayList<>());

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
    public void testCriarUsuario() {
        // Configuração
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João", new ArrayList<>(), new ArrayList<>());
        Usuario usuarioCriado = new Usuario(1L, "João", new ArrayList<>(), new ArrayList<>());

        when (usuarioService.criar(usuarioCreateDTO)).thenReturn(usuarioCriado);

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
    public void testDeletarUsuario() {
        // Configuração
        Long id = 1L;

        doNothing().when(usuarioService).deletar(id);

        // Execução
        ResponseEntity<Void> response = usuarioController.deletar(id);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testAtualizarUsuario() {
        //Atualizar Usuario
        Long id = 1L;
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João", new ArrayList<>(), new ArrayList<>());
        Usuario usuarioAtualizado = new Usuario(id, "João", new ArrayList<>(), new ArrayList<>());

        when(usuarioService.atualizar(id, usuarioCreateDTO)).thenReturn(usuarioAtualizado);

        // Execução
        ResponseEntity<UsuarioDTO> response = usuarioController.atualizar(id, usuarioCreateDTO);

        // Verificação
        assertEquals(200, response.getStatusCodeValue());
        UsuarioDTO usuarioDTO = response.getBody();
        assertNotNull(usuarioDTO);
        assertEquals(id, usuarioDTO.getId());
        assertEquals("João", usuarioDTO.getNome());
    }

    @Test
    public void convertToUsuarioDTOConvertsCorrectly() {
        // Configuração
        Usuario usuario = new Usuario(1L, "João", new ArrayList<>(), new ArrayList<>());

        // Execução
        UsuarioDTO usuarioDTO = usuarioController.convertToUsuarioDTO(usuario);

        // Verificação
        assertNotNull(usuarioDTO);
        assertEquals(1L, usuarioDTO.getId());
        assertEquals("João", usuarioDTO.getNome());
        assertTrue(usuarioDTO.getEnderecos().isEmpty());
        assertTrue(usuarioDTO.getCelulares().isEmpty());
    }

    @Test
    public void convertToUsuarioDTOConvertsWithEnderecosAndCelulares() {
        // Configuração
        List<Endereco> enderecos = Arrays.asList(new Endereco(1L, "Rua A", "Cidade A", "Estado A", "00000-000", new Usuario(1L, "João", new ArrayList<>(), new ArrayList<>())));
        List<Celular> celulares = Arrays.asList(new Celular(1L, "123456789", new Usuario( 1L, "João", new ArrayList<>(), new ArrayList<>())));
        Usuario usuario = new Usuario(1L, "João", enderecos, celulares);

        // Execução
        UsuarioDTO usuarioDTO = usuarioController.convertToUsuarioDTO(usuario);

        // Verificação
        assertNotNull(usuarioDTO);
        assertEquals(1L, usuarioDTO.getId());
        assertEquals("João", usuarioDTO.getNome());
        assertEquals(1, usuarioDTO.getEnderecos().size());
        assertEquals(1, usuarioDTO.getCelulares().size());
        assertEquals("Rua A", usuarioDTO.getEnderecos().get(0).getRua());
        assertEquals("123456789", usuarioDTO.getCelulares().get(0).getNumero());
    }
}