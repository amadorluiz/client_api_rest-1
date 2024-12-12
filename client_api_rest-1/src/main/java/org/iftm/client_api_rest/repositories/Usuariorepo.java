package org.iftm.client_api_rest.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.iftm.client_api_rest.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Usuariorepo extends JpaRepository<Usuario, Long> {

    // Busca usuários cujo nome contém o valor informado, ignorando maiúsculas e minúsculas
    List<Usuario> findByNomeContainingIgnoreCase(String nome);

    // Busca um usuário específico pelo CPF
    Optional<Usuario> findByCpf(String cpf);

    // Busca um usuário específico pelo e-mail, ignorando maiúsculas e minúsculas
    Optional<Usuario> findByEmailIgnoreCase(String email);

    // Busca um usuário específico pelo telefone
    Optional<Usuario> findByTelefone(String telefone);

    // Busca usuários nascidos após uma determinada data
    List<Usuario> findByDataNascimentoAfter(LocalDate dataCorte);
}
