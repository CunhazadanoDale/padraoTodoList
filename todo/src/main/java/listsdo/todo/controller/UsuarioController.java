package listsdo.todo.controller;

import listsdo.todo.model.Usuario;
import listsdo.todo.service.UsuarioService;
import listsdo.todo.transferdata.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping ("/listarTodosUsuarios")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping ("/criarUsuario")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO novoUsuario = usuarioService.criarUsuario(usuarioDTO);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @PutMapping("/criarUsuario/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id,
                                                       @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO atualizado = usuarioService.atualizarUsuario(id, usuarioDTO);
        return new ResponseEntity<>(atualizado, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<UsuarioDTO> deletarUsuario(@PathVariable Long id) {
        UsuarioDTO deletado = usuarioService.deletarUsuario(id);
        return ResponseEntity.ok(deletado);
    }

    @PutMapping("/{id}/tarefas")
    public ResponseEntity<UsuarioDTO> associarTarefas(@PathVariable Long id,
                                                      @RequestBody List<Long> tarefaIds) {
        UsuarioDTO atualizado = usuarioService.associarTarefas(id, tarefaIds);
        return ResponseEntity.ok(atualizado);
    }


}
