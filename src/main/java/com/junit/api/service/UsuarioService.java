package com.junit.api.service;

import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.model.Usuario;
import com.junit.api.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario obterPorId(Long id) {
        logger.debug("Obtendo usuário com ID: {}", id);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            logger.info("Usuário encontrado: {}", usuarioOpt.get());
        } else {
            logger.warn("Usuário com ID {} não encontrado", id);
        }
        return usuarioOpt.orElse(null);
    }

    public Usuario criar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioCreateDTO usuarioCreateDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario usuario = usuarioExistente.get();
            usuario.setNome(usuarioCreateDTO.getNome());
            return usuarioRepository.save(usuario);
        } else {
            return null;
        }
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
