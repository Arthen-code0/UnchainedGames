package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.ProductCreateDTO;
import modelos.unchainedgames.models.Product;
import modelos.unchainedgames.services.ProductService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/all")
    public List<Product> obtenerTodosProductos(){
        return service.obtenerTodosProductos();
    }

    @GetMapping("/{id}")
    public Product obtenerProductosPorId(@PathVariable Integer id){
        return service.obtenerProductosPorId(id);
    }

    @PostMapping("/create")
    public void createProduct(@RequestBody ProductCreateDTO dto) {
        service.createProduct(dto);
    }

    @PutMapping("/update/{id}")
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductCreateDTO dto) {
        service.updateProduct(id, dto);
    }

    @PutMapping("/update/{id}/picture")
    public Product updateProductPicture(
            @PathVariable Integer id,
            @RequestParam("file") MultipartFile file
    ) {
        return service.updateProductPicture(id, file);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        service.deleteProduct(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<String> languages
    ) {
        return service.searchProducts(name, languages);
    }
}
