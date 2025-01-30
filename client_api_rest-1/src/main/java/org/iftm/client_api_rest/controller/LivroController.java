import java.util.List;
import java.util.Optional;

import org.iftm.client_api_rest.entities.Livro;
import org.iftm.client_api_rest.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Listar todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> findAll() {
        List<Livro> livros = livroService.findAll();
        return ResponseEntity.ok(livros);
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        Optional<Livro> livro = livroService.findById(id);
        return livro.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo livro
    @PostMapping
    public ResponseEntity<Livro> create(@RequestBody Livro livro) {
        Livro novoLivro = livroService.insert(livro);
        return ResponseEntity.ok(novoLivro);
    }

    // Atualizar um livro
    @PutMapping("/{id}")
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
        Livro livroAtualizado = livroService.update(id, livro);
        return ResponseEntity.ok(livroAtualizado);
    }

    // Deletar um livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        livroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
