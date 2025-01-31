package org.iftm.client_api_rest.controller;

import org.iftm.client_api_rest.entities.Usuario;
import org.iftm.client_api_rest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService UsuarioService;

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> usuarios = UsuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Optional<Usuario> usuario = UsuarioService.findById(id);
        return usuario.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo usuário
    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        Usuario novoUsuario = UsuarioService.insert(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // Atualizar um usuário
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = UsuarioService.update(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Deletar um usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        UsuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
