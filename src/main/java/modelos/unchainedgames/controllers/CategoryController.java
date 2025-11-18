package modelos.unchainedgames.controllers;

import lombok.AllArgsConstructor;
import modelos.unchainedgames.dto.CategoryCreateDTO;
import modelos.unchainedgames.models.Category;
import modelos.unchainedgames.services.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {

    private CategoryService service;

    @GetMapping("/all")
    public List<Category> obtenerTodasCategorias(){
        return service.obtenerTodasCategorias();
    }

    @GetMapping("/{id}")
    public Category obtenerCategoriaPorId(@PathVariable Integer id){
        return service.obtenerCategoriaPorId(id);
    }

    @PostMapping("/create")
    public void createCategory(@RequestBody CategoryCreateDTO dto) {
        service.createCategory(dto);
    }

    @PutMapping("/update/{id}")
    public void updateCategory(@PathVariable Integer id, @RequestBody CategoryCreateDTO dto) {
        service.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        service.deleteCategory(id);
    }
}