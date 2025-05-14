package listsdo.todo.repository;

import listsdo.todo.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByConcluida(boolean concluida);
    Tarefa findByTitulo(String titulo);
}