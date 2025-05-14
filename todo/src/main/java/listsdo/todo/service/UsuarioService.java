package listsdo.todo.service;

import listsdo.todo.exceptions.APIExceptions;
import listsdo.todo.exceptions.ResourceNotFoundException;
import listsdo.todo.model.Tarefa;
import listsdo.todo.model.Usuario;
import listsdo.todo.repository.TarefaRepository;
import listsdo.todo.repository.UsuarioRepository;
import listsdo.todo.transferdata.TarefaDTO;
import listsdo.todo.transferdata.UsuarioDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<UsuarioDTO> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if(usuarios.isEmpty()) {
            throw new APIExceptions("Nenhum usuario criado até o momento");
        }

        return usuarios.stream().map(usuario -> {
            UsuarioDTO dto = new UsuarioDTO();
            dto.setId(usuario.getId());
            dto.setNome(usuario.getNome());
            dto.setEmail(usuario.getEmail());

            List<TarefaDTO> tarefasDTO = usuario.getTarefas().stream()
                    .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                    .toList();

            dto.setTarefas(tarefasDTO);

            return dto;
        }).toList();
    }

    public UsuarioDTO criarUsuario(UsuarioDTO usuarioDTO) {

        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        usuario = usuarioRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO associarTarefas(Long usuarioId, List<Long> tarefaIds) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        List<Tarefa> tarefas = tarefaRepository.findAllById(tarefaIds);
        if (tarefas.isEmpty()) {
            throw new APIExceptions("Nenhuma tarefa válida encontrada");
        }

        usuario.getTarefas().addAll(tarefas);
        usuario = usuarioRepository.save(usuario);

        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        List<TarefaDTO> tarefasDTO = usuario.getTarefas().stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                .collect(Collectors.toList());

        dto.setTarefas(tarefasDTO);
        return dto;
    }

    public UsuarioDTO atualizarUsuario(Long usuarioId, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        Usuario atualizado = usuarioRepository.save(usuario);

        UsuarioDTO dto = modelMapper.map(atualizado, UsuarioDTO.class);
        List<TarefaDTO> tarefasDTO = atualizado.getTarefas().stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                .collect(Collectors.toList());

        dto.setTarefas(tarefasDTO);
        return dto;
    }

    public UsuarioDTO deletarUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", usuarioId));

        usuarioRepository.delete(usuario);

        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        List<TarefaDTO> tarefasDTO = usuario.getTarefas().stream()
                .map(tarefa -> modelMapper.map(tarefa, TarefaDTO.class))
                .collect(Collectors.toList());

        dto.setTarefas(tarefasDTO);
        return dto;
    }



}
