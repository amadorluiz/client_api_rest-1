import java.util.List;
import java.util.Optional;

import org.iftm.client_api_rest.entities.Livro;
import org.iftm.client_api_rest.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // Apagar um livro por ID
    public void deleteById(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        livroRepository.deleteById(id);
    }

    // Apagar todos os livros
    public void deleteAll() {
        livroRepository.deleteAll();
    }

    // Inserir um livro
    @Transactional
    public Livro save(Livro livro) {
        validateLivro(livro);
        return livroRepository.save(livro);
    }

    // Inserir um conjunto de livros
    @Transactional
    public List<Livro> saveAll(List<Livro> livros) {
        for (Livro livro : livros) {
            validateLivro(livro);
        }
        return livroRepository.saveAll(livros);
    }

    // Modificar um livro
    @Transactional
    public Livro update(Long id, Livro updatedLivro) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro com ID " + id + " não encontrado.");
        }
        validateLivro(updatedLivro);
        updatedLivro.setId(id); // Garante que o ID seja mantido
        return livroRepository.save(updatedLivro);
    }

    // Consultas específicas
    public Optional<Livro> findById(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> findByTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> findByAutor(String autor) {
        return livroRepository.findByAutor(autor);
    }

    public List<Livro> findByAnoPublicacaoGreaterThan(int ano) {
        return livroRepository.findByAnoPublicacaoGreaterThan(ano);
    }

    public List<Livro> findDisponiveis() {
        return livroRepository.findByDisponivel(true);
    }

    // Validação de regras de negócio
    private void validateLivro(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().isEmpty()) {
            throw new IllegalArgumentException("O título do livro é obrigatório.");
        }
        if (livro.getAutor() == null || livro.getAutor().isEmpty()) {
            throw new IllegalArgumentException("O autor do livro é obrigatório.");
        }
        if (livro.getAnoPublicacao() <= 0) {
            throw new IllegalArgumentException("O ano de publicação deve ser maior que zero.");
        }
    }
}
