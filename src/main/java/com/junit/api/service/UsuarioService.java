package com.junit.api.service;

import com.junit.api.dto.UsuarioCreateDTO;
import com.junit.api.model.Celular;
import com.junit.api.model.Endereco;
import com.junit.api.model.Usuario;
import com.junit.api.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Usuario criar(UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioCreateDTO.getNome());

        // Mapear e adicionar endereços
        if (usuarioCreateDTO.getEnderecos() != null) {
            List<Endereco> enderecos = usuarioCreateDTO.getEnderecos().stream()
                    .map(dto -> {
                        Endereco endereco = new Endereco();
                        endereco.setRua(dto.getRua());
                        endereco.setCidade(dto.getCidade());
                        endereco.setEstado(dto.getEstado());
                        endereco.setCep(dto.getCep());
                        endereco.setUsuario(usuario);
                        return endereco;
                    })
                    .collect(Collectors.toList());
            usuario.setEnderecos(enderecos);
        }

        // Mapear e adicionar celulares
        if (usuarioCreateDTO.getCelulares() != null) {
            List<Celular> celulares = usuarioCreateDTO.getCelulares().stream()
                    .map(dto -> {
                        Celular celular = new Celular();
                        celular.setNumero(dto.getNumero());
                        celular.setUsuario(usuario);
                        return celular;
                    })
                    .collect(Collectors.toList());
            usuario.setCelulares(celulares);
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(Long id, UsuarioCreateDTO usuarioCreateDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));

        usuarioExistente.setNome(usuarioCreateDTO.getNome());

        // Atualizar endereços
        usuarioExistente.getEnderecos().clear();
        if (usuarioCreateDTO.getEnderecos() != null) {
            List<Endereco> enderecos = usuarioCreateDTO.getEnderecos().stream()
                    .map(dto -> {
                        Endereco endereco = new Endereco();
                        endereco.setRua(dto.getRua());
                        endereco.setCidade(dto.getCidade());
                        endereco.setEstado(dto.getEstado());
                        endereco.setCep(dto.getCep());
                        endereco.setUsuario(usuarioExistente);
                        return endereco;
                    })
                    .collect(Collectors.toList());
            usuarioExistente.getEnderecos().addAll(enderecos);
        }

        // Atualizar celulares
        usuarioExistente.getCelulares().clear();
        if (usuarioCreateDTO.getCelulares() != null) {
            List<Celular> celulares = usuarioCreateDTO.getCelulares().stream()
                    .map(dto -> {
                        Celular celular = new Celular();
                        celular.setNumero(dto.getNumero());
                        celular.setUsuario(usuarioExistente);
                        return celular;
                    })
                    .collect(Collectors.toList());
            usuarioExistente.getCelulares().addAll(celulares);
        }

        return usuarioRepository.save(usuarioExistente);
    }

    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
