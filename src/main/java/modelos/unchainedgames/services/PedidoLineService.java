package modelos.unchainedgames.services;

import lombok.RequiredArgsConstructor;
import modelos.unchainedgames.dto.PedidoLineCreateDTO;
import modelos.unchainedgames.models.PedidoLine;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.repository.IPedidoLineRepository;
import modelos.unchainedgames.repository.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoLineService {

    private final IPedidoLineRepository repository;
    private final IProductRepository productRepository;

    public List<PedidoLine> obtenerTodosPedidoLine() {
        return repository.findAll();
    }

    public PedidoLine obtenerPedidoLinePorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void createPedidoLine(PedidoLineCreateDTO dto) {
        PedidoLine newPedidoLine = new PedidoLine();
        newPedidoLine.setAmount(dto.getAmount());

        // 👇 aquí resolvemos el Product a partir del ID del DTO
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + dto.getProductId())
                );

        newPedidoLine.setProduct(product);

        repository.save(newPedidoLine);
    }

    public void updatePedidoLine(Integer id, PedidoLineCreateDTO dto) {
        PedidoLine updatedPedidoLine = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "PedidoLine no encontrado con id: " + id)
                );

        updatedPedidoLine.setAmount(dto.getAmount());

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException(
                        "Producto no encontrado con id: " + dto.getProductId())
                );

        updatedPedidoLine.setProduct(product);

        repository.save(updatedPedidoLine);
    }

    public void deletePedidoLine(Integer id) {
        repository.deleteById(id);
    }
}
