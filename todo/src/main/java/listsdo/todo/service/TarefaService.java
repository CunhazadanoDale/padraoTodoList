package listsdo.todo.service;

import listsdo.todo.exceptions.APIExceptions;
import listsdo.todo.exceptions.ResourceNotFoundException;
import listsdo.todo.model.Tarefa;
import listsdo.todo.repository.TarefaRepository;
import listsdo.todo.transferdata.TarefaDTO;
import listsdo.todo.transferdata.TarefaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public TarefaResponse listarTodas(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Tarefa> tarefasPages = tarefaRepository.findAll(pageDetails);

        List<Tarefa> tarefas = tarefasPages.getContent();

        if(tarefas.isEmpty()) {
            throw new APIExceptions("Nenhuma tarefa criada até o momento");
        }

        List<TarefaDTO> tarefaDTOS = tarefas.stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                .toList();

        TarefaResponse tarefaResponse = new TarefaResponse();
        tarefaResponse.setContent(tarefaDTOS);
        tarefaResponse.setPageNumber(tarefasPages.getNumber());
        tarefaResponse.setPageSize(tarefasPages.getSize());
        tarefaResponse.setTotalElements(tarefasPages.getTotalElements());
        tarefaResponse.setTotalPages(tarefasPages.getTotalPages());
        tarefaResponse.setLastPage(tarefasPages.isLast());

        return tarefaResponse;
    }

    public Optional<Tarefa> buscarPorId(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, Long id) {

        Optional<Tarefa> tarefaOptional = tarefaRepository.findById(id);

        if (tarefaOptional.isPresent()) {

            List<Tarefa> tarefas = List.of(tarefaOptional.get());

            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
            Page<Tarefa> page = new PageImpl<>(tarefas, pageDetails, tarefas.size());

            return page.stream().findFirst();
        }

        throw new APIExceptions("Não foi possível localizar nenhum ID");
    }

    public TarefaDTO criarTarefa(TarefaDTO tarefaDTO) {
        Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);

        Tarefa tarefaFromDB = tarefaRepository.findByTitulo(tarefa.getTitulo());
        if (tarefaFromDB != null) {
            throw new APIExceptions("Tarefa com o nome " + tarefa.getTitulo() + "já existe!!");
        }

        Tarefa savedTarefa = tarefaRepository.save(tarefa);
        return modelMapper.map(savedTarefa, TarefaDTO.class);
    }


    public TarefaDTO atualizarTarefa(TarefaDTO tarefaDTO, Long tarefaID ) {
        Tarefa savedTarefa = tarefaRepository.findById(tarefaID)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", "tarefaID", tarefaID));

        Tarefa tarefa = modelMapper.map(tarefaDTO, Tarefa.class);
        tarefa.setId(tarefaID);
        savedTarefa = tarefaRepository.save(tarefa);
        return modelMapper.map(savedTarefa, TarefaDTO.class);


    }

    public TarefaDTO deletarTarefa(Long tarefaId) {
        Tarefa tarefa = tarefaRepository.findById(tarefaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", "tarefaID", tarefaId));

        tarefaRepository.delete(tarefa);
        return modelMapper.map(tarefa, TarefaDTO.class);
    }
}
