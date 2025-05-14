package listsdo.todo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    @ManyToMany
    @JoinTable(name = "usuarioTarefa",
    joinColumns = @JoinColumn(name = "usuarioID"),
    inverseJoinColumns = @JoinColumn(name = "tarefaID"))
    private Set<Tarefa> tarefas = new HashSet<>();

    public Usuario(Long id, String nome, String email, Set<Tarefa> tarefas) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tarefas = tarefas;
    }

    public Usuario() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
