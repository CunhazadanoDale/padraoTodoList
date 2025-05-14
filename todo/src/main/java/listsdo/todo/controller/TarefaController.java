package listsdo.todo.controller;

import jakarta.validation.Valid;
import listsdo.todo.confing.AppConstants;
import listsdo.todo.model.Tarefa;
import listsdo.todo.service.TarefaService;
import listsdo.todo.transferdata.TarefaDTO;
import listsdo.todo.transferdata.TarefaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/listarTarefas")
    public ResponseEntity<TarefaResponse> getAllTarefas(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                        @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_TAREFAS_BY) String sortBy,
                                                        @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        TarefaResponse tarefaResponse = tarefaService.listarTodas(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(tarefaResponse, HttpStatus.OK);

    }

    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<Optional<Tarefa>> buscarPorID(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                        @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                        @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_TAREFAS_BY) String sortBy,
                                                        @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
                                                        @PathVariable Long id) {
        Optional<Tarefa> tarefaResponse = tarefaService.buscarPorId(pageNumber, pageSize, sortBy, sortOrder, id);
        return new ResponseEntity<>(tarefaResponse, HttpStatus.OK);
    }

    @PostMapping("/criarTarefas")
    public ResponseEntity<TarefaDTO> criarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO) {
        TarefaDTO novaTarefa = tarefaService.criarTarefa(tarefaDTO);
        return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
    }

    @PutMapping("/criarTarefas/{id}")
    public ResponseEntity<TarefaDTO> atualizarTarefa(@Valid @RequestBody TarefaDTO tarefaDTO,
                                                  @PathVariable Long id) {
        TarefaDTO savedTarefaDTO = tarefaService.atualizarTarefa(tarefaDTO, id);
        return new ResponseEntity<>(savedTarefaDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deletarTarefa/{id}")
    public ResponseEntity<TarefaDTO> deletar(@PathVariable Long id) {
        TarefaDTO deleteTarefa = tarefaService.deletarTarefa(id);
        return new ResponseEntity<>(deleteTarefa, HttpStatus.OK);
    }
}
