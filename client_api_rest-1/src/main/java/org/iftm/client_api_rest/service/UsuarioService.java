package org.iftm.client_api_rest.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.iftm.client_api_rest.entities.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final Map<Long, Usuario> usuarios = new HashMap<>();

    // Validações
    private void validarEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail inválido: deve conter '@'.");
        }
    }

    private void validarIdade(LocalDate dataNascimento) {
        if (dataNascimento == null || Period.between(dataNascimento, LocalDate.now()).getYears() > 100) {
            throw new IllegalArgumentException("Idade inválida: o usuário não pode ter mais de 100 anos.");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome inválido: deve conter pelo menos 3 caracteres.");
        }
    }

    private void validarUsuario(Usuario usuario) {
        validarEmail(((Object) usuario).getEmail());
        validarIdade(usuario.getDataNascimento());
        validarNome(usuario.getNome());
    }

    // Serviços
    @Transactional
    public Usuario inserirUsuario(Usuario usuario) {
        validarUsuario(usuario);
        usuarios.put(usuario.getId(), usuario);
        return usuario;
    }

    @Transactional
    public List<Usuario> inserirUsuarios(List<Usuario> novosUsuarios) {
        novosUsuarios.forEach(this::validarUsuario);
        novosUsuarios.forEach(usuario -> usuarios.put(usuario.getId(), usuario));
        return novosUsuarios;
    }

    @Transactional
    public void apagarUsuario(Long id) {
        if (!usuarios.containsKey(id)) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        usuarios.remove(id);
    }

    @Transactional
    public void apagarTodosUsuarios() {
        usuarios.clear();
    }

    @Transactional
    public Usuario modificarUsuario(Long id, Usuario usuarioAtualizado) {
        if (!usuarios.containsKey(id)) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        validarUsuario(usuarioAtualizado);
        usuarios.put(id, usuarioAtualizado);
        return usuarioAtualizado;
    }

    // Consultas
    public Usuario consultarPorId(Long id) {
        return Optional.ofNullable(usuarios.get(id))
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
    }

    public List<Usuario> consultarPorNome(String nome) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getNome().equalsIgnoreCase(nome))
                .collect(Collectors.toList());
    }

    public Usuario consultarPorCpf(String cpf) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
    }

    public Usuario consultarPorEmail(String email) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
    }

    public Usuario consultarPorTelefone(String telefone) {
        return usuarios.values().stream()
                .filter(usuario -> usuario.getTelefone().equals(telefone))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
    }

    // Consulta com validação
    public List<Usuario> consultarUsuariosMaisNovos(int idadeMaxima) {
        return usuarios.values().stream()
                .filter(usuario -> Period.between(usuario.getDataNascimento(), LocalDate.now()).getYears() <= idadeMaxima)
                .collect(Collectors.toList());
    }
    
}