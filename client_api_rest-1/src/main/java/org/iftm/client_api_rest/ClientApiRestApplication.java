package org.iftm.client_api_rest;

import java.time.LocalDate;

import org.iftm.client_api_rest.entities.Livro;
import org.iftm.client_api_rest.entities.Usuario;
import org.iftm.client_api_rest.service.LivroService;
import org.iftm.client_api_rest.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApiRestApplication implements CommandLineRunner {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApiRestApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Criando e salvando usuários
        
        Usuario usuario1 = new Usuario(null, "01122333497", "Joao Marcelo", "joao-marcelo@gmail.com", LocalDate.now(), "123456789");
        usuarioService.inserirUsuario(usuario1);
        
        
        System.out.println("\nUsuário: " + usuarioService.consultarPorId(1L).get().getNome());
        
        // Criando e salvando livros
        Livro livro1 = new Livro(null, "Java", "Deitel","x", 2000, true);
        livroService.save(livro1);

        System.out.println("\nLivro: " + livroService.findByTitulo("Java").get(0).getId());
        
    }
}
