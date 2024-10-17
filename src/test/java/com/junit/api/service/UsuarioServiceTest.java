package com.junit.api.service;

import static org.junit.jupiter.api.Assertions.*;

import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.model.Usuario;
import com.junit.api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    public void testListarTodos() {
        // Configuração do mock
        Usuario usuario1 = new Usuario(1L, "João");
        Usuario usuario2 = new Usuario(2L, "Maria");
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
        Usuario usuarioMock = new Usuario(id, "João");

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

    @Test
    public void testCriar() {
        // Configuração do mock
        Usuario usuarioParaSalvar = new Usuario(null, "João");
        Usuario usuarioSalvo = new Usuario(1L, "João");

        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioSalvo);

        // Chamada do método a ser testado
        Usuario resultado = usuarioService.criar(usuarioParaSalvar);

        // Verificações
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João", resultado.getNome());
        verify(usuarioRepository, times(1)).save(usuarioParaSalvar);
    }

    @Test
    public void testAtualizarExistente() {
        // Configuração
        Long id = 1L;
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João Atualizado");
        Usuario usuarioExistente = new Usuario(id, "João");

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execução
        Usuario resultado = usuarioService.atualizar(id, usuarioCreateDTO);

        // Verificação
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("João Atualizado", resultado.getNome());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    public void testAtualizarInexistente() {
        // Configuração
        Long id = 1L;
        UsuarioCreateDTO usuarioCreateDTO = new UsuarioCreateDTO("João Atualizado");

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        // Execução
        Usuario resultado = usuarioService.atualizar(id, usuarioCreateDTO);

        // Verificação
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(0)).save(any(Usuario.class));
    }

    @Test
    public void testDeletar() {
        // Configuração do mock
        Long id = 1L;

        doNothing().when(usuarioRepository).deleteById(id);

        // Chamada do método a ser testado
        usuarioService.deletar(id);

        // Verificações
        verify(usuarioRepository, times(1)).deleteById(id);
    }
}
