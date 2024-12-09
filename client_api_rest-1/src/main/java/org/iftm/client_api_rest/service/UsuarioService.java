package org.iftm.client_api_rest.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.iftm.client_api_rest.entities.Usuario;
import org.iftm.client_api_rest.repositories.Usuariorepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private Usuariorepo Usuariorepo;

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
        validarEmail(usuario.getEmail());
        validarIdade(usuario.getDataNascimento());
        validarNome(usuario.getNome());
    }

    // Serviços
    @Transactional
    public Usuario inserirUsuario(Usuario usuario) {
        validarUsuario(usuario);
        return Usuariorepo.save(usuario);
    }

    @Transactional
    public List<Usuario> inserirUsuarios(List<Usuario> novosUsuarios) {
        for (Usuario usuario : novosUsuarios) {
            validarUsuario(usuario);
        }
        return Usuariorepo.saveAll(novosUsuarios);
    }

    @Transactional
    public void apagarUsuario(Long id) {
        if (!Usuariorepo.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        Usuariorepo.deleteById(id);
    }

    @Transactional
    public void apagarTodosUsuarios() {
        Usuariorepo.deleteAll();
    }

    @Transactional
    public Usuario modificarUsuario(Long id, Usuario usuarioAtualizado) {
        if (!Usuariorepo.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado.");
        }
        validarUsuario(usuarioAtualizado);
        usuarioAtualizado.setId(id); // Garante que o ID seja mantido
        return Usuariorepo.save(usuarioAtualizado);
    }

    // Consultas
    public Optional<Usuario> consultarPorId(Long id) {
        return Usuariorepo.findById(id);
    }

    public List<Usuario> consultarPorNome(String nome) {
        return Usuariorepo.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Usuario> consultarPorCpf(String cpf) {
        return Usuariorepo.findByCpf(cpf);
    }

    public Optional<Usuario> consultarPorEmail(String email) {
        return Usuariorepo.findByEmailIgnoreCase(email);
    }

    public Optional<Usuario> consultarPorTelefone(String telefone) {
        return Usuariorepo.findByTelefone(telefone);
    }

    public List<Usuario> consultarUsuariosMaisNovos(int idadeMaxima) {
        LocalDate dataCorte = LocalDate.now().minusYears(idadeMaxima);
        return Usuariorepo.findByDataNascimentoAfter(dataCorte);
    }
}
