package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.PedidoCreateDTO;
import modelos.unchainedgames.models.Pedido;
import modelos.unchainedgames.repository.IPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoService {
    private IPedidoRepository repository;

    public List<Pedido> obtenerTodosPedido(){
        return repository.findAll();
    }

    public Pedido obtenerPedidoPorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createPedido(PedidoCreateDTO dto){
        Pedido newPedido = new Pedido();
        newPedido.setDatetime(dto.getDatetime());
        newPedido.setStatus(dto.getStatus());
        newPedido.setUsuario(dto.getUsuario());

        repository.save(newPedido);
    }

    public void updatePedido(Integer id, PedidoCreateDTO dto){
        Pedido updatedPedido = repository.findById(id).orElse(null);

        if (updatedPedido != null){
            updatedPedido.setDatetime(dto.getDatetime());
            updatedPedido.setStatus(dto.getStatus());
            updatedPedido.setUsuario(dto.getUsuario());

            repository.save(updatedPedido);
        }
    }

    public void deletePedido(Integer id){
        repository.deleteById(id);
    }

}
