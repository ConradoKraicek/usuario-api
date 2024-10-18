package com.junit.api.service;

import com.junit.api.dto.CelularCreateDTO;
import com.junit.api.dto.EnderecoCreateDTO;
import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.model.Usuario;
import com.junit.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCriarUsuarioComEnderecosECelulares() {
        // Configuração
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO();
        usuarioCreateDTO.setNome("João");

        EnderecoCreateDTO enderecoDTO = new EnderecoCreateDTO();
        enderecoDTO.setRua("Rua A");
        enderecoDTO.setCidade("Cidade B");
        enderecoDTO.setEstado("SP");
        enderecoDTO.setCep("12345678");

        CelularCreateDTO celularDTO = new CelularCreateDTO();
        celularDTO.setNumero("11987654321");

        usuarioCreateDTO.setEnderecos(Arrays.asList(enderecoDTO));
        usuarioCreateDTO.setCelulares(Arrays.asList(celularDTO));

        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execução
        Usuario resultado = usuarioService.criar(usuarioCreateDTO);

        // Verificação
        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
        assertEquals(1, resultado.getEnderecos().size());
        assertEquals("Rua A", resultado.getEnderecos().get(0).getRua());
        assertEquals(1, resultado.getCelulares().size());
        assertEquals("11987654321", resultado.getCelulares().get(0).getNumero());
    }


    @Test
    public void testListarTodos() {
        // Configuração do mock
        Usuario usuario1 = new Usuario(1L, "João",new ArrayList<>(), new ArrayList<>());
        Usuario usuario2 = new Usuario(2L, "Maria",new ArrayList<>(), new ArrayList<>());
        List<Usuario> listaMock = Arrays.asList(usuario1, usuario2);

        when(usuarioRepository.findAll()).thenReturn(listaMock);

        // Chamada do método a ser testado
        List<Usuario> resultado = usuarioService.listarTodos();

        // Verificações
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    public void testObterPorIdExistente() {
        // Configuração do mock
        Long id = 1L;
        Usuario usuarioMock = new Usuario(id, "João",new ArrayList<>(), new ArrayList<>());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioMock));

        // Chamada do método a ser testado
        Usuario resultado = usuarioService.obterPorId(id);

        // Verificações
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("João", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    public void testObterPorIdInexistente() {
        // Configuração do mock
        Long id = 1L;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Chamada do método a ser testado
        Usuario resultado = usuarioService.obterPorId(id);

        // Verificações
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(id);
    }
}
