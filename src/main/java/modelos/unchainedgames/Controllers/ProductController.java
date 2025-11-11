package modelos.unchainedgames.Controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProductCreateDTO;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.services.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService service;

    @GetMapping("/all")
    public List<Product> obtenerTodosPedidos(){
        return service.obtenerTodosPedidos();
    }

    @GetMapping("/{id}")
    public Product obtenerPedidosPorId(@PathVariable Integer id){
        return service.obtenerPedidosPorId(id);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductCreateDTO dto) {
        service.createProduct(dto);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductCreateDTO dto) {
        service.updateProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
    }
}
