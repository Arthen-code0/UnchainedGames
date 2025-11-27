package modelos.unchainedgames.services;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.PedidoLineCreateDTO;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.repository.IPedidoLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PedidoLineService {

    private IPedidoLineRepository repository;

    public List<PedidoLine> obtenerTodosPedidoLine(){
        return repository.findAll();
    }

    public PedidoLine obtenerPedidoLinePorId(Integer id){
        return repository.findById(id).orElse(null);
    }

    public void createPedidoLine(PedidoLineCreateDTO dto){
        PedidoLine newPedidoLine = new PedidoLine();
        newPedidoLine.setAmount(dto.getAmount());
        newPedidoLine.setPvp(dto.getPvp());
        newPedidoLine.setProduct(dto.getProduct());

        repository.save(newPedidoLine);
    }

    public void updatePedidoLine(Integer id, PedidoLineCreateDTO dto){
        PedidoLine updatedPedidoLine = repository.findById(id).orElse(null);

        if (updatedPedidoLine != null){
            updatedPedidoLine.setAmount(dto.getAmount());
            updatedPedidoLine.setPvp(dto.getPvp());
            updatedPedidoLine.setProduct(dto.getProduct());

            repository.save(updatedPedidoLine);
        }
    }

    public void deletePedidoLine(Integer id){
        repository.deleteById(id);
    }
}
